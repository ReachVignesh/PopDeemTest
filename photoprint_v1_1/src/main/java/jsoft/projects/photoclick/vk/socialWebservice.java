package jsoft.projects.photoclick.vk;

import android.content.Context;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class socialWebservice {

	public static String url, result, user_id;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	JSONObject userjobj;
	Context c;

	public void CALL_WEB_SERVICE(ArrayList<NameValuePair> Data) {
		url = "http://192.168.1.55/demo_web_service/social_auth.php";
		// user_id, name, type [facebook, twitter, google, vk]

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
		HttpResponse response;

		try {
			HttpPost post = new HttpPost(url);
			StringEntity se = new StringEntity(json.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(new UrlEncodedFormEntity(Data));
			response = client.execute(post);

			if (response != null) {
				is = response.getEntity().getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Connection", "Cannot Estabilish Connection");
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			result = sb.toString();
			Log.d("result", result);
									
		} catch (Exception e) {
			Log.e("Loading Runnable Error converting result :", e.toString());
		}		

//		try {
//			userjobj = new JSONObject(result);
//			user_id = userjobj.getJSONObject("data").getString("id");
//			Log.d("system_db_user_id", user_id);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}
	
}


