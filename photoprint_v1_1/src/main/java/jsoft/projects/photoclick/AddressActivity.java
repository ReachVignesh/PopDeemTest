package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import jsoft.projects.photoclick.library.UserFunctionsAddress;
import org.json.JSONException;
import org.json.JSONObject;

public class AddressActivity extends Activity {
    Button btnAddressSend;
    EditText addressName;
    EditText regionName;
    EditText cityName;
    EditText streetName;
    EditText houseNumber;
    EditText apartNumber;
    TextView registerErrorMsg;
    CheckBox deliveryCheck1;
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
    private static String KEY_CREATED_AT = "created_at";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addressform);

        // Importing all assets like buttons, text fields
        addressName = (EditText) findViewById(R.id.addressName);
        regionName = (EditText) findViewById(R.id.regionName);
        cityName = (EditText) findViewById(R.id.cityName);
        streetName = (EditText) findViewById(R.id.streetName);
        houseNumber = (EditText) findViewById(R.id.houseNumber);
        apartNumber = (EditText) findViewById(R.id.apartNumber);
        btnAddressSend = (Button) findViewById(R.id.btnAddressSend);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        deliveryCheck1 = (CheckBox) findViewById(R.id.deliveryCheckBox);

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

                String key;
                if(deliveryCheck1.isChecked()) {
                    // Use the key 1 for your webservice
                    key = "Yes";
                } else {
                    // Use the key 2 for your webservice
                    key = "No";
                }
                UserFunctionsAddress userFunctionAddress = new UserFunctionsAddress();
                JSONObject json = userFunctionAddress.addressUser(name, region, city, street, house, apart, key);
//                , region, city, street, house, apart, key
                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        registerErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully registered
                            // Store user details in SQLite Database
//                            DatabaseHandlerAddress db = new DatabaseHandlerAddress(getApplicationContext());
//                            JSONObject json_address = json.getJSONObject("user");

                            // Clear all previous data in database
//                            userFunctionAddress.logoutAddress(getApplicationContext());
//                            db.addAddress(json_address.getString(KEY_ADDRESSNAME),json_address.getString(KEY_REGIONNAME), json_address.getString(KEY_CITYNAME), json_address.getString(KEY_STREETNAME), json_address.getString(KEY_HOUSENUMBER), json_address.getString(KEY_APARTNUMBER),json_address.getString(KEY_DELIVERYCHECK), json.getString(KEY_UID), json_address.getString(KEY_CREATED_AT));
//                            json_address.getString(KEY_REGIONNAME), json_address.getString(KEY_CITYNAME), json_address.getString(KEY_STREETNAME), json_address.getString(KEY_HOUSENUMBER), json_address.getString(KEY_APARTNUMBER),json_address.getString(KEY_DELIVERYCHECK),
                            // Launch Dashboard Screen
                            Intent success = new Intent(getApplicationContext(), SuccessActivity.class);
                            // Close all views before launching Dashboard
                            success.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(success);
                            // Close Registration Screen
                            finish();
                        }else{
                            // Error in Address Form
                            registerErrorMsg.setText("Please fill all Fields");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
//            adapter.clear();
//
//        } else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
//            String[] all_path = data.getStringArrayExtra("all_path");
//
//            ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();
//
//            for (String string : all_path) {
//                CustomGallery item = new CustomGallery();
//                item.sdcardPath = string;
//
//                dataT.add(item);
//            }
//        }
//    }
}
