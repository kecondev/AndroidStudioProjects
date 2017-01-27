package kr.co.devinside.mydeleayed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView tvInfo = null;
    Button btnRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = (TextView)findViewById(R.id.tvInfo);
        btnRequest = (Button)findViewById(R.id.btnRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }

    private  void request() {
        String title = "원격 요청";
        String message = "데이터를 요청하시겠습니까?";
        String titleButtonYes = "예";
        String titleButtonNo = "아니요";

        AlertDialog dialog = makeRequestDialog(title, message, titleButtonYes, titleButtonNo);
        dialog.show();

        tvInfo.setText("원격 데이터 요청 중...");
    }

    private AlertDialog makeRequestDialog(CharSequence title, CharSequence message,
                                          CharSequence titleButtonYes, CharSequence titleButtonNo) {
        AlertDialog.Builder requestDialog = new AlertDialog.Builder(this);
        requestDialog.setTitle(title);
        requestDialog.setMessage(message);
        requestDialog.setPositiveButton(titleButtonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {

                    }

                    tvInfo.setText("원격 데이터 요청 완료");
                }*/
                RequestHandler handler = new RequestHandler();
                handler.sendEmptyMessageDelayed(0, 20);
            }
        });

        requestDialog.setNegativeButton(titleButtonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return requestDialog.show();
    }

    class RequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    //tvInfo.setText("원격 데이터 요청 중..." + i);
                } catch (InterruptedException ex) {

                }

                tvInfo.setText("원격 데이터 요청 완료");
            }
        }
    }
}