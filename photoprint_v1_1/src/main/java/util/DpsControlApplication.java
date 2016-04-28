package util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import jsoft.projects.photoclick.ExceptionHandler;
import jsoft.projects.photoclick.ExceptionHandler_home;

public class DpsControlApplication extends Application {

	SharedPreferences preferences;
	DpsControlApplication application;

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	Context context;

	@Override
	public void onCreate() {
		super.onCreate();

		context = this;
		application = this;
		preferences = getSharedPreferences("dpscontrol", 0);

	}

    public static void exception_handler(Activity act) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(act));
    }

    public static void exception_handler_home(Activity act) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler_home(act));
    }

}
