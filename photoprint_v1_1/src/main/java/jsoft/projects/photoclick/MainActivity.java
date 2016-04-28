package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import jsoft.projects.photoclick.libs.SessionMngr;
import util.DpsControlApplication;


public class MainActivity extends Activity {

	SessionMngr session;
    int TUTORIAL;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        DpsControlApplication.exception_handler_home(this);

//        SharedPreferences settings = getSharedPreferences("prefs", 0);
//        boolean firstRun = settings.getBoolean("firstRun", true);
//        if ( firstRun )
//        {
//            // here run your first-time instructions, for example :
//            startActivityForResult(
//                    new Intent(this, TutorialActivity.class), TUTORIAL);
//
//        }
		session = new SessionMngr(getApplicationContext());
		
		if(session.IsLoggedIn()){
			openDashboard();
		}
		finish();
	}

//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data) {
//        if (requestCode == TUTORIAL) {
//            SharedPreferences settings = getSharedPreferences("prefs", 0);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putBoolean("firstRun", false);
//            editor.commit();
//        }
//    }
	
	public void openDashboard(){
		Intent dashboard = new Intent(getApplicationContext(), WelcomeActivity.class);
		
		// Close all views before launching Dashboard
		dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		dashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(dashboard);
	}

}