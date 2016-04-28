package jsoft.projects.photoclick.vk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import jsoft.projects.photoclick.MainActivity;
import jsoft.projects.photoclick.R;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditActivity extends Activity {
	ArrayList<NameValuePair> select_pro;
	EditText edNAME, edCITY, edEMAIL, edGENDER, edLOCATION;
	public static int mode;
	Bundle b;
	ArrayList<NameValuePair> global_web;
	ArrayList<NameValuePair> submit_web;
	Button btnSubmit;
	JSONObject JObject;
	JSONArray jArray;
	String ID, NAME, SOCIAL, CITY, EMAIL, GENDER, LOCATION;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		b = getIntent().getExtras();

		edNAME = (EditText) findViewById(R.id.editNAME);
		edCITY = (EditText) findViewById(R.id.editCITY);
		edEMAIL = (EditText) findViewById(R.id.editEMAIL);
		edGENDER = (EditText) findViewById(R.id.editGENDER);
		edLOCATION = (EditText) findViewById(R.id.editLOCATION);

		btnSubmit = (Button) findViewById(R.id.btnSAVE);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (b != null) {
					mode = b.getInt("mode");
					switch (mode) {
					case 0: {
						String fid = b.getString("FID");
						submit_web = new ArrayList<NameValuePair>();
						submit_web.add(new BasicNameValuePair("ID", fid));
						submit_web.add(new BasicNameValuePair("NAME", edNAME
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("CITY", edCITY
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("EMAIL", edEMAIL
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("GENDER",
								edGENDER.getText().toString()));
						submit_web.add(new BasicNameValuePair("LOCATION",
								edLOCATION.getText().toString()));
						updateWebservice uws = new updateWebservice();
						uws.CALL_WEB_SERVICE(submit_web);

						Toast.makeText(EditActivity.this,
								"Record updated successfully !!!",
								Toast.LENGTH_LONG).show();

						startActivity(new Intent(EditActivity.this,
								MainActivity.class));
					}

					case 1: {
						String tid = b.getString("TID");
						submit_web = new ArrayList<NameValuePair>();
						submit_web.add(new BasicNameValuePair("ID", tid));
						submit_web.add(new BasicNameValuePair("NAME", edNAME
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("CITY", edCITY
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("EMAIL", edEMAIL
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("GENDER",
								edGENDER.getText().toString()));
						submit_web.add(new BasicNameValuePair("LOCATION",
								edLOCATION.getText().toString()));
						updateWebservice uws = new updateWebservice();
						uws.CALL_WEB_SERVICE(submit_web);

						Toast.makeText(EditActivity.this,
								"Record updated successfully !!!",
								Toast.LENGTH_LONG).show();

						startActivity(new Intent(EditActivity.this,
								MainActivity.class));
					}

					case 2: {
						String gid = b.getString("GID");
						submit_web = new ArrayList<NameValuePair>();
						submit_web.add(new BasicNameValuePair("ID", gid));
						submit_web.add(new BasicNameValuePair("NAME", edNAME
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("CITY", edCITY
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("EMAIL", edEMAIL
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("GENDER",
								edGENDER.getText().toString()));
						submit_web.add(new BasicNameValuePair("LOCATION",
								edLOCATION.getText().toString()));
						updateWebservice uws = new updateWebservice();
						uws.CALL_WEB_SERVICE(submit_web);

						Toast.makeText(EditActivity.this,
								"Record updated successfully !!!",
								Toast.LENGTH_LONG).show();

						startActivity(new Intent(EditActivity.this,
								MainActivity.class));
					}

					case 3: {
						String lid = b.getString("LID");
						submit_web = new ArrayList<NameValuePair>();
						submit_web.add(new BasicNameValuePair("ID", lid));
						submit_web.add(new BasicNameValuePair("NAME", edNAME
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("CITY", edCITY
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("EMAIL", edEMAIL
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("GENDER",
								edGENDER.getText().toString()));
						submit_web.add(new BasicNameValuePair("LOCATION",
								edLOCATION.getText().toString()));
						updateWebservice uws = new updateWebservice();
						uws.CALL_WEB_SERVICE(submit_web);

						Toast.makeText(EditActivity.this,
								"Record updated successfully !!!",
								Toast.LENGTH_LONG).show();

						startActivity(new Intent(EditActivity.this,
								MainActivity.class));
					}

					case 4: {
						String vid = b.getString("VID");
						submit_web = new ArrayList<NameValuePair>();
						submit_web.add(new BasicNameValuePair("ID", vid));
						submit_web.add(new BasicNameValuePair("NAME", edNAME
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("CITY", edCITY
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("EMAIL", edEMAIL
								.getText().toString()));
						submit_web.add(new BasicNameValuePair("GENDER",
								edGENDER.getText().toString()));
						submit_web.add(new BasicNameValuePair("LOCATION",
								edLOCATION.getText().toString()));
						updateWebservice uws = new updateWebservice();
						uws.CALL_WEB_SERVICE(submit_web);

						Toast.makeText(EditActivity.this,
								"Record updated successfully !!!",
								Toast.LENGTH_LONG).show();

						startActivity(new Intent(EditActivity.this,
								MainActivity.class));
					}
					}
				}
			}
		});

		if (b != null) {
			mode = b.getInt("mode");
			switch (mode) {
			case 0: {
				String fid = b.getString("FID");
				global_web = new ArrayList<NameValuePair>();
				global_web.add(new BasicNameValuePair("ID", fid));
				selectWebservice ws = new selectWebservice();
				ws.CALL_WEB_SERVICE(global_web);

				GETDATA async = new GETDATA();
				async.execute();
			}

			case 1: {
				String tid = b.getString("TID");
				global_web = new ArrayList<NameValuePair>();
				global_web.add(new BasicNameValuePair("ID", tid));
				selectWebservice ws = new selectWebservice();
				ws.CALL_WEB_SERVICE(global_web);

				GETDATA async = new GETDATA();
				async.execute();
			}

			case 2: {
				String gid = b.getString("GID");
				global_web = new ArrayList<NameValuePair>();
				global_web.add(new BasicNameValuePair("ID", gid));
				selectWebservice ws = new selectWebservice();
				ws.CALL_WEB_SERVICE(global_web);

				GETDATA async = new GETDATA();
				async.execute();
			}

			case 3: {
				String lid = b.getString("LID");
				global_web = new ArrayList<NameValuePair>();
				global_web.add(new BasicNameValuePair("ID", lid));
				selectWebservice ws = new selectWebservice();
				ws.CALL_WEB_SERVICE(global_web);

				GETDATA async = new GETDATA();
				async.execute();
			}

			case 4: {
				String vid = b.getString("VID");
				global_web = new ArrayList<NameValuePair>();
				global_web.add(new BasicNameValuePair("ID", vid));
				selectWebservice ws = new selectWebservice();
				ws.CALL_WEB_SERVICE(global_web);

				GETDATA async = new GETDATA();
				async.execute();
			}
			}
		}
	}

	public class GETDATA extends AsyncTask<String, Void, Void> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(EditActivity.this);
			dialog.setMessage("Please wait");
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				JObject = new JSONObject(selectWebservice.result_select);

				// // STORE DATA IN FILE ....
				// FileOutputStream outputStream;
				// try {
				// outputStream = openFileOutput("result.txt",
				// Context.MODE_PRIVATE);
				// outputStream.write(oAuthWebService.result.getBytes());
				// outputStream.close();
				// } catch (Exception e) {
				// e.printStackTrace();
				// }

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}

				try {

					if (JObject.getString("status").equalsIgnoreCase("success")) {
						jArray = new JSONArray();
						jArray = JObject.getJSONArray("data");

						for (int i = 0; i < jArray.length(); i++) {

							ID = jArray.getJSONObject(i).getString("ID");
							NAME = jArray.getJSONObject(i).getString("NAME");
							SOCIAL = jArray.getJSONObject(i).getString("SOCIAL");
							CITY = jArray.getJSONObject(i).getString("CITY");
							EMAIL = jArray.getJSONObject(i).getString("EMAIL");
							GENDER = jArray.getJSONObject(i).getString("GENDER");
							LOCATION = jArray.getJSONObject(i).getString("LOCATION");

							edNAME.setText(NAME);
							edCITY.setText(CITY);
							edEMAIL.setText(EMAIL);
							edGENDER.setText(GENDER);
							edLOCATION.setText(LOCATION);

						}
					} else {
						Toast.makeText(EditActivity.this, "FAIL",
								Toast.LENGTH_LONG).show();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}