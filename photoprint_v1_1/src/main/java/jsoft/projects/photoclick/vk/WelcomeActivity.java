package jsoft.projects.photoclick.vk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import jsoft.projects.photoclick.MainActivity;
import jsoft.projects.photoclick.R;

import java.net.URL;

@SuppressLint("NewApi")
public class WelcomeActivity extends Activity {
	Bundle b;
	TextView tvWelcome;
	public static String twitterImage;
	ImageView photo;
	URL imageURL;
	Bitmap bitmap;
	Button editProfile;
	String gID, gUSER, mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		// 937348332		
		b = getIntent().getExtras();
		tvWelcome = (TextView) findViewById(R.id.welcomemessage);
		photo = (ImageView) findViewById(R.id.profileImage);
		editProfile = (Button) findViewById(R.id.btnEDIT);
		editProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WelcomeActivity.this, EditActivity.class);
				i.putExtra("mode", 1);
				i.putExtra("TID", gID);
				startActivity(i);
			}
		});
		
//		if (b != null) {
//			gID = b.getString("TID");
//			gUSER = b.getString("name");
//			mode = b.getString("mode");
//
//			if (mode.equalsIgnoreCase("Twitter")) {
//				tvWelcome.append(mode + "\n" + gID + "\n" + gUSER);
//
//				try {
//					twitterImage = TwitterApp.user.getBiggerProfileImageURL();
//					imageURL = new URL(twitterImage);
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				try {
//					bitmap = BitmapFactory.decodeStream(imageURL
//							.openConnection().getInputStream());
//					photo.setImageBitmap(bitmap);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			} else {
//				tvWelcome.append(gID + "\n" + gUSER);
//			}
//		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
		WelcomeActivity.this.finish();
		startActivity(i);
		super.onBackPressed();

	}

}
