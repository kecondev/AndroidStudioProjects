package DevInside.Android.ArduinoControl;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothThermometerActivity extends Activity  {
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mThermometerChatService = null;
    
    private TextView tvThermometer;
    
    Intent deviceIntent = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.bluetooththermometer);
    
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.info_not_use, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        ImageButton ibtnSecureTermometer = (ImageButton)findViewById(R.id.btnSecureTermometer);
        ibtnSecureTermometer.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				deviceIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
	            startActivityForResult(deviceIntent, BluetoothDef.REQUEST_CONNECT_DEVICE_SECURE);
			}
		});     
        
        ImageButton ibtnInSecureTermometer = (ImageButton)findViewById(R.id.btnInSecureTermometer);
        ibtnInSecureTermometer.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				deviceIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
	            startActivityForResult(deviceIntent, BluetoothDef.REQUEST_CONNECT_DEVICE_INSECURE);
			}
		});         
    }
    
    @Override
    public void onStart() {
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, BluetoothDef.REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mThermometerChatService == null) setupChat();        	
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        if (mThermometerChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mThermometerChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
            	mThermometerChatService.start();
            }
        }        
    }    
    
    private void setupChat() {
        // Initialize the compose field with a listener for the return key
    	tvThermometer = (TextView) findViewById(R.id.tvdatacurrent);
    	tvThermometer.setText("");
    	
        // Initialize the BluetoothChatService to perform bluetooth connections
    	mThermometerChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }    
    
    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mThermometerChatService != null) mThermometerChatService.stop();
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }    
    
    private final void setStatus(int resId) {
        //final ActionBar actionBar = getActionBar();
        //actionBar.setSubtitle(resId);
    	Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    private final void setStatus(CharSequence subTitle) {
        //final ActionBar actionBar = getActionBar();
        //actionBar.setSubtitle(subTitle);
    	Toast.makeText(getApplicationContext(), subTitle, Toast.LENGTH_SHORT).show();
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case BluetoothDef.MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));                    
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    setStatus(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    setStatus(R.string.title_not_connected);
                    break;
                }
                break;
            case BluetoothDef.MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                tvThermometer.setText(readMessage);
                break;
            case BluetoothDef.MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(BluetoothDef.DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case BluetoothDef.MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(BluetoothDef.TOAST), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case BluetoothDef.REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
        case BluetoothDef.REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, false);
            }
            break;
        case BluetoothDef.REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occurred
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mThermometerChatService.connect(device, secure);
    }    
}
