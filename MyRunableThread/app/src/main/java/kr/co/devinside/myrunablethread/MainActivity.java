package kr.co.devinside.myrunablethread;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    boolean isRunning = false;
    TextView txtView = null;
    ProgressBar progressBar = null;

    Handler handler = null;
    ProgressRunnable progressRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView)findViewById(R.id.txtView);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        handler = new Handler();
        progressRunnable = new ProgressRunnable();
    }

    public void onStart() {
        super.onStart();

        progressBar.setProgress(0);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0; i < 20 && isRunning; i++) {
                        Thread.sleep(1000);

                        handler.post(progressRunnable);
                    }
                } catch (Exception e) {

                }
            }
        });

        isRunning = true;
        thread.start();
    }

    public void onStop() {
        super.onStop();

        isRunning = false;
    }

    public class ProgressRunnable implements Runnable {
        public void run() {
            progressBar.incrementProgressBy(5);

            if(progressBar.getProgress() == progressBar.getMax()) {
                txtView.setText("Runnable Done");
            } else {
                txtView.setText("Runnable Working ..." + progressBar.getProgress());
            }
        }
    }
}
