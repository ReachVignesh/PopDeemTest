
package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import jsoft.projects.photoclick.library.UserFunctions;
import jsoft.projects.photoclick.libs.ConnectionMngr;
import jsoft.projects.photoclick.libs.SessionMngr;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;

    SessionMngr session;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    ConnectionMngr cm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setTheme(SampleList.THEME); //Used for theme switching in samples
        session = new SessionMngr(getApplicationContext());

        cm = new ConnectionMngr(getApplicationContext());
        setContentView(R.layout.login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunction = new UserFunctions();

                if(!cm.isOnline()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Not connected to network", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 150);
                    toast.show();
                    return;
                }

                Log.d("Button", "Login");
                JSONObject json = userFunction.loginUser(email, password);

                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
                            // Store user details in SQLite Database
//                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");

                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
//                            db.addUser(json_user.getString(KEY_NAME),
//                                    json_user.getString(KEY_EMAIL),
//                                    json.getString(KEY_UID),
//                                    json_user.getString(KEY_CREATED_AT));
                            session.setKeyValues("name", json_user.getInt(KEY_NAME));
                            session.setKeyValues("uid", json_user.getInt(KEY_UID));
                            session.setKeyValues("email", json_user.getString(KEY_EMAIL));
                            session.setKeyValues("created_at", json_user.getString(KEY_CREATED_AT));
                            openDashboard();

                            // Launch Dashboard Screen
//                            Intent dashboard = new Intent(getApplicationContext(), Dashboard.class);

                            // Close all views before launching Dashboard
//                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(dashboard);

                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
//                finish();
            }
        });
    }
    public void openDashboard(){
        Intent dashboard = new Intent(getApplicationContext(), WelcomeActivity.class);

        // Close all views before launching Dashboard
        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dashboard);
    }
}