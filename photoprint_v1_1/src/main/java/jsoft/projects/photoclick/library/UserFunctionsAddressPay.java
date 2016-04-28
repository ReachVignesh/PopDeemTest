/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package jsoft.projects.photoclick.library;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class UserFunctionsAddressPay {

    private JSONParser jsonParser;

    private static String addressURL = "http://photoclick.arion.kz/photo_android/UploadToServer.php";

//    private static String address_tag = "address";


    // constructor
    public UserFunctionsAddressPay(){
        jsonParser = new JSONParser();
    }

    /**
     * function make Address Form
     * */
    public void addressUserPay(String uid, String name, String region,String city,String street,String house,String apart,String key,String payable,String comment,String phone,String time, String type){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("uidd", uid));
        params.add(new BasicNameValuePair("add_name", name));
        params.add(new BasicNameValuePair("region_name", region));
        params.add(new BasicNameValuePair("city_name", city));
        params.add(new BasicNameValuePair("street_name", street));
        params.add(new BasicNameValuePair("house_number", house));
        params.add(new BasicNameValuePair("apart_number", apart));
        params.add(new BasicNameValuePair("check", key));
        params.add(new BasicNameValuePair("payable_amt", payable));
        params.add(new BasicNameValuePair("comment_back", comment));
        params.add(new BasicNameValuePair("orderid", time));
        params.add(new BasicNameValuePair("print_typee", type));
        params.add(new BasicNameValuePair("phone_no", phone));


        // getting JSON Object
        JSONParser.getAddressDetails(params);
//        JSONObject json = jsonParser.getJSONFromUrl(addressURL, params);
        // return json
//        Log.e("JSON", json.toString());
//        return json;
    }

    /**
     * Function get Login status
     * */

  /*   public boolean isUserLoggedIn(Context context){
        DatabaseHandlerAddressPay db = new DatabaseHandlerAddressPay(context);
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
    public boolean logoutAddressPay(Context context){
        DatabaseHandlerAddressPay db = new DatabaseHandlerAddressPay(context);
        db.resetTables();
        return true;
    }*/

}
