package jsoft.projects.photoclick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import jsoft.projects.photoclick.library.UserFunctionsAddressPay;

public class AddressPayActivity extends Activity {
    Button btnAddressSend;
    EditText addressName;
    EditText regionName;
    EditText cityName;
    EditText streetName;
    EditText houseNumber;
    EditText apartNumber;
    EditText payableAmt;
    EditText commentBack;
    CheckBox deliveryCheck;
    TextView registerErrorMsg;
//    Spinner spinnerRegion;
//    GalleryAdapter adapter;


    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_ADDRESSNAME = "add_name";
    private static String KEY_REGIONNAME = "region_name";
    private static String KEY_CITYNAME = "city_name";
    private static String KEY_STREETNAME = "street_name";
    private static String KEY_HOUSENUMBER = "house_number";
    private static String KEY_APARTNUMBER = "apart_number";
    private static String KEY_DELIVERYCHECK = "check";
    private static String KEY_PAYABLE = "payable_amt";
    private static String KEY_COMMENT = "comment_back";
    private static String KEY_CREATED_AT = "created_at";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addressform_pay);

        // Importing all assets like buttons, text fields
        addressName = (EditText) findViewById(R.id.addressName);
        regionName = (EditText) findViewById(R.id.regionName);
        cityName = (EditText) findViewById(R.id.cityName);
        streetName = (EditText) findViewById(R.id.streetName);
        houseNumber = (EditText) findViewById(R.id.houseNumber);
        apartNumber = (EditText) findViewById(R.id.apartNumber);
        payableAmt = (EditText) findViewById(R.id.payable_Amt);
        commentBack = (EditText) findViewById(R.id.comment_Back);
        deliveryCheck = (CheckBox) findViewById(R.id.deliveryCheck);
        btnAddressSend = (Button) findViewById(R.id.btnAddressSend);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

//        spinnerRegion= (Spinner) findViewById(R.id.spinnerRegion);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.region,android.R.layout.simple_spinner_dropdown_item);
//        spinnerRegion.setAdapter(adapter);

        // Address Send Button Click event
        btnAddressSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = addressName.getText().toString();
                String region = regionName.getText().toString();
                String city = cityName.getText().toString();
                String street = streetName.getText().toString();
                String house = houseNumber.getText().toString();
                String apart = apartNumber.getText().toString();
                String payable = payableAmt.getText().toString();
                String comment = commentBack.getText().toString();

                String key;
                if(deliveryCheck.isChecked()) {
                    // Use the key 1 for your webservice
                    key = "Yes";
                } else {
                    // Use the key 2 for your webservice
                    key = "No";
                }

                UserFunctionsAddressPay userFunctionAddressPay = new UserFunctionsAddressPay();
//                JSONObject json = userFunctionAddressPay.addressUserPay(name, region, city, street, house, apart, key, payable, comment);

//                // check for login response
//                try {
//                    if (json.getString(KEY_SUCCESS) != null) {
//                        registerErrorMsg.setText("");
//                        String res = json.getString(KEY_SUCCESS);
//                        if(Integer.parseInt(res) == 1){
//                            // user successfully registered
//                            // Store user details in SQLite Database
////                            DatabaseHandlerAddressPay db = new DatabaseHandlerAddressPay(getApplicationContext());
////                            JSONObject json_address = json.getJSONObject("user");
//
//                            // Clear all previous data in database
////                            userFunctionAddressPay.logoutAddressPay(getApplicationContext());
////                            db.addAddressPay(json_address.getString(KEY_ADDRESSNAME), json_address.getString(KEY_REGIONNAME), json_address.getString(KEY_CITYNAME), json_address.getString(KEY_STREETNAME), json_address.getString(KEY_HOUSENUMBER), json_address.getString(KEY_APARTNUMBER), json_address.getString(KEY_DELIVERYCHECK), json_address.getString(KEY_PAYABLE), json_address.getString(KEY_COMMENT), json.getString(KEY_UID), json_address.getString(KEY_CREATED_AT));
//                            // Launch Dashboard Screen
//                            Intent success = new Intent(getApplicationContext(), SuccessActivity.class);
//                            // Close all views before launching Dashboard
//                            success.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(success);
//                            // Close Registration Screen
//                            finish();
//                        }else{
//                            // Error in Address Form
//                            registerErrorMsg.setText("Please fill all Fields");
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });

    }
}
