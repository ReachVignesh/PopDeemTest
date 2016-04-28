/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package jsoft.projects.photoclick.library;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.UTF_8;

public class JSONParser {

    static InputStream is = null;
	static JSONObject jObj = null,jObject;
	static String json = "",result;
    private static String loginURL = "http://photoclick.arion.kz/photo_android/";
    private static String registerURL = "http://photoclick.arion.kz/photo_android/";
    private static String addressURL = "http://photoclick.arion.kz/photo_android/UploadToServer.php";

    // constructor
	public JSONParser() {

	}

    public static JSONObject getRegisterDetails(String register_tag, String name, String email,String password,String phoneno) {
        // Making HTTP request
        try {
            // defaultHttpClient

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(registerURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", register_tag));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("phoneno", phoneno));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, UTF_8);
            httpPost.setEntity(ent);
            HttpResponse responsePOST = httpClient.execute(httpPost);
            HttpEntity httpEntity = responsePOST.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(result);
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
        }
        // try parse the string to a JSON object
        try {
            jObject = new JSONObject(json);
        } catch (JSONException e) {
        }

        // return JSON String
        return jObject;
    }

    public static JSONObject getLoginDetails(String login_tag, String email,String password) {
        // Making HTTP request
        try {
            // defaultHttpClient

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(loginURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", login_tag));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, UTF_8);
            httpPost.setEntity(ent);
            HttpResponse responsePOST = httpClient.execute(httpPost);
            HttpEntity httpEntity = responsePOST.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(result);
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
        }
        // try parse the string to a JSON object
        try {
            jObject = new JSONObject(json);
        } catch (JSONException e) {
        }

        // return JSON String
        return jObject;
    }

    public static void getAddressDetails(List<NameValuePair> params){
            // Making HTTP request
            try {
                // defaultHttpClient

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(addressURL);
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
////      params.add(new BasicNameValuePair("tag", address_tag));
//        params.add(new BasicNameValuePair("add_name", name));
//        params.add(new BasicNameValuePair("region_name", region));
//        params.add(new BasicNameValuePair("city_name", city));
//        params.add(new BasicNameValuePair("street_name", street));
//        params.add(new BasicNameValuePair("house_number", house));
//        params.add(new BasicNameValuePair("apart_number", apart));
//        params.add(new BasicNameValuePair("check", key));
//        params.add(new BasicNameValuePair("payable_amt", payable));
//        params.add(new BasicNameValuePair("comment_back", comment));
                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, UTF_8);
                httpPost.setEntity(ent);
                HttpResponse responsePOST = httpClient.execute(httpPost);
                HttpEntity httpEntity = responsePOST.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(result);
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
        }
        // try parse the string to a JSON object
//        try {
//            jObject = new JSONObject(json);
//        } catch (JSONException e) {
//        }

        // return JSON String
//        return jObject;
    }

	public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
}



