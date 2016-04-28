
package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import jsoft.projects.photoclick.library.UserFunctions;
import jsoft.projects.photoclick.libs.ConnectionMngr;
import org.json.JSONException;
import org.json.JSONObject;
import util.DpsControlApplication;

public class RegisterActivity extends Activity {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword, inputConfirmPassword, inputPhoneno;
    TextView registerErrorMsg;
    ConnectionMngr cm;
    Context context;

//    phoneno

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        context = this;
        DpsControlApplication.exception_handler(this);

        cm = new ConnectionMngr(getApplicationContext());
        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        inputConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);
        inputPhoneno = (EditText) findViewById(R.id.registerPhone);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void showtoast(String toast){
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
            }

            public void onClick(View view) {

                if(!cm.isOnline()){
                    Toast toast = Toast.makeText(getApplicationContext(), (getResources().getString(R.string.not_connected)), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 150);
                    toast.show();
                    return;
                }

                if(inputFullName.getText().toString().isEmpty()){

                    showtoast("Please enter your Name");

                }  else if(inputEmail.getText().toString().isEmpty()){

                    showtoast("Please enter your Email");

                }  else if(inputPassword.getText().toString().isEmpty()){
                    showtoast("Please enter Password");

                }  else if(inputPhoneno.getText().toString().isEmpty()) {
                    showtoast("Please enter Phone number");
                }
                   else if(inputPassword.getText() == inputConfirmPassword.getText() ) {
                    showtoast("Please enter correct password");
                }
               /*else if(commentBack.getText().toString().isEmpty()){
                   showtoast("Please enter Comment");
               }*/
                else {

                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String phoneno = inputPhoneno.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(name, email, password, phoneno);

                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        registerErrorMsg.setText("Welcome to Photo Click !!!");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully registered
                            // Store user details in SQLite Database
//                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");

                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
//                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                            // Launch Dashboard Screen
                            Intent dashboard = new Intent(getApplicationContext(), WelcomeActivity.class);
                            // Close all views before launching Dashboard
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                            // Close Registration Screen
                            finish();
                        }else{
                            // Error in registration
                            registerErrorMsg.setText((getResources().getString(R.string.reg_error)));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(RegisterActivity.this, Login.class);
        RegisterActivity.this.finish();
        startActivity(i);
        super.onBackPressed();

    }

}
