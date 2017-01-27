package kr.co.devinside.mythread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    ProgressBar bar = null;
    TextView txtView = null;

    boolean isRunning = false;

    ProgressHandler progressHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressHandler = new ProgressHandler();
        bar = (ProgressBar)findViewById(R.id.progressbar);
        txtView = (TextView)findViewById(R.id.txtView);
    }

    public void onStart() {
        super.onStart();

        bar.setProgress(0);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < 20 && isRunning; i++) {
                        Thread.sleep(1000);
                        Message msg = progressHandler.obtainMessage();
                        progressHandler.sendMessage(msg);
                    }
                } catch (Exception e) {

                }
            }
        });

        isRunning = true;
        thread1.start();
    }

    public void onStop() {
        super.onStop();

        isRunning = false;
    }

    public class ProgressHandler extends Handler {
        public void handleMessage(Message msg) {
            bar.incrementProgressBy(5);
            if(bar.getProgress() == bar.getMax()) {
                txtView.setText("Done");
            } else {
                txtView.setText("Working ..." + bar.getProgress());
            }
        }
    }
}

