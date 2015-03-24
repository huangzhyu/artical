/**
 * 
 */
package com.hzy.artical;

import java.util.ArrayList;

import com.hzy.artical.network.DownloadUtil;
import com.hzy.artical.util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author huangzha
 *
 */
public class DataReceiverActivity extends Activity implements View.OnClickListener{
	
	private ListView categories ;
	private Button saveBtn;
	private String[] params;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receiver_main);
		
		saveBtn = (Button) findViewById(R.id.save);
		
		saveBtn.setOnClickListener(this);
		
		categories = (ListView) findViewById(R.id.categories);
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,getResources().getStringArray(R.array.categories));
		
		categories.setAdapter(adapter);
		
		
		categories.setCacheColorHint(0);//设置拖动时ListView的背景
		
		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();


		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				handleSendText(intent); // Handle text being sent
			} else if (type.startsWith("image/")) {
				handleSendImage(intent); // Handle single image being sent
			}
		} else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
			if (type.startsWith("image/")) {
				handleSendMultipleImages(intent); // Handle multiple images
													// being sent
			}
		} else {
			// Handle other intents, such as being started from the home screen
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// TODO Auto-generated method stub

	}

	void handleSendText(Intent intent) {
		// String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		// if (sharedText != null) {
		// // Update UI to reflect text being shared
		// }
		ClipData clipData = intent.getClipData();
		if (clipData != null) {
			int count = clipData.getItemCount();
			if (count > 0) {
				ClipData.Item item = clipData.getItemAt(0);
				CharSequence str = item.coerceToText(this);
				if (str != null) {
					params = str.toString().split("\n\n");
					TextView title = (TextView) findViewById(R.id.title);
					title.setText(params[0]);
					TextView address = (TextView) findViewById(R.id.address);
					address.setText(params[1]);
				}

			}
		}

	}

	void handleSendImage(Intent intent) {
		Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {
			// Update UI to reflect image being shared
		}
	}

	void handleSendMultipleImages(Intent intent) {
		ArrayList<Uri> imageUris = intent
				.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		if (imageUris != null) {
			// Update UI to reflect multiple images being shared
		}
	}

	@Override
	public void onClick(View v) {
		if(Util.getUtil().checkNetworkState(this)){
//			int position = categories.getCheckedItemPosition();
			new DownloadUtil(this).execute(params);
			System.out.println(params[1]);
		}else{
			showDialog(0);
		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
        return new AlertDialog.Builder(this)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle(R.string.open_wifi)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	WifiManager wifiManager = (WifiManager)
    					getSystemService(Context.WIFI_SERVICE);
    					wifiManager.setWifiEnabled(true);
            }
        })
        .create();
	}

}
