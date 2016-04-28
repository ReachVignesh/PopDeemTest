package jsoft.projects.photoclick.vk;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import jsoft.projects.photoclick.DetailsVk;
import jsoft.projects.photoclick.PaymentSelectionVk;
import jsoft.projects.photoclick.R;
import jsoft.projects.photoclick.cart.ShoppingCart;
import jsoft.projects.photoclick.vk.api.Api;
import jsoft.projects.photoclick.vk.api.KException;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.DpsControlApplication;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@SuppressLint("NewApi")
public class VKActivity extends Activity {

    // VK ...
    Account account = new Account();
    Api api;
//    Bundle b;
    TextView tvWelcome;
    ArrayList<NameValuePair> vkdata;
    ArrayList<String> imageUrls;
    private static ArrayList<String> vkImages;
    private ArrayList<String> selectedItems;
    private ProgressDialog dialog = null;
    Context context;
    ImageView photo;
    TextView count_price;
    Button editProfile;
    String first_name, last_name, image_profile, all_photos;
    private DisplayImageOptions options;
    private ImageAdapter imageAdapter;
    DpsControlApplication selection;
//    private VkSession mVkSession;
//    private Vk mVk;

    ImageLoader imageLoader;

    @SuppressWarnings("static-access")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_grid_vk);
        selection = (DpsControlApplication)getApplication();
        DpsControlApplication.exception_handler_home(this);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        context =this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        mVk  = mVk.getSession();
//        mVkSession	= mVk.getSession();

//        b = getIntent().getExtras();
        tvWelcome = (TextView) findViewById(R.id.welcomemessage);
        photo = (ImageView) findViewById(R.id.profileImage);
        count_price = (TextView) findViewById(R.id.count_price);

        if(selection.getPreferences().getString("free","0").equalsIgnoreCase("btnfree"))   {
            count_price.setVisibility(View.INVISIBLE);
        }  else  if (selection.getPreferences().getString("free","0").equalsIgnoreCase("btnpay"))
        {
            count_price.setVisibility(View.VISIBLE);
        }

        imageUrls=new ArrayList<String>();

        editProfile = (Button) findViewById(R.id.btnEDIT);
        editProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VKActivity.this, EditActivity.class);
                i.putExtra("mode", 3);
                i.putExtra("VID", String.valueOf(Account.getVk_user_id(context)));
                i.putExtra("NAME", first_name + " " + last_name);
                startActivity(i);
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

//        if (b != null) {
//            account.access_token = b.getString("token");
//            account.user_id = b.getLong("user_id", 0);

//            Account.save(con,auth[0],Long.parseLong(auth[1]));

