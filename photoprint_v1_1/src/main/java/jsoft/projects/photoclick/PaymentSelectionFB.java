package jsoft.projects.photoclick;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import util.DpsControlApplication;

public class PaymentSelectionFB extends Activity {
    private Button btnPrintPay;
    private Button btnPrintFree;
    DpsControlApplication selection;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.payment_selection);
        selection = (DpsControlApplication)getApplication();
        DpsControlApplication.exception_handler_home(this);


        btnPrintFree = (Button) findViewById(R.id.btnPrintFree);
        btnPrintPay = (Button) findViewById(R.id.btnPrintPay);

        // view products click event
        btnPrintFree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = selection.getPreferences().edit();
                edit.putString("free","btnfree");
                edit.commit();
                Intent i = new Intent(getApplicationContext(), FbLogin.class);
                startActivity(i);
//                finish();
            }
        });
        btnPrintPay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SharedPreferences.Editor edit = selection.getPreferences().edit();
                edit.putString("free","btnpay");
                edit.commit();
                Intent i = new Intent(getApplicationContext(), FbLogin.class);
                startActivity(i);
//                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        Intent i = new Intent(this, SocialSelection.class);
        startActivity(i);
        return true;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(PaymentSelectionFB.this, SocialSelection.class);
        PaymentSelectionFB.this.finish();
        startActivity(i);
        super.onBackPressed();

    }
}