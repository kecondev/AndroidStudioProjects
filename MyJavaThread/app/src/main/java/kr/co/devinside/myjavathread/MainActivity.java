package kr.co.devinside.myjavathread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    int value = 0;
    boolean bRunning = false;

    Button btnStart = null;
    Button btnGet = null;
    TextView txtView = null;

    Thread thread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnGet = (Button)findViewById(R.id.btnGet);

        txtView = (TextView)findViewById(R.id.txtValue);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread = new BackgroundThread();
                thread.start();
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtView.setText(Integer.toString(value));
            }
        });
    }

    protected  void onResume() {
        super.onResume();

        bRunning = true;
    }

    protected void onPause() {
        super.onPause();

        bRunning = false;
        value = 0;
    }

    class BackgroundThread extends Thread {
        public void run() {
            while (bRunning) {
                try {
                    Thread.sleep(1000);
                    value++;
                } catch (InterruptedException e) {
                    Log.e("MyJavaThread", "Exception in thread.", e);
                }
            }
        }
    }
}
