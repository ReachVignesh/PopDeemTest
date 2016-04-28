package jsoft.projects.photoclick;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import util.DpsControlApplication;

public class SocialSelection extends Activity {
    //	UserFunctions userFunctions;
//	Button btnLogout;
    ImageButton btnFacebook;
    ImageButton btnInstagram;
    ImageButton btnVk;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.social_selection);
        DpsControlApplication.exception_handler_home(this);
        btnFacebook =(ImageButton)findViewById(R.id.btnFacebook);
        btnInstagram =(ImageButton)findViewById(R.id.btnInstagram);
        btnVk =(ImageButton)findViewById(R.id.btnVk);

        btnFacebook.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myintent1 = new Intent(SocialSelection.this,PaymentSelectionFB.class);
                startActivity(myintent1);

            }
        });
        btnInstagram.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myintent2 = new Intent(SocialSelection.this,PaymentSelectionInsta.class);
                startActivity(myintent2);

            }
        });
        btnVk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myintent3 = new Intent(SocialSelection.this, PaymentSelectionVk.class);
                startActivity(myintent3);

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
        return true;
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(SocialSelection.this, WelcomeActivity.class);
        SocialSelection.this.finish();
        startActivity(i);
        super.onBackPressed();

    }

}