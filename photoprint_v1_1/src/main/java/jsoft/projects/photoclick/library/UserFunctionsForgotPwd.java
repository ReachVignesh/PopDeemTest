package jsoft.projects.photoclick.library;

import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctionsForgotPwd {

    private JSONParser jsonParser;

    // constructor
    public UserFunctionsForgotPwd(){
        jsonParser = new JSONParser();
    }

    /**
     * function make Address Form
     * */
    public JSONObject forgotPwd(String email){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String pwd_tag = "forgot_password";
        params.add(new BasicNameValuePair("tag", pwd_tag));
        params.add(new BasicNameValuePair("email", email));

        // getting JSON Object
        String pwdURL = "http://photoclick.arion.kz/photo_android/forgot_password.php";
        JSONObject json = jsonParser.getJSONFromUrl(pwdURL, params);
        // return json
        Log.e("JSON", json.toString());
        return json;
    }
}
