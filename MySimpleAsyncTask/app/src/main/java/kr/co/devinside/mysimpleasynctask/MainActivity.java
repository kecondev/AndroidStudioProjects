package kr.co.devinside.mysimpleasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView textView01;
    ProgressBar progress;
    Button executeBtn;
    Button cancelBtn;
    Button statusBtn;

    //백그라운드 작업을 수행할 클래스를 선언
    BackgroundTask task;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView01 = (TextView)findViewById(R.id.textView01);
        progress = (ProgressBar)findViewById(R.id.progress);
        executeBtn = (Button)findViewById(R.id.executeBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        statusBtn = (Button)findViewById(R.id.statusBtn);

        executeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new BackgroundTask();
                task.execute(100);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancel()매서드를 호출하면 AsyncTask 작업을 취소할 수 있다.
                //이 매서드를 통해 작업을 취소하면 onCancelled()매서드가 호출된다.
                task.cancel(true);
            }
        });

        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task != null)
                //AsyncTask의 진행상태를 알고 싶으면
                //AsyncTask 객체의 getStatus()매서드를 호출하면 된다.
                //상태에 따라 PENDING, RUNNING, FINISHED를 반환한다.
                Toast.makeText(getApplicationContext(), task.getStatus().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //백그라운드 작업을 수행할 클래스를 정의
    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        //onPreExecute()매서드는 초기화 단계에서 사용된다.
        //value값을 0으로 초기화하고 진행바의 값을 0으로 설정한다.
        protected void onPreExecute() {
            value = 0;
            progress.setProgress(value);
        }

        //주 작업을 수행하는 매서드이다.
        protected Integer doInBackground(Integer ... values) {
            //작업이 취소되거나 value가 100 이상일 경우에 while문 종료
            while (isCancelled() == false) {
                value++;
                if(value >= 100) {
                    break;
                } else {
                    //진행상태를 업데이트 하기 위해 publishProgress를 호출
                    //publishProgress매서드가 호출되면 작업 중간에 UI 객체에 접근할 수 있다.
                    //publishProgress매서드는 onProgressUpdate를 호출한다.
                    publishProgress(value);
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
            }

            return value;
        }

        //publishProgress()매서드가 호출될 때 실행된다.
        protected void onProgressUpdate(Integer ... values) {
            progress.setProgress(values[0].intValue());
            textView01.setText("Current Value : " + values[0].toString());
        }

        //onPostExecute()매서드는 스레드에서 수행되던 작업이 종료되었을 때 호출된다.
        protected void onPostExecute(Integer result) {
            progress.setProgress(0);
            textView01.setText("Finished.");
        }

        //onCancelled()매서드는 스레드에서 수행되던 작업이 취소되었을 때 호출된다.
        protected void onCancelled() {
            progress.setProgress(0);
            textView01.setText("Cancelled.");
        }
    }
}
