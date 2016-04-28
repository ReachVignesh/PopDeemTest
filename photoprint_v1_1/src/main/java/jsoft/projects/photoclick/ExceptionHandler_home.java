package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler_home implements
		Thread.UncaughtExceptionHandler {
	private final Activity myContext;
	private final String LINE_SEPARATOR = "\n";

	public ExceptionHandler_home(Activity context) {
		myContext = context;
	}

	public void uncaughtException(Thread thread, Throwable exception) {
		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		StringBuilder errorReport = new StringBuilder();
		errorReport.append("************ CAUSE OF ERROR ************\n\n");
		errorReport.append(stackTrace.toString());

		errorReport.append("\n************ DEVICE INFORMATION ***********\n");
		errorReport.append("Brand: ");
		errorReport.append(Build.BRAND);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Device: ");
		errorReport.append(Build.DEVICE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Model: ");
		errorReport.append(Build.MODEL);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Id: ");
		errorReport.append(Build.ID);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Product: ");
		errorReport.append(Build.PRODUCT);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("\n************ FIRMWARE ************\n");
		errorReport.append("SDK: ");
		errorReport.append(Build.VERSION.SDK_INT);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Release: ");
		errorReport.append(Build.VERSION.RELEASE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Incremental: ");
		errorReport.append(Build.VERSION.INCREMENTAL);
		errorReport.append(LINE_SEPARATOR);

		Intent intent = new Intent(myContext, WelcomeActivity.class);
		// intent.putExtra("error", errorReport.toString());
		myContext.startActivity(intent);

		// Intent intent = new Intent(myContext, ErrorActivity.class);
		// intent.putExtra("error", errorReport.toString());
		// myContext.startActivity(intent);

		// Intent in = new Intent(Intent.ACTION_MAIN);
		// in.addCategory(Intent.CATEGORY_HOME);
		// in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// myContext.startActivity(in);
		// myContext.finish();

		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}

}
