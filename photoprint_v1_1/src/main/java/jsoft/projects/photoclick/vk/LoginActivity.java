package jsoft.projects.photoclick.vk;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import jsoft.projects.photoclick.PaymentSelectionVk;
import jsoft.projects.photoclick.R;
import jsoft.projects.photoclick.vk.api.Auth;
import util.DpsControlApplication;

@SuppressLint("SetJavaScriptEnabled")
public class LoginActivity extends Activity {
	WebView webview;

    Context con;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_vk);
        DpsControlApplication.exception_handler_home(this);

        con = this;

		webview = (WebView) findViewById(R.id.vkontakteview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.clearCache(true);

		webview.setWebViewClient(new FacebookWebViewClient());
		CookieSyncManager.createInstance(this);

		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();

		String url = Auth.getUrl(Constants.API_ID, Auth.getSettings());
		webview.loadUrl(url);
	}

	class FacebookWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			parseUrl(url);
		}
	}

	private void parseUrl(String url) {
		try {
			if (url == null)
				return;
			Log.i("url", "url=" + url);
			if (url.startsWith(Auth.redirect_url)) {
				if (!url.contains("error=")) {
					String[] auth = Auth.parseRedirectUrl(url);


                    Account.save(con,auth[0],Long.parseLong(auth[1]));

					Intent intent = new Intent(LoginActivity.this, VKActivity.class);
					intent.putExtra("token", auth[0]);
					intent.putExtra("user_id", Long.parseLong(auth[1]));
					startActivity(intent);
				}
				// finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        Intent i = new Intent(this, PaymentSelectionVk.class);
        startActivity(i);
        return true;
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(LoginActivity.this, PaymentSelectionVk.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}