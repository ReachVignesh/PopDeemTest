package jsoft.projects.photoclick;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import jsoft.projects.photoclick.vk.Account;
import jsoft.projects.photoclick.vk.VKActivity;
import util.DpsControlApplication;

public class PaymentSelectionVk extends Activity {
    private Button btnPrintPay;
    private Button btnPrintFree;
    DpsControlApplication vkselection;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.payment_selection);

        context = this;

        vkselection =(DpsControlApplication) getApplication();
        DpsControlApplication.exception_handler_home(this);

        btnPrintFree = (Button) findViewById(R.id.btnPrintFree);
        btnPrintPay = (Button) findViewById(R.id.btnPrintPay);

        // view products click event
        btnPrintFree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = vkselection.getPreferences().edit();
                edit.putString("free","btnfree");
                edit.commit();
                if(Account.getVk_access_tokenid(context).isEmpty()&&Account.getVk_user_id(context)==0) {


                    Intent i = new Intent(getApplicationContext(), jsoft.projects.photoclick.vk.LoginActivity.class);
                    startActivity(i);
//                finish();
                }else{
                    Intent i = new Intent(getApplicationContext(), VKActivity.class);
                    startActivity(i);
                }
            }
        });
        btnPrintPay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SharedPreferences.Editor edit = vkselection.getPreferences().edit();
                edit.putString("free","btnpay");
                edit.commit();

                if(Account.getVk_access_tokenid(context).isEmpty()&&Account.getVk_user_id(context)==0) {


                    Intent i = new Intent(getApplicationContext(), jsoft.projects.photoclick.vk.LoginActivity.class);
                    startActivity(i);
//                finish();
                }else{
                    Intent i = new Intent(getApplicationContext(), VKActivity.class);
                    startActivity(i);
                }
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
        Intent i = new Intent(PaymentSelectionVk.this, SocialSelection.class);
        PaymentSelectionVk.this.finish();
        startActivity(i);
//        super.onBackPressed();
    }
}