package com.scott.herbert.AnDOSid;

import org.achartengine.chart.LineChart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.net.MalformedURLException;


public class AnDOSid extends Activity {
	
	String IMEI = "";
	String androidId = "";
	
	private static final int ANDOSID_ID = 21441109;
	
	DOSdata localDOSdata = new DOSdata();

	
	static final int DIALOG_INVALID_URL = 0;
	static final int DIALOG_INVALID_PAYLOAD_SIZE = 1;
	static final int DIALOG_AT_OWN_RISK = 2;
	static final int DIALOG_INVALID_TIMEER = 3;
	

	protected Dialog onCreateDialog(int id) {
	    Dialog dialog;
		switch(id) {
	    case DIALOG_INVALID_URL:
	    	AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
	    	builder2.setMessage(R.string.AnDOSid_error1)
	    		    .setPositiveButton(R.string.AnDOSid_OK, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog alert2 = builder2.create();
	    	alert2.show();
	    	dialog = alert2;
	        break;
	    case DIALOG_INVALID_PAYLOAD_SIZE:
	    	AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
	    	builder3.setMessage(R.string.AnDOSid_error2)
	    		    .setPositiveButton(R.string.AnDOSid_OK, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog alert3 = builder3.create();
	    	alert3.show();
	    	dialog = alert3;
	        break;
	    case DIALOG_AT_OWN_RISK:
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.AnDOSid_welcome_msg)
	    	       .setCancelable(false)
	    	       .setNegativeButton(R.string.AnDOSid_Quit, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                AnDOSid.this.finish();
	    	           }
	    	       })
	    	       .setPositiveButton(R.string.AnDOSid_Continue, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog alert1 = builder.create();
	    	alert1.show();
	    	dialog = alert1;
	        break;
	    case DIALOG_INVALID_TIMEER:
	    	AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
	    	builder4.setMessage(R.string.AnDOSid_error3)
	    		    .setPositiveButton(R.string.AnDOSid_OK, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog alert4 = builder4.create();
	    	alert4.show();
	    	dialog = alert4;
	        break;
	    default:
	        dialog = null;
	    }
	    return dialog;
	}
	
	private OnClickListener OKbtnClick = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			// OK btn clicked so we need to disable the OK button and everything other than the stop btn
			// and start the service
			
			Boolean flag;
	    	EditText targetURL;
	    	EditText payloadSize;
	    	EditText etMilliseconds;
			flag = true;
	    	Button OKbtn;
	    	Button stopbtn;
	        
	        //Allocate buttons and editboxes
	        OKbtn = (Button)findViewById(R.id.btnGo);
	        stopbtn = (Button)findViewById(R.id.btnStop);
	        targetURL = (EditText)findViewById(R.id.TargetURL);
	        payloadSize = (EditText)findViewById(R.id.editText2);
	        etMilliseconds = (EditText)findViewById(R.id.milliseconds);
		
			try {
				@SuppressWarnings("unused")
				URL test = new URL(targetURL.getText().toString());
				
			} catch(MalformedURLException e) {
				// display not URL error
				flag = false;
				onCreateDialog(DIALOG_INVALID_URL);
			}
			
			try  
			{  
			    @SuppressWarnings("unused")
				double d = Double.parseDouble(payloadSize.getText().toString());  
			}  
			  catch(NumberFormatException nfe)  
			{  
					// display not URL error
					flag = false;
					onCreateDialog(DIALOG_INVALID_PAYLOAD_SIZE);
			}
				try  
				{  
				    @SuppressWarnings("unused")
					double d = Double.parseDouble(etMilliseconds.getText().toString());  
				}  
				  catch(NumberFormatException nfe)  
				{  
						// display not URL error
						flag = false;
						onCreateDialog(DIALOG_INVALID_TIMEER);
				}
			if (flag) {  
				Intent myIntent = new Intent(getApplication(), DOService.class);
				myIntent.putExtra("DTarget", targetURL.getText().toString());
				myIntent.putExtra("DIMEI", localDOSdata.getIMEI());
				myIntent.putExtra("DandroidId", localDOSdata.getAndroidId());
				myIntent.putExtra("DPayloadSize", payloadSize.getText().toString());
				myIntent.putExtra("DMilliseconds", etMilliseconds.getText().toString());
	        	OKbtn.setEnabled(false);
	        	targetURL.setEnabled(false);
	        	payloadSize.setEnabled(false);
	        	
	        	stopbtn.setEnabled(true);
				startService(myIntent);
				
	        	Context context = getApplicationContext();
	        	CharSequence text = getResources().getText(R.string.AnDOSid_started);
	        	int duration = Toast.LENGTH_SHORT;

	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.show();
			}
		}
		
	};
	
	private OnClickListener StopbtnClick = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			// we need to disable the stop btn enable everything else and kill the service
	    	Button OKbtn;
	    	Button stopbtn;
	    	EditText targetURL;
	    	EditText payloadSize;
	        
	    	//Stop service
	    	Intent myIntent = new Intent(getApplication(), DOService.class);
	    	stopService(myIntent);
	    	
	        //Allocate buttons and editboxes
	        OKbtn = (Button)findViewById(R.id.btnGo);
	        stopbtn = (Button)findViewById(R.id.btnStop);
	        targetURL = (EditText)findViewById(R.id.TargetURL);
	        payloadSize = (EditText)findViewById(R.id.editText2);
        	OKbtn.setEnabled(true);
        	targetURL.setEnabled(true);
        	payloadSize.setEnabled(true);
        	stopbtn.setEnabled(false);
        	
        	Context context = getApplicationContext();
        	CharSequence text = getResources().getText(R.string.AnDOSid_stopped_);
        	int duration = Toast.LENGTH_SHORT;

        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
        	
        	// And close the notification 
        	String ns = Context.NOTIFICATION_SERVICE;
    		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
    		mNotificationManager.cancel(ANDOSID_ID);
		}
		
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	Button OKbtn;
    	Button stopbtn;
    	EditText targetURL;
    	EditText payloadSize;
        
        //Allocate buttons and editboxes
        OKbtn = (Button)findViewById(R.id.btnGo);
        stopbtn = (Button)findViewById(R.id.btnStop);
        targetURL = (EditText)findViewById(R.id.TargetURL);
        payloadSize = (EditText)findViewById(R.id.editText2);
        
        
		int isRunning = 0;
        Intent myIntent = getIntent(); // this getter is just for example purpose, can differ
        
        if (myIntent !=null && myIntent.getExtras()!=null){
        	isRunning = myIntent.getExtras().getInt("AR",0);
        }
         
        if (isRunning == 1){
        	OKbtn.setEnabled(false);
        	targetURL.setEnabled(false);
        	payloadSize.setEnabled(false);
        	
        	stopbtn.setEnabled(true);
        } else {
        
        	// Were not running disable the stop button
        	OKbtn.setEnabled(true);
        	targetURL.setEnabled(true);
        	payloadSize.setEnabled(true);
        	
        	stopbtn.setEnabled(false);
        	// 		Also display the "at your own risk" warning.
        	onCreateDialog(DIALOG_AT_OWN_RISK);
        }
        
        // Attach the code to the buttons
        OKbtn.setOnClickListener(OKbtnClick);
        stopbtn.setOnClickListener(StopbtnClick);
        
        
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        IMEI = "" + tm.getDeviceId();
        if(IMEI==""){
        	IMEI = "None";
        }
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if(androidId==""){
        	androidId = "None";
        }
         
        localDOSdata.setAndroidId(androidId);
        localDOSdata.setIMEI(IMEI);
        
    }
    
    protected void onStop(){
    	super.onStop();
    	/*  if we exit stop the service. 
    	try {
    		Intent myIntent = new Intent(getApplication(), DOService.class);
	    	stopService(myIntent);
    	} catch(Exception e) {
    		
    	}
    */
    }
    
}