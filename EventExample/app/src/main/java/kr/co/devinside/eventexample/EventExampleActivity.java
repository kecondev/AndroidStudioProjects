package kr.co.devinside.eventexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_example);

        Button button = (Button)findViewById(R.id.myButton);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        TextView myTextView = (TextView)findViewById(R.id.myTxtView);
                        myTextView.setText("Button clicked!");
                    }
                }
        );

        button.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        TextView myTextView = (TextView)findViewById(R.id.myTxtView);
                        myTextView.setText("Long button click!");
                        return false;
                    }
                }
        );
    }
}
