package com.hzy.artical.network;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.hzy.artical.persistence.FileManager;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class DownloadUtil extends AsyncTask<String, Void, Boolean> {
	private Context ctx;
	private boolean result=false;


	public DownloadUtil(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String fileName = params[0].concat(".html");
			InputStream inputStream=null;
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse httpResponse = httpclient.execute(new HttpGet(params[1]));
				inputStream = httpResponse.getEntity().getContent();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			result= FileManager.getFileManager().writeFile(ctx, inputStream, fileName);
			return result;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Toast.makeText(ctx, "Start", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			Toast.makeText(ctx, "Finished", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(ctx, "Failed", Toast.LENGTH_SHORT).show();
		}
	}

}
