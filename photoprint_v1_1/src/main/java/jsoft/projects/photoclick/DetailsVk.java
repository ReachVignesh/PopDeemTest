package jsoft.projects.photoclick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import jsoft.projects.photoclick.cart.OrderManager;
import jsoft.projects.photoclick.cart.ShoppingCart;
import jsoft.projects.photoclick.library.UserFunctionsAddressPay;
import jsoft.projects.photoclick.libs.ConnectionMngr;
import jsoft.projects.photoclick.libs.SessionMngr;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import util.DpsControlApplication;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailsVk extends Activity {

    public ImageLoader imageLoader = ImageLoader.getInstance();
    private int uid = 0;
    private ProgressDialog dialog = null;
    private int serverResponseCode = 0;
    public String imgPath = null;
    Context context;

    String upLoadServerUri = "http://photoclick.arion.kz/photo_android/UploadToServer.php";

    static SimpleDateFormat MsgSubTime = new SimpleDateFormat("ddMMyyyyhhmmssSSS");

    String time;
    Spinner Spinner_region;

    SessionMngr session;
    ShoppingCart cart;

    String lineEnd = "\r\n";
    String twoHypens = "--";
    String boundary = "*****";

    TextView tvMulMsg;
    EditText addressName;
    EditText regionName;
    EditText cityName;
    EditText streetName;
    EditText houseNumber;
    EditText apartNumber;
    EditText payableAmt;
    EditText commentBack;
    EditText phoneNo;
    CheckBox deliveryCheck;
    Button btnAddressSend;
    TextView registerErrorMsg,payable;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";

    private List<NameValuePair> nvpSizes;
    private static ArrayList<String> selectedItems = null;
    private FullScreenImageAdapter adapter;
    private boolean cartFlag = true;
    private ViewPager viewPager;
    private boolean fbImgs=false;
    ConnectionMngr cm;
    DpsControlApplication selection;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionMngr(getApplicationContext());
        cart = new ShoppingCart(getApplicationContext());
        cm = new ConnectionMngr(getApplicationContext());
        selection = (DpsControlApplication)getApplication();
        uid = session.getIntValues("uid");
        DpsControlApplication.exception_handler_home(this);

        context = this;

        selectedItems = new ArrayList<String>();

        Intent  intent = getIntent();

        fbImgs = intent.getBooleanExtra("fbImgs", false);

        @SuppressWarnings("unchecked")
        ArrayList<String> orderedItems = (ArrayList<String>) intent.getSerializableExtra("orderedItems");
        if(orderedItems !=null ){
            selectedItems = orderedItems;
            cartFlag = false;
        }else{
            selectedItems = cart.getCartImages();
        }
//		Log.d("Selected Items in detail Page: Line no 82", selectedItems.toString());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.details);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(selectedItems.size()>0){
            Toast.makeText(getApplicationContext(), "Please fill Address form.", Toast.LENGTH_LONG).show();
        }

        // 	If is in database add to the list;

        viewPager = (ViewPager) findViewById(R.id.pager);
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        adapter = new FullScreenImageAdapter(DetailsVk.this, selectedItems);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        // Importing all assets like buttons, text fields
        addressName = (EditText) findViewById(R.id.addressName);
        regionName = (EditText) findViewById(R.id.regionName);
        cityName = (EditText) findViewById(R.id.cityName);
        streetName = (EditText) findViewById(R.id.streetName);
        houseNumber = (EditText) findViewById(R.id.houseNumber);
        apartNumber = (EditText) findViewById(R.id.apartNumber);
        payableAmt = (EditText) findViewById(R.id.payable_Amt);
        commentBack = (EditText) findViewById(R.id.comment_Back);
        phoneNo = (EditText) findViewById(R.id.phoneNumber);
        deliveryCheck = (CheckBox) findViewById(R.id.deliveryCheck);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        payable =(TextView)findViewById(R.id.payable);
        Button button = (Button) findViewById(R.id.btnAddressSend);
        deliveryCheck.setText("       "+getString(R.string.delivery));
        Spinner_region = (Spinner) findViewById(R.id.regionSpinner);


        if(selection.getPreferences().getString("free","").equalsIgnoreCase("btnfree"))   {
            payable.setVisibility(View.GONE);
            commentBack.setVisibility(View.GONE);
            type = "free";

        }  else  if (selection.getPreferences().getString("free","").equalsIgnoreCase("btnpay"))
        {
            payable.setVisibility(View.VISIBLE);
            commentBack.setVisibility(View.VISIBLE);
            type = "pay";
        }

        payable.setText(selection.getPreferences().getString("Amt",""));

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnChoosePhotosClick(view);
                }
                return false;
            }
        });
    }

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu){
		if(cartFlag == true){
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.details_action_bar, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}*/


    public void btnChoosePhotosClick(View v){

        if(!cm.isOnline()){
            Toast toast = Toast.makeText(getApplicationContext(), "Not connected to network", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 150);
            toast.show();
            return;
        }

        if(addressName.getText().toString().isEmpty()){

            showtoast("Please enter your name");

        }  else if(regionName.getText().toString().isEmpty()){

            showtoast("Please enter Region name");

        }  else if(cityName.getText().toString().isEmpty()){
            showtoast("Please enter City name");

        }  else if(streetName.getText().toString().isEmpty()){
            showtoast("Please enter Street name");

        }else if(houseNumber.getText().toString().isEmpty()){
            showtoast("Please enter House number");

        }else if(apartNumber.getText().toString().isEmpty()){
            showtoast("Please enter Apartment number");
        }
               /*else if(commentBack.getText().toString().isEmpty()){
                   showtoast("Please enter Comment");
               }*/
        else {

            nvpSizes = new ArrayList<NameValuePair>();
            for (String key : adapter.checkedQty.keySet()) {
                String size = key.substring(3);
                nvpSizes.add(new BasicNameValuePair(adapter.checkedQty.get(key), size)); //qty , sizeId
                Log.d("Checked Count:", key + "=>" + adapter.checkedQty.get(key));
            }

            Log.d("Checked Values", adapter.checked.toString());
            Log.d("Checked Qty", adapter.checkedQty.toString());

            cart.updateImgInfo(adapter.checked, adapter.checkedQty);

            if (selectedItems.size() > 0) {
                dialog = ProgressDialog.show(this, (getResources().getString(R.string.generating)), (getResources().getString(R.string.uploading_file)), true);
                new Thread(new Runnable() {
                    public void run() {
                        time =  getTimewithMilliSeconds();
                        UploadFile(selectedItems, nvpSizes);
                        onAddress();
                    }
                }).start();
            } else {
                Toast.makeText(getApplicationContext(), "No items in cart please add some images.", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void showtoast(String toast){
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
    }


	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		nvpSizes = new ArrayList<NameValuePair>();
			for(String key : adapter.checkedQty.keySet()){
				String size = key.substring(3);
				nvpSizes.add(new BasicNameValuePair(adapter.checkedQty.get(key),size)); //qty , sizeId
				Log.d("Checked Count:",key+"=>"+adapter.checkedQty.get(key));
			}


		Log.d("Checked Values",adapter. checked.toString());
		Log.d("Checked Qty", adapter.checkedQty.toString());

		cart.updateImgInfo(adapter.checked, adapter.checkedQty);

		switch(item.getItemId()){
			case R.id.action_check_out:

				if(!cm.isOnline()){
					Toast toast = Toast.makeText(getApplicationContext(), "Not connected to network", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 150);
					toast.show();
					return false;
				}
				if(selectedItems.size()>0){
					dialog = ProgressDialog.show(this, "Placing Order", "Please wait. Uploading file...",true);
					new Thread(new Runnable(){
						public void run(){
							UploadFile(selectedItems, nvpSizes);
						}
					}).start();
					}
				else{
					Toast.makeText(getApplicationContext(), "No items in cart please add some images.", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.action_add_more:
				selectedItems = null;
				finish();
				Intent intent = new Intent(Details.this, WelcomeActivity.class);
				startActivity(intent);
				break;
			case R.id.action_delete_cart:
				OrderManager om = new OrderManager(getApplicationContext());
				om.open();
				om.delOrderDetails();
				om.delOrderItem();
				om.close();
				Intent i1 = new Intent(Details.this, WelcomeActivity.class);
				startActivity(i1);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}*/


    public void onAddress() {
        String name = addressName.getText().toString();
        String region = regionName.getText().toString();
        String city = cityName.getText().toString();
        String street = streetName.getText().toString();
        String house = houseNumber.getText().toString();
        String apart = apartNumber.getText().toString();
        String payable1 = payable.getText().toString();
        String comment = commentBack.getText().toString();
        String phone = phoneNo.getText().toString();
        String uid1 =  String.valueOf(uid);

        String key;
        if(deliveryCheck.isChecked()) {
            key = "Yes";
        } else {
            key = "No";
        }
        UserFunctionsAddressPay userFunctionAddressPay = new UserFunctionsAddressPay();
        userFunctionAddressPay.addressUserPay(uid1,name, region, city, street, house, apart, key, payable1, comment,phone ,time,type);
    }


    protected int UploadFile(ArrayList<String> sourceFileUri, List<NameValuePair> nvpSizes){

        String fileName;// = sourceFileUri.get(2).toString();

        deliveryCheck = (CheckBox) findViewById(R.id.deliveryCheck);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        File sourceFile = null;
        UrlEncodedFormEntity form;


        try{

            // Open a URL connection to the Servlet
            FileInputStream fileInputStream = null;
            URL url = new URL(upLoadServerUri);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached copy
            conn.setRequestMethod("POST"); //Sets connection method
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHypens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uid\""+lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Integer.toString(uid));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHypens + boundary + lineEnd);

                        /*dos.writeBytes("Content-Disposition: form-data; name=\"add_name\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(addressName.getText().toString());
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"region_name\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(regionName.getText().toString());
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"city_name\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cityName.getText().toString());
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"street_name\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(streetName.getText().toString());
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"house_number\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(houseNumber.getText().toString());
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"apart_number\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(apartNumber.getText().toString());
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"check\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(key);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"payable_amt\""+lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(payable.getText().toString());
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);        */
            dos.writeBytes("Content-Disposition: form-data; name=\"time\""+lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(time);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHypens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"print_type\""+lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(type);
            dos.writeBytes(lineEnd);

            for (String aSourceFileUri : sourceFileUri) {

                sourceFile = new File(aSourceFileUri);

                if (!_ifIsFile(sourceFile)) {
                    return 0;
                }

                fileName = sourceFile.toString();

                OrderManager om = new OrderManager(getApplicationContext());
                om.open();
                long oiid = om.getOrderItemIdByImage(fileName);
                om.close();

                fileInputStream = new FileInputStream(sourceFile);
                //conn.setRequestProperty("uploaded_file[]", fileName);

                String qty;
                String size;
                for (int j = 0; j < nvpSizes.size(); j++) {
                    qty = nvpSizes.get(j).getName();
                    size = nvpSizes.get(j).getValue();

                    String[] tokens = size.split("_");
                    if (Long.parseLong(tokens[0]) == oiid) {

                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"size[]\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(size);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHypens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"qty[]\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(qty);
                        dos.writeBytes(lineEnd);
                    }
                }
//						dos.writeBytes(lineEnd);
                dos.writeBytes(twoHypens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file[]\";filename=\"" + fileName + "\"" + lineEnd);
                //dos.writeBytes("Content-Disposition: form-data; name=\"uid\";filename=\"" + "rabin"+"\""+lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                //dos.writeBytes(lineEnd);
                dos.writeBytes(twoHypens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uid\";value=\"photo click\"" + lineEnd);
                dos.writeBytes("Content-Type: text/html; charset="+ HTTP.UTF_8 + lineEnd);
                dos.writeBytes("Content-Transfer-Encoding: 8bit" + lineEnd);

                dos.writeBytes(lineEnd);
            }
            // send multipart form data necessary after file data
            dos.writeBytes(twoHypens+boundary+twoHypens+lineEnd);

            // Responses from the server(code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();



            Log.i("uploadFile","HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            if(serverResponseCode == 200){ //if http response is created
                runOnUiThread(new Runnable(){
                    public void run(){
                        String msg = (getResources().getString(R.string.order_success));
                        Toast.makeText(DetailsVk.this, msg, Toast.LENGTH_SHORT).show();

                        ShoppingCart cart = new ShoppingCart(getApplicationContext());
                        cart.deleteItems();
                        finish();
                        if(fbImgs == true){
//									DeleteFbImages();
//                                    DeleteVkImages();
//                                    DeleteInstaImages();
                        }
                        dialog.dismiss();
                        Intent i = new Intent(DetailsVk.this, SuccessActivity.class);
                        startActivity(i);
                    }
                });
            }
            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (MalformedURLException ex) {

            dialog.dismiss();
            ex.printStackTrace();

            runOnUiThread(new Runnable() {
                public void run() {
                    tvMulMsg.setText("MalformedURLException Exception : check script url.");
                    //Toast.makeText(Gallery.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                }
            });

            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

            dialog.dismiss();
            e.printStackTrace();

            runOnUiThread(new Runnable() {
                public void run() {
                    tvMulMsg.setText("Got Exception with server");
                    //Toast.makeText(context, "Got Exception with server ", Toast.LENGTH_SHORT).show();
                }
            });
            Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);
        }
        dialog.dismiss();
        return serverResponseCode;
    }

    public static String getTimewithMilliSeconds() {
        Calendar c = Calendar.getInstance();
        String formattedDate = MsgSubTime.format(c.getTime());
        return formattedDate;
    }

    public boolean _ifIsFile(File file){
        if(!file.isFile()){
            dialog.dismiss();
            if(checkForURLFile(file.toString())){
                return true;
            }

            Log.e("uploadFile", "Source File not exist : " + imgPath);

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    tvMulMsg.setText("Source File not exist : " + imgPath);
                }
            });
            return false;
        }
        return true;
    }

    public static void DeleteFbImages(){

        for(int i=0 ;i< selectedItems.size();i++){
            String delImg = selectedItems.get(i);
            DeleteFile(delImg);
        }

        File path = new File (Environment.getExternalStorageDirectory().toString()+"/fb_images");
        if(path.exists()){
            path.delete();
        }

    }
    public static void DeleteVkImages(){

        for(int i=0 ;i< selectedItems.size();i++){
            String delImg = selectedItems.get(i);
            DeleteFile(delImg);
        }

        File path = new File (Environment.getExternalStorageDirectory().toString()+"/vk_images");
        if(path.exists()){
            path.delete();
        }

    }
    public static void DeleteInstaImages(){

        for(int i=0 ;i< selectedItems.size();i++){
            String delImg = selectedItems.get(i);
            DeleteFile(delImg);
        }

        File path = new File (Environment.getExternalStorageDirectory().toString()+"/instagram_images/");
        if(path.exists()){
            path.delete();
        }

    }

    public static void DeleteFile(String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            System.out.println("not exists");
            return;
        }
        if(!file.isDirectory()){
            System.out.println("Deleted");
            file.delete();
            return;
        }
    }

    public boolean checkForURLFile(String url){
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            // HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if(checked)
            Toast.makeText(getApplicationContext(), Long.toString(view.getId()), Toast.LENGTH_LONG).show();

        switch(view.getId()){

        }
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        OrderManager om = new OrderManager(getApplicationContext());
        om.open();
        om.delOrderDetails();
        om.delOrderItem();
        om.close();
//        Intent i = new Intent(Details.this, Dashboard.class);
        DetailsVk.this.finish();
//        startActivity();
//        super.onBackPressed();

    }

}