//            account.save(this);
            api = new Api(Account.getVk_access_tokenid(context), Constants.API_ID);

            Collection<Long> uid = new ArrayList<Long>();
            uid.add(Account.getVk_user_id(context));
            try {
                Long count = Long.parseLong("1000");
                imageUrls.clear();
                api.getProfiles(uid, null, "first_name, last_name, photo", null);

                api.getAllPhotos(Account.getVk_user_id(context), null, count);

                JSONObject jsonResponse;
                try {
                    jsonResponse = new JSONObject(api.response);

                    Log.d("Albums", jsonResponse + "");

                    JSONArray jsonMainNode = jsonResponse .optJSONArray("response");

                    int lengthJsonArr = jsonMainNode.length();
                    for (int i = 1; i < lengthJsonArr; i++) {
                        JSONObject jsonChildNode = jsonMainNode .getJSONObject(i);
                        Log.d("JSON", jsonChildNode + "");

                        first_name="";
                        last_name="";
                        image_profile="";

//						first_name = jsonChildNode.optString("first_name");
//						last_name = jsonChildNode.optString("last_name");
//						image_profile = jsonChildNode.optString("photo");
                        all_photos = jsonChildNode.optString("src_big");

                        imageUrls.add(all_photos);

                        Log.d("src_big",  jsonResponse + "");

                        vkdata = new ArrayList<NameValuePair>();
                        vkdata.add(new BasicNameValuePair("ID", Account.getVk_user_id(context) + ""));
                        vkdata.add(new BasicNameValuePair("NAME", first_name + " " + last_name));
                        vkdata.add(new BasicNameValuePair("SOCIAL", "VK"));
                        vkdata.add(new BasicNameValuePair("PICTURE", image_profile));
                        vkdata.add(new BasicNameValuePair("ALLPHOTOS", all_photos));

                    }

                    options = new DisplayImageOptions.Builder()
                            .showStubImage(R.drawable.stub_image)
                            .showImageForEmptyUri(R.drawable.image_for_empty_url)
                            .cacheInMemory().cacheOnDisc().build();

                    imageAdapter = new ImageAdapter(this, imageUrls);

                    GridView gridView = (GridView) findViewById(R.id.user_Photo);
                    gridView.setAdapter(imageAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (KException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
//        }

        Button button = (Button) findViewById(R.id.btnViewImg);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                btnChoosePhotosClick(v);
            }
        });

//        account.restore(this);
        if (Account.getVk_access_tokenid(context)!= null) {
            api = new Api(Account.getVk_access_tokenid(context), Constants.API_ID);
        }
    }

    public void getcount(){

        selectedItems = imageAdapter .getCheckedItems();
        int value = 30 *  selectedItems.size();
        count_price.setText((getResources().getString(R.string.price))+value);
        SharedPreferences.Editor edit = selection.getPreferences().edit();
        edit.putString("Amt",(getResources().getString(R.string.price))+value);
        edit.commit();
    }

    public class ImageAdapter extends BaseAdapter {

        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;
        SparseBooleanArray msparseBooleanArray;

            public ImageAdapter(Context context, ArrayList<String> imageUrl) {
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
                                    VKActivity.this, R.anim.fade_in);
                            imageView.setAnimation(anim);
                            anim.start();
                        }
                    });

            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    boolean flag = (!mCheckBox.isChecked());
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
                getcount();
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

            File directory = new File(Environment.getExternalStorageDirectory(),"/vk_images");
            if(!directory.exists())
            {
                directory.mkdir();
            }
            for (Object param : params) {
                try {

                    URL url = new URL(param.toString());
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file

                    String imgName = randString(5);
                    File fileName = new File(directory + "/vk_image" + imgName + ".jpg");
                    vkImages.add(fileName.toString());

//		            Log.d("Facebook Images",randString(3));

                    OutputStream output = new FileOutputStream(fileName);
//		            counter++;

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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

            Log.d("Image Urls of VkPhotos", vkImages.toString());

            ShoppingCart cart = new ShoppingCart(VKActivity.this);
            cart.addToCart(vkImages);

            Intent iDetails = new Intent(VKActivity.this, DetailsVk.class);
            iDetails.putExtra("fbImgs", true);
            startActivity(iDetails);
            dialog.dismiss();

        }
    }

    public void btnChoosePhotosClick(View v) {

        vkImages = new ArrayList<String>();
        final ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
        if(selection.getPreferences().getString("free","").equalsIgnoreCase("btnfree"))   {

            if(selectedItems.size()>4 && selectedItems.size()<26){
                dialog = ProgressDialog.show(VKActivity.this, "",(getResources().getString(R.string.on_progress)), true);
                new Thread(new Runnable() {
                    public void run() {
                        new DownloadFileFromURL().execute(selectedItems.toArray());
                    }
                }).start();
            }else {
                Toast.makeText(context, (getResources().getString(R.string.free_toast)), Toast.LENGTH_LONG).show();
            }
        }    else {
            if(selectedItems.size()>0){
                dialog = ProgressDialog.show(VKActivity.this, "",(getResources().getString(R.string.on_progress)), true);
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

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(VKActivity.this, PaymentSelectionVk.class);
        VKActivity.this.finish();
        startActivity(i);
//        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        Intent i = new Intent(this, PaymentSelectionVk.class);
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
