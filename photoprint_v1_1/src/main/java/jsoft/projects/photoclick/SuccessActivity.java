package jsoft.projects.photoclick;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import jsoft.projects.photoclick.cart.OrderManager;
import util.DpsControlApplication;

public class SuccessActivity extends Activity {
    Button btnBackToDashboard;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.success);
        DpsControlApplication.exception_handler_home(this);
        btnBackToDashboard =(Button)findViewById(R.id.btnBackToDashboard);

        btnBackToDashboard.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                OrderManager om = new OrderManager(getApplicationContext());
                om.open();
                om.delOrderDetails();
                om.delOrderItem();
                om.close();
                Intent myintent8 = new Intent(SuccessActivity.this,WelcomeActivity.class);
                startActivity(myintent8);
                finish();

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

}