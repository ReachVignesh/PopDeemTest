package jsoft.projects.photoclick.instagram;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import jsoft.projects.photoclick.BaseActivity;
import jsoft.projects.photoclick.FbPhotos;
import jsoft.projects.photoclick.PaymentSelectionInsta;
import jsoft.projects.photoclick.R;
import net.londatiga.android.instagram.Instagram;
import net.londatiga.android.instagram.InstagramRequest;
import net.londatiga.android.instagram.InstagramSession;
import net.londatiga.android.instagram.InstagramUser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LoginInsta extends BaseActivity {
    private InstagramSession mInstagramSession;
    private Instagram mInstagram;
    public String id;
    private ProgressBar mLoadingPb;
    private GridView mGridView;
    Context context;
    private FbPhotos.ImageAdapter imageAdapter;
    ArrayList<String> photoList;

    public static final String CLIENT_ID = "d52b81e94ab946929a61f3c71c2830f0";
    public static final String CLIENT_SECRET = "04cffd330c72479e9b3c0d2477e378cb";
    public static final String REDIRECT_URI = "http://innerglowstudio.com/contact.html";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mInstagram  		= new Instagram(this, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);

        mInstagramSession	= mInstagram.getSession();

        if (mInstagramSession.isActive()) {
            setContentView(R.layout.ac_image_grid_insta);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            InstagramUser instagramUser = mInstagramSession.getUser();

            mLoadingPb 	= (ProgressBar) findViewById(R.id.pb_loading);
            mGridView	= (GridView) findViewById(R.id.gridview);

            ((TextView) findViewById(R.id.tv_name)).setText(instagramUser.fullName);
            ((TextView) findViewById(R.id.tv_username)).setText(instagramUser.username);

            ((Button) findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mInstagramSession.reset();

                    startActivity(new Intent(LoginInsta.this, LoginInsta.class));

                    finish();
                }
            });

            ImageView userIv = (ImageView) findViewById(R.id.iv_user);

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

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);

            AnimateFirstDisplayListener animate  = new AnimateFirstDisplayListener();

            imageLoader.displayImage(instagramUser.profilPicture, userIv, animate);

            new DownloadTask().execute();

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

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private Instagram.InstagramAuthListener mAuthListener = new Instagram.InstagramAuthListener() {
        @Override
        public void onSuccess(InstagramUser user) {
            finish();

            startActivity(new Intent(LoginInsta.this, LoginInsta.class));
        }

        @Override
        public void onError(String error) {
            showToast(error);
        }

        @Override
        public void onCancel() {
            showToast("OK. Maybe later?");

        }
    };

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public class DownloadTask extends AsyncTask<URL, Integer, Long> {
        ArrayList<String> photoList;

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

                Log.d("response====>", response);
//                String response			 = request.createRequest("GET", "/users/self/feed", params);
//                https://api.instagram.com/v1/users/3/media/recent/?client_id=YOUR-CLIENT_ID
                if (!response.equals("")) {
                    JSONObject jsonObj  = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray jsonData	= jsonObj.getJSONArray("data");

                    int length = jsonData.length();

                    if (length > 0) {
                        photoList = new ArrayList<String>();

                        for (int i = 0; i < length; i++) {
                            JSONObject jsonPhoto = jsonData.getJSONObject(i).getJSONObject("images").getJSONObject("low_resolution");

                            photoList.add(jsonPhoto.getString("url"));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
            mLoadingPb.setVisibility(View.GONE);

            if (photoList == null) {
                Toast.makeText(getApplicationContext(), "No Photos Available", Toast.LENGTH_LONG).show();
            } else {
                DisplayMetrics dm = new DisplayMetrics();

                getWindowManager().getDefaultDisplay().getMetrics(dm);

                int width 	= (int) Math.ceil((double) dm.widthPixels / 2);
                width=width-50;
                int height	= width;

                PhotoListAdapter adapter = new PhotoListAdapter(LoginInsta.this);

                adapter.setData(photoList);
                adapter.setLayoutParam(width, height);

                mGridView.setAdapter(adapter);
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
}