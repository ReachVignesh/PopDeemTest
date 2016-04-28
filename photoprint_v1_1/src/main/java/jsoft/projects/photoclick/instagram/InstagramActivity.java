package jsoft.projects.photoclick.instagram;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import jsoft.projects.photoclick.*;
import jsoft.projects.photoclick.cart.ShoppingCart;
import net.londatiga.android.instagram.Instagram;
import net.londatiga.android.instagram.InstagramRequest;
import net.londatiga.android.instagram.InstagramSession;
import net.londatiga.android.instagram.InstagramUser;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import util.DpsControlApplication;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InstagramActivity extends BaseActivity {
    private InstagramSession mInstagramSession;
    private Instagram mInstagram;
    public String id;
    ArrayList<String> imageUrls;
    private static ArrayList<String> instagramImages;
    private ProgressDialog dialog = null;
    private ArrayList<String> selectedItems;
    private DisplayImageOptions options;
    private ImageAdapter imageAdapter;
    DpsControlApplication selection;

    public static final String CLIENT_ID = "d52b81e94ab946929a61f3c71c2830f0";
    public static final String CLIENT_SECRET = "04cffd330c72479e9b3c0d2477e378cb";
    public static final String REDIRECT_URI = "http://innerglowstudio.com/contact.html";

    Context context;
    GridView gridView;
    TextView count_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        context = this;
        DpsControlApplication.exception_handler_home(this);

        mInstagram = new Instagram(this, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
        mInstagramSession = mInstagram.getSession();
        selection = (DpsControlApplication)getApplication();

        if (mInstagramSession.isActive()) {
            setContentView(R.layout.ac_image_grid_insta);

            InstagramUser instagramUser = mInstagramSession.getUser();

            imageUrls=new ArrayList<String>();

             gridView = (GridView) findViewById(R.id.user_Photo);

            ((TextView) findViewById(R.id.tv_name)).setText(instagramUser.fullName);
            ((TextView) findViewById(R.id.tv_username)).setText(instagramUser.username);

            count_price = (TextView) findViewById(R.id.count_price);

            if(selection.getPreferences().getString("free","").equalsIgnoreCase("btnfree"))   {
                count_price.setVisibility(View.INVISIBLE);
            }  else  if (selection.getPreferences().getString("free","").equalsIgnoreCase("btnpay"))
            {
                count_price.setVisibility(View.VISIBLE);
            }

            ((Button) findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mInstagramSession.reset();

                    startActivity(new Intent(InstagramActivity.this, InstagramActivity.class));

                    finish();
                }
            });

            DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.stub_image)
                    .showImageForEmptyUri(R.drawable.stub_image)
                    .showImageOnFail(R.drawable.stub_image)
                    .cacheInMemory(true)
                    .cacheOnDisc(false)
                    .considerExifParams(true)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .writeDebugLogs()
                    .defaultDisplayImageOptions(displayOptions)
                    .build();

            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);

            new DownloadTask().execute();

            Button button = (Button) findViewById(R.id.btnViewImg);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    btnChoosePhotosClick(v);
                }
            });

        } else {
            setContentView(R.layout.activity_insta);

            ((Button) findViewById(R.id.btn_connect)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mInstagram.authorize(mAuthListener);
                }
            });
        }

    }

    public void getcountamt(){

        selectedItems = imageAdapter.getCheckedItems();
        int value = 30 * selectedItems.size();
        count_price.setText((getResources().getString(R.string.price))+value);
        SharedPreferences.Editor edit = selection.getPreferences().edit();
        edit.putString("Amt",(getResources().getString(R.string.price))+value);
        edit.commit();
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private Instagram.InstagramAuthListener mAuthListener = new Instagram.InstagramAuthListener() {
        @Override
        public void onSuccess(InstagramUser user) {
            finish();

            startActivity(new Intent(InstagramActivity.this, InstagramActivity.class));
        }

        @Override
        public void onError(String error) {
            showToast(error);
        }

        @Override
        public void onCancel() {
            showToast((getResources().getString(R.string.later)));

        }
    };

    public class DownloadTask extends AsyncTask<URL, Integer, Long> {

        InstagramUser instagramUser = mInstagramSession.getUser();

        protected void onCancelled() {

        }

        protected void onPreExecute() {

        }

        protected Long doInBackground(URL... urls) {
            long result = 0;

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>(1);

                params.add(new BasicNameValuePair("count", "-1"));

                InstagramRequest request = new InstagramRequest(mInstagramSession.getAccessToken());

                String response = request.createRequest("GET", "/users/"+ instagramUser.id +"/media/recent", params);
//                                                                       My UserId: 494189868
                if (!response.equals("")) {
                    JSONObject jsonObj  = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray jsonData	= jsonObj.getJSONArray("data");

                    int length = jsonData.length();

                    if (length > 0) {
                        imageUrls = new ArrayList<String>();

                        for (int i = 0; i < length; i++) {
                            JSONObject jsonPhoto = jsonData.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution");

                            imageUrls.add(jsonPhoto.getString("url"));
                        }
                    }
                }
                options = new DisplayImageOptions.Builder()
                        .showStubImage(R.drawable.stub_image)
                        .showImageForEmptyUri(R.drawable.image_for_empty_url)
                        .cacheInMemory().cacheOnDisc().build();

                imageAdapter = new ImageAdapter(context,imageUrls);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(Long result) {
           gridView.setAdapter(imageAdapter);
            }
    }

    public class ImageAdapter extends BaseAdapter {

        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;
        SparseBooleanArray msparseBooleanArray;

        public ImageAdapter(Context context ,ArrayList<String> imageUrl) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            msparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList<String>();
            this.mList = imageUrl;
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArray = new ArrayList<String>();

            for (int i = 0; i < mList.size(); i++) {
                if (msparseBooleanArray.get(i)) {
                    mTempArray.add(mList.get(i));
                }
            }
            return mTempArray;
        }

//		public void clearAllCheckedItems(){
//			for (int i = 0; i < mList.size(); i++) {
//				if (msparseBooleanArray.get(i)) {
//					mTempArray.add(mList.get(i));
//				}
//			}
//		}

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) { // argument is position
            return null;
        }

        @Override
        public long getItemId(int position) { // argument is position
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_multiphoto_item,
                        null);
            }

            final CheckBox mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkBox1);

            final ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.imageView1);

            imageLoader.displayImage("" + mList.get(position), imageView,
                    options, new SimpleImageLoadingListener() {
                        //@Override
                        public void onLoadingComplete(Bitmap loadedImage) {
                            Animation anim = AnimationUtils.loadAnimation(
                                    InstagramActivity.this, R.anim.fade_in);
                            imageView.setAnimation(anim);
                            anim.start();
                        }
                    });

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    boolean flag = (mCheckBox.isChecked()) ? false : true;
                    mCheckBox.setChecked(flag);
                }
            });

            mCheckBox.setTag(position);
            mCheckBox.setChecked(msparseBooleanArray.get(position));
            mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);

            return convertView;
        }

        CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                msparseBooleanArray.put((Integer) buttonView.getTag(),
                        isChecked);
                getcountamt();


            }
        };
    }

    class DownloadFileFromURL extends AsyncTask<Object, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(Object... params) {
            int count;

            File directory = new File(Environment.getExternalStorageDirectory(),"/instagram_images");
            if(directory.exists() == false)
            {
                directory.mkdir();
            }
            for(int i = 0; i<params.length; i++){
                try{

                    URL url = new URL(params[i].toString());
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file

                    String imgName = randString(5);
                    File fileName = new File(directory+"/instagram_image"+imgName+".jpg");
                    instagramImages.add(fileName.toString());

//		            Log.d("Facebook Images",randString(3));

                    OutputStream output = new FileOutputStream(fileName);
//		            counter++;

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress(""+(int)((total*100)/lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch(MalformedURLException e){
                    e.printStackTrace();
                } catch(ClientProtocolException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
            super.onProgressUpdate(progress);
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);

            // Displaying downloaded image into image view
            // Reading image path from sdcard

//	    	UploadFile(selectedItems);

            Log.d("Image Urls at page No 413 of FbPhotos", instagramImages.toString());

            ShoppingCart cart = new ShoppingCart(InstagramActivity.this);
            cart.addToCart(instagramImages);

            Intent iDetails = new Intent(InstagramActivity.this, DetailsInstagram.class);
            iDetails.putExtra("fbImgs", true);
            startActivity(iDetails);
            dialog.dismiss();

        }

    }

    public void btnChoosePhotosClick(View v) {
        instagramImages = new ArrayList<String>();
        final ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
        if(selection.getPreferences().getString("free","").equalsIgnoreCase("btnfree"))   {

            if(selectedItems.size()>4 && selectedItems.size()<26){
                dialog = ProgressDialog.show(InstagramActivity.this, "", (getResources().getString(R.string.on_progress)), true);
                new Thread(new Runnable() {
                    public void run() {
                        new DownloadFileFromURL().execute(selectedItems.toArray());
                    }
                }).start();
            }else {
                Toast.makeText(context,(getResources().getString(R.string.free_toast)), Toast.LENGTH_LONG).show();
            }
        }    else {
            if(selectedItems.size()>0){
                dialog = ProgressDialog.show(InstagramActivity.this, "", (getResources().getString(R.string.on_progress)), true);
                new Thread(new Runnable() {
                    public void run() {
                        new DownloadFileFromURL().execute(selectedItems.toArray());
                    }
                }).start();
            }else {
                Toast.makeText(context,(getResources().getString(R.string.paid_toast)), Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        Intent i = new Intent(this, PaymentSelectionInsta.class);
        startActivity(i);
        return true;
    }
    public static String randString(int length)
    {
        Random rnd = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }
        return new String(text);
    }
}