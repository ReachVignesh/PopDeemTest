package jsoft.projects.photoclick;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.facebook.Session;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import jsoft.projects.photoclick.cart.ShoppingCart;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import util.DpsControlApplication;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Random;

public class FbPhotos extends BaseActivity {

	int counter=0;

	String imgPath = null;
	String upLoadServerUri = "http://photoclick.arion.kz/photo_android/UploadToServer.php";
	String lineEnd = "\r\n";
	String twoHypens = "--";
	String boundary = "*****";

	TextView tvMulMsg;
	
	private ArrayList<String> selectedItems;
	private ArrayList<String> imageIds;
	private ArrayList<String> imageUrls;
	private DisplayImageOptions options;
	private ImageAdapter imageAdapter;
	private ProgressDialog dialog = null;
	private String albumId;
    Context context;

    TextView count_price;
    DpsControlApplication selection;
    String free;
	
	private static ArrayList<String> fbImages;

	Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);
        selection = (DpsControlApplication)getApplication();

        context = this;


        Bundle extras = getIntent().getExtras();
		albumId = extras.getString("albumId");

        count_price=(TextView)findViewById(R.id.count_price);

		session = new Session(getApplicationContext());
        DpsControlApplication.exception_handler_home(this);

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

		tvMulMsg = (TextView) findViewById(R.id.tvMulMsg);

        count_price = (TextView) findViewById(R.id.count_price);

        if(selection.getPreferences().getString("free","0").equalsIgnoreCase("btnfree"))   {
            count_price   .setVisibility(View.INVISIBLE);
        }  else  if (selection.getPreferences().getString("pay","0").equalsIgnoreCase("btnpay"))
        {
            count_price   .setVisibility(View.VISIBLE);
        }

//        if(selection.getPreferences().getString("pay","0").equalsIgnoreCase("btnpay"))   {
//            count_price   .setVisibility(View.VISIBLE);
//        }  else
//        {
//            count_price   .setVisibility(View.INVISIBLE);
//        }
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

        String url = "https://graph.facebook.com/" + albumId + "/photos?limit=500&offset=0&fields=images&access_token=" + session.getAccessToken();
