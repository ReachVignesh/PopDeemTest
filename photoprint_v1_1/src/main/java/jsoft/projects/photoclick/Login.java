package jsoft.projects.photoclick;

import android.view.Window;
import jsoft.projects.photoclick.libs.ConnectionMngr;
import jsoft.projects.photoclick.libs.SessionMngr;
import jsoft.projects.photoclick.libs.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import util.DpsControlApplication;

public class Login extends Activity{

    Button btnLogin, btnDBrd;
    Button btnReg,btnForgotPwd;
    //Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;

    SessionMngr session;


    // JSON Response node names
    public static String KEY_SUCCESS = "success";
    public static String KEY_UID = "uid";
    public static String KEY_UNAME = "name";
    public static String KEY_USERNAME = "username";
    public static String KEY_COUNTRY = "country";
    public static String KEY_STATE = "state";
    public static String KEY_CITY = "city";
    public static String KEY_CONTACT = "contact_no";
    public static String KEY_MYSELF = "myself";
    public static String KEY_EMAIL = "email";

    ConnectionMngr cm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        session = new SessionMngr(getApplicationContext());
        DpsControlApplication.exception_handler(this);
        cm = new ConnectionMngr(getApplicationContext());

        setContentView(R.layout.login_red);
//			Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.etUsername);
        inputPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
//			btnDBrd = (Button) findViewById(R.id.btnDBrd);
//			btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.tvMsg);
        btnReg = (Button) findViewById(R.id.btnReg);
        btnForgotPwd = (Button) findViewById(R.id.btnForgotPwd);

        btnReg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        btnForgotPwd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(), ForgotPwdActivity.class);
                startActivity(i);
            }
        });

        // Login button Click Event
        btnLogin.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunctions = new UserFunctions();

                if(!cm.isOnline()){
                    Toast toast = Toast.makeText(getApplicationContext(), (getResources().getString(R.string.not_connected)), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 150);
                    toast.show();
                    return;
                }

                JSONObject json = userFunctions.loginUser(email, password);

                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){

                            // user successfully logged in
                            JSONObject json_user = json.getJSONObject("user");

//							Log.d("USER info", json_user.toString());
                            // Launch Dashboard Screen
                            session.setKeyValues("uid", json_user.getInt(KEY_UID));
                            session.setKeyValues("userName", json_user.getString(KEY_UNAME));
                            session.setKeyValues("username", json_user.getString(KEY_USERNAME));
                            session.setKeyValues("country", json_user.getString(KEY_COUNTRY));
                            session.setKeyValues("state", json_user.getString(KEY_STATE));
                            session.setKeyValues("city", json_user.getString(KEY_CITY));
                            session.setKeyValues("contact", json_user.getString(KEY_CONTACT));
                            session.setKeyValues("myself", json_user.getString(KEY_MYSELF));
                            session.setKeyValues("email", json_user.getString(KEY_EMAIL));
                            openDashboard();

                            // Close Login Screen
							finish();
                        }else if(Integer.parseInt(json.getString("error")) == 3 ){
                            loginErrorMsg.setText(json.getString("error_msg"));
                        }
                        else{
                            // Error in login
                            loginErrorMsg.setText((getResources().getString(R.string.incorrect_user)));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e){

                }
            }
        });
    }

    public void openDashboard(){
        Intent dashboard = new Intent(getApplicationContext(), WelcomeActivity.class);

        // Close all views before launching Dashboard
        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dashboard);
    }
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
