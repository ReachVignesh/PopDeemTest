package jsoft.projects.photoclick;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageButton;
import com.facebook.Session;
import jsoft.projects.photoclick.libs.SessionMngr;
import jsoft.projects.photoclick.vk.Account;
import net.londatiga.android.instagram.InstagramSession;
import util.DpsControlApplication;

public class WelcomeActivity extends Activity {
    //	UserFunctions userFunctions;
//	Button btnLogout;
    Button btnAboutUs;
    ImageButton btnDeviceGallery;
    ImageButton btnSocialSelect;
    public int uid;

    SessionMngr session;

    InstagramSession instagramSession;

//    Account account = new Account();

    Context context;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        session = new SessionMngr(getApplicationContext());
        uid = session.getIntValues("uid");

        instagramSession = new InstagramSession(this);

        context = this;

        setContentView(R.layout.welcome);
        btnDeviceGallery =(ImageButton)findViewById(R.id.btnDeviceGallery);
        btnSocialSelect =(ImageButton)findViewById(R.id.btnSocialSelect);
        btnAboutUs =(Button)findViewById(R.id.btnAboutUs);
        DpsControlApplication.exception_handler(this);
        String user = session.getStringValues("userName");


        btnDeviceGallery.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myintent1 = new Intent(WelcomeActivity.this,PaymentSelection.class);
                startActivity(myintent1);

            }
        });
        btnSocialSelect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myintent3 = new Intent(WelcomeActivity.this,SocialSelection.class);
                startActivity(myintent3);

            }
        });
        btnAboutUs.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myintent4 = new Intent(WelcomeActivity.this,AboutActivity.class);
                startActivity(myintent4);

            }
        });
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                logout();
            }
        });

    }

    private void logout(){
        session.unsetSession("uid");
        CookieSyncManager.createInstance(this);
        CookieManager cm = CookieManager.getInstance();
        cm.removeAllCookie();

        instagramSession.reset();

        Account.restore(context);

        fbLogout();
        // End the current activity
        finish();
        // Start a new activity
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    public void fbLogout(){
        Session session = Session.getActiveSession();
        if (session != null) {
            Log.d("fbLogout:", "I am in a session with parameters");
            if (!session.isClosed()) {
                Log.d("fbLogout:","I am in a open session with parameters");
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {
            Log.d("fbLogout:","I am in a session without parameters");
            session = new Session(WelcomeActivity.this);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved

        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.exit))
//                .setMessage("Are you sure you want to exit?")    getResources().getString(R.string.loading)
                .setMessage(getResources().getString(R.string.app_exit))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton(getResources().getString(R.string.no), null).show();
    }

}