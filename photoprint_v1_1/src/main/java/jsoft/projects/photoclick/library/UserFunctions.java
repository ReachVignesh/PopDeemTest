/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package jsoft.projects.photoclick.library;

import android.content.Context;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {
	
	private JSONParser jsonParser;

//	private static String loginURL = "http://192.168.123.102/projects/android_login_api/";
//	private static String registerURL = "http://192.168.123.102/projects/android_login_api/";
    private static String loginURL = "http://photoclick.arion.kz/photo_android/";
    private static String registerURL = "http://photoclick.arion.kz/photo_android/";
//    private static String addressURL = "http://photoclick.arion.kz/photo_android/";

	private static String login_tag = "login";
	private static String register_tag = "register";
//    private static String address_tag = "address";


    // constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

        JSONObject json =   JSONParser.getLoginDetails(login_tag, email, password);
		// return json
		Log.e("JSON", json.toString());
		return json;
	}

	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password, String phoneno){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("phoneno", phoneno));

        JSONObject json =   JSONParser.getRegisterDetails(register_tag, name, email, password, phoneno);

        // getting JSON Object
//		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
//    /**
//     * function make Address Form
//     * */
//    public JSONObject addressUser(String name, String region,String city,String street,String house,String apart){
//        // Building Parameters
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", address_tag));
//        params.add(new BasicNameValuePair("name", name));
//        params.add(new BasicNameValuePair("region", region));
//        params.add(new BasicNameValuePair("city", city));
//        params.add(new BasicNameValuePair("street", street));
//        params.add(new BasicNameValuePair("house", house));
//        params.add(new BasicNameValuePair("apart", apart));
//
//        // getting JSON Object
//        JSONObject json = jsonParser.getJSONFromUrl(addressURL, params);
//        // return json
//        Log.e("JSON", json.toString());
//        return json;
//    }
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
}
