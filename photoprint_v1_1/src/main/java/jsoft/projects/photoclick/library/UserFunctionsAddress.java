/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package jsoft.projects.photoclick.library;

import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctionsAddress {

    private JSONParser jsonParser;

    //	private static String loginURL = "http://192.168.123.102/projects/android_login_api/";
//	private static String registerURL = "http://192.168.123.102/projects/android_login_api/";

    private static String addressURL = "http://photoclick.arion.kz/photo_android/";

    private static String address_tag = "address";


    // constructor
    public UserFunctionsAddress(){
        jsonParser = new JSONParser();
    }

    /**
     * function make Address Form
     * */
    public JSONObject addressUser(String name, String region,String city,String street,String house,String apart, String key)
//    , String region,String city,String street,String house,String apart, String key
    {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", address_tag));
        params.add(new BasicNameValuePair("add_name", name));
        params.add(new BasicNameValuePair("region_name", region));
        params.add(new BasicNameValuePair("city_name", city));
        params.add(new BasicNameValuePair("street_name", street));
        params.add(new BasicNameValuePair("house_number", house));
        params.add(new BasicNameValuePair("apart_number", apart));
        params.add(new BasicNameValuePair("check", key));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(addressURL, params);
        // return json
        Log.e("JSON", json.toString());
        return json;
    }

    /**
     * Function get Login status
     * */

   /*  public boolean isUserLoggedIn(Context context){
        DatabaseHandlerAddress db = new DatabaseHandlerAddress(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }

    *//**
     * Function to logout user
     * Reset Database
     * *//*
    public boolean logoutAddress(Context context){
        DatabaseHandlerAddress db = new DatabaseHandlerAddress(context);
        db.resetTables();
        return true;
    }*/

}
