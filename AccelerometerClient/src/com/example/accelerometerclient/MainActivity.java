package com.example.accelerometerclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity implements SensorEventListener {
	
	SensorManager sensorManager;
	private Sensor mAccelerometer;
	Socket pcserver = null;
    DataOutputStream os = null;
    DataInputStream is = null;
    private EditText mIpEditText;
    private Button connect;
    private Button close;
    private Button quit;
    
    private float accelX;
    private float accelY;
    private float accelZ;
	
	private final Handler h = new Handler()
	{
		public void handleMessage(Message msg)
		{
			String content = (String) msg.obj;
			Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
		}
	};
	
	public void alert(String message) {
        Message m = h.obtainMessage();
        m.obj = message;
        h.sendMessage(m);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("Teste 1");
		super.onCreate(savedInstanceState);
		System.out.println("Teste 2");
		setContentView(R.layout.activity_main);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		// Quit APP Connection button
        quit = (Button) findViewById(R.id.button3);
        quit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
                quitShop();
            }
        });
		
	}
	
	protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        getAddr();
    }
	
	private void getAddr(){
        //Connect Button Pressed - open connection to server
        // Get our EditText object.
        // Initialize the compose field with a listener for the return key
        mIpEditText = (EditText) findViewById(R.id.editText1);
       // mIpEditText.setOnEditorActionListener(mWriteListener);
 
        // Connect Button
        connect = (Button) findViewById(R.id.button1);
        connect.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                String ipAddr = mIpEditText.getText().toString();
                setupConnection(ipAddr);
            }
        });
 
     // Close Connection button
        close = (Button) findViewById(R.id.button2);
        close.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                closeShop();
            	
            }
        });
     
    }
	
	private void setupConnection(String ipAddr){
        //TextView view = (TextView) findViewById(R.id.msgView);
        try {
            pcserver = new Socket(ipAddr, 3141);
            System.out.println(pcserver.getLocalAddress().getAddress());
            os = new DataOutputStream(pcserver.getOutputStream());
            is = new DataInputStream(pcserver.getInputStream());
        } catch (UnknownHostException e) {
        	alert("Don't know about host: hostname");
        } catch (IOException e) {
        	alert("Couldn't get I/O for the connection to: hostname");
        }
             
        //TextView view = (TextView) findViewById(R.id.xval);
        //view.setText("Connected or at least tried...");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		System.out.println("Teste 0");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		 if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
	            return;
		
		 accelX = event.values[0];
		 accelY = event.values[1];
		 accelZ = event.values[2];

        
         sendingInfo(accelX, accelY, accelZ);
	}
	
	private void sendingInfo(float sensorsX, float sensorsY, float sensorsZ){
        if (pcserver != null && os != null && is != null) {
            try {
                //send the X sensor data
                os.writeFloat(sensorsX);
                //os.writeBytes(sensorsY);
                // clean up:
                // close the output stream
                // close the input stream
                // close the socket
                //os.close();
                //is.close();
                //pcserver.close();
            } catch (UnknownHostException e) {
                alert("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
            	alert("IOException:  " + e);
            }
            //view.setText("Sent stuff I guess");
        }
    }
	
	 private void closeShop(){
	     
	        if (pcserver != null && os != null && is != null) {
	            try {
	 
	                //os.writeBytes(sensorsX);
	                //os.writeBytes(sensorsY);
	                // clean up:
	                // close the output stream
	                // close the input stream
	                // close the socket
	                os.close();
	                is.close();
	                pcserver.close();
	            } catch (UnknownHostException e) {
	                //view.setText("Trying to connect to unknown host: " + e);
	            } catch (IOException e) {
	                //view.setText("IOException:  " + e);
	            }
	        }
	    }
	 
	 private void quitShop(){
	        //TextView view = (TextView) findViewById(R.id.msgView);
	        //view.setText("Exiting App");
		    System.out.println("Teste 4");
	        onDestroy();
	         
	    }

}
