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

public class MyArduinoBluetoothControlActivity extends Activity {

	TextView tvBluetooth;
	TextView tvDevice;
	
	private BluetoothAdapter mBluetoothAdapter = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.info_not_use, Toast.LENGTH_LONG).show();
            finish();
            return;
        }           
        
        ImageButton ibtnLCD = (ImageButton)findViewById(R.id.btnLCD);
        ibtnLCD.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), BluetoothLCDActivity.class);
	            startActivity(intent);
			}
		});  
        
        ImageButton ibtnControler = (ImageButton)findViewById(R.id.btnController);
        ibtnControler.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), BluetoothControllerActivity.class);
	            startActivity(intent);
			}
		}); 
        
        ImageButton ibtnThermometer = (ImageButton)findViewById(R.id.btnThermometer);
        ibtnThermometer.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), BluetoothThermometerActivity.class);
	            startActivity(intent);
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
        } 
    }    
    
    @Override
    public synchronized void onResume() {
        super.onResume();        
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
    }     
}