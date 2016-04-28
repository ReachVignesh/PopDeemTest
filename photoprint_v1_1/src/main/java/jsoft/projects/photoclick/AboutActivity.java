package jsoft.projects.photoclick;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import util.DpsControlApplication;

/**
 * Created by innerglowitsolutions on 25/05/14.
 */

public class AboutActivity extends Activity {

    private Button btnEmailUs;
    private TextView txtAboutPhones;

    /** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar actionBar = getActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.activity_about);
    DpsControlApplication.exception_handler_home(this);
    btnEmailUs = (Button) findViewById(R.id.btnEmailUs);
    txtAboutPhones = (TextView) findViewById(R.id.txtAboutPhones);

    btnEmailUs.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "yerlan@photoclick.kz", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact support team");
            startActivity(Intent.createChooser(emailIntent, "Email Us"));
        }
    });

  /*  txtAboutPhones.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            TextView numberField = (TextView) findViewById(R.id.txtAboutPhones);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + numberField.getText())); // set the Uri
            startActivity(intent);
        }

    });*/
}
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
        return true;
    }

 }
