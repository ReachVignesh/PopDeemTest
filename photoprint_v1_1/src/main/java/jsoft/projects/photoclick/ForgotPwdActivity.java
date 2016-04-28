package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import jsoft.projects.photoclick.library.UserFunctionsForgotPwd;
import org.json.JSONException;
import org.json.JSONObject;
import util.DpsControlApplication;

public class ForgotPwdActivity extends Activity {
    Button btnSend;
    EditText emailId;
    TextView pwdErrorMsg;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_EMAIL = "email";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgot_pwd);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Importing all assets like buttons, text fields
        emailId = (EditText) findViewById(R.id.etEmailId);
        btnSend = (Button) findViewById(R.id.btnSend);
        pwdErrorMsg = (TextView) findViewById(R.id.tvErrorMsg);
        DpsControlApplication.exception_handler(this);

        // Password Send Button Click event
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = emailId.getText().toString();

                UserFunctionsForgotPwd UserFunctionsForgotPwd = new UserFunctionsForgotPwd();
                JSONObject json = UserFunctionsForgotPwd.forgotPwd(email);

                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        pwdErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user password sent
                            Intent success = new Intent(getApplicationContext(), Login.class);
                            // Close all views before launching Dashboard
                            success.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(success);
                            Toast.makeText(getApplicationContext(), "Please check your Inbox for Password", Toast.LENGTH_LONG).show();
                            // Close Pwd Screen
                            finish();
                        }else{
                            // Error in Pwd Screen
                            pwdErrorMsg.setText("Please type Email Id");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(ForgotPwdActivity.this, Login.class);
        ForgotPwdActivity.this.finish();
        startActivity(i);
        super.onBackPressed();

    }
}