//        String url = "https://graph.facebook.com/" + albumId + "/photos?limit=500&offset=0&until=yesterday&fields=images&access_token=" + session.getAccessToken();

        try {

			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			String algorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(algorithm);
			tmf.init(keyStore);

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, tmf.getTrustManagers(), null);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpEntity.getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}

			String jString = "[" + builder.toString() + "]";

			JSONArray jsonArray = new JSONArray(jString);

			String temp = jsonArray.getJSONObject(0).get("data").toString();

			JSONArray jsonAlbumsArray = new JSONArray(temp);

			MatrixCursor mcImages = new MatrixCursor(new String[] { "source", "id" }); // properties from the JSONObjects

			for (int i = 0; i < jsonAlbumsArray.length(); i++) {
				JSONObject jo = jsonAlbumsArray.getJSONObject(i);
				JSONArray ja = jo.getJSONArray("images");
				JSONObject joImages = ja.getJSONObject(0);
				mcImages.addRow(new Object[] { joImages.get("source"),
						jo.get("id") });
			}

			this.imageUrls = new ArrayList<String>();
			this.imageIds = new ArrayList<String>();
			String tempUrl;
			for (int i = 0; i < mcImages.getCount(); i++) {
				mcImages.moveToPosition(i);

				int dataColumnIndex = mcImages.getColumnIndex("source");
				tempUrl = mcImages.getString(dataColumnIndex);
				//images = LoadImageFromWebOperations(temp);
				imageUrls.add(tempUrl);
				dataColumnIndex = mcImages.getColumnIndex("id");
				imageIds.add(mcImages.getString(dataColumnIndex));
				
			}
			mcImages.close();

			options = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.stub_image)
					.showImageForEmptyUri(R.drawable.image_for_empty_url)
					.cacheInMemory().cacheOnDisc().build();

			imageAdapter = new ImageAdapter(this, imageUrls);

			GridView gridView = (GridView) findViewById(R.id.gridview);
			gridView.setAdapter(imageAdapter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Button button = (Button) findViewById(R.id.btnViewImg);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnChoosePhotosClick(v);

				
			}
		});
		
	}

    public void getcount(){

        selectedItems = imageAdapter .getCheckedItems();
        int value = 30 * selectedItems.size();
        count_price.setText((getResources().getString(R.string.price))+value);
        SharedPreferences.Editor edit = selection.getPreferences().edit();
        edit.putString("Amt",(getResources().getString(R.string.price))+value);
        edit.commit();
    }

	@Override
	protected void onStop() {
		imageLoader.stop();
		super.onStop();
	}

	public static Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}

	public void btnChoosePhotosClick(View v) {

		fbImages = new ArrayList<String>();
//		selectedItems = imageAdapter .getCheckedItems();

        final ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
//        selectedItems = imageAdapter .getCheckedItems();
        if(selection.getPreferences().getString("free","").equalsIgnoreCase("btnfree"))   {

            if(selectedItems.size()>4 && selectedItems.size()<26){
                dialog = ProgressDialog.show(FbPhotos.this, "",(getResources().getString(R.string.on_progress)), true);
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
                dialog = ProgressDialog.show(FbPhotos.this, "", (getResources().getString(R.string.on_progress)), true);
                new Thread(new Runnable() {
                    public void run() {
                        new DownloadFileFromURL().execute(selectedItems.toArray());
                    }
                }).start();
            }else {
                Toast.makeText(context,(getResources().getString(R.string.paid_toast)), Toast.LENGTH_LONG).show();
            }
        }
		/*dialog = ProgressDialog.show(FbPhotos.this, "", "On Progress...",
				true);
		new Thread(new Runnable() {
			public void run() {
				new DownloadFileFromURL().execute(selectedItems.toArray());
			}
		}).start();*/
	}

	public class ImageAdapter extends BaseAdapter {

		ArrayList<String> mList;
		LayoutInflater mInflater;
		Context mContext;
		SparseBooleanArray msparseBooleanArray;

		public ImageAdapter(Context context, ArrayList<String> imageUrls) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			msparseBooleanArray = new SparseBooleanArray();
			mList = new ArrayList<String>();
			this.mList = imageUrls;
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

			imageLoader.displayImage("" + imageUrls.get(position), imageView,
					options, new SimpleImageLoadingListener() {
//						@Override
						public void onLoadingComplete(Bitmap loadedImage) {
							Animation anim = AnimationUtils.loadAnimation(
									FbPhotos.this, R.anim.fade_in);
							imageView.setAnimation(anim);
							anim.start();
						}
					});
			
			imageView.setOnClickListener(new OnClickListener() {
				
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

		OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
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
	    	
	    	File directory = new File(Environment.getExternalStorageDirectory(),"/fb_images");
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
		            File fileName = new File(directory+"/fb_image"+imgName+".jpg");
		            fbImages.add(fileName.toString());
		            
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
	    	
	    	Log.d("Image Urls at page No 413 of FbPhotos", fbImages.toString());
	    	
	    	ShoppingCart cart = new ShoppingCart(FbPhotos.this);
			cart.addToCart(fbImages);

//            if(fbImages.size()>0){
//                Intent iDetails = new Intent(FbPhotos.this, Details.class);
//                iDetails.putExtra("fbImgs", true);
//                startActivity(iDetails);
//                dialog.dismiss();
//            }else {
//                Toast.makeText(context, "Not a single image selected", Toast.LENGTH_SHORT).show();
//            }
			
			Intent iDetails = new Intent(FbPhotos.this, DetailsFb.class);
			iDetails.putExtra("fbImgs", true);
			startActivity(iDetails);
			dialog.dismiss();
	    	
	    }
	 
	}
	
	public static void DeleteFbImages(){
		
		for(int i=0 ;i< fbImages.size();i++){
			String delImg = Environment.getExternalStorageDirectory().toString()+"/fb_images/fb_image"+i+".jpg";
			DeleteFile(delImg);
		}
		
		File path = new File (Environment.getExternalStorageDirectory().toString()+"/fb_images");
		if(path.exists()){
			path.delete();
		}
		
	}
	
	public static void DeleteFile(String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			System.out.println("not exists");
			return;
		}
		if(!file.isDirectory()){
			System.out.println("Deleted");
			file.delete();
			return;
		}
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        Intent i = new Intent(this, FbGallery.class);
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