package kr.co.devinside.mygraphanimation;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    Resources res;
    Animation growAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        growAnim = AnimationUtils.loadAnimation(this, R.anim.grow);
        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        addItem("Red", 80, Color.RED);
        addItem("Green", 100, Color.GREEN);
        addItem("Blue", 40, Color.BLUE);
    }

    private void addItem(String name, int value, int color) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 텍스트뷰 추가
        TextView textView = new TextView(this);
        textView.setText(name);
        params.width = 180;
        params.setMargins(0, 4, 0, 4);
        itemLayout.addView(textView, params);

        // 프로그레스바 추가
        ProgressBar proBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        proBar.setIndeterminate(false);
        proBar.setMax(100);
        proBar.setProgress(100);
        if(color == Color.RED)
            proBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color_red));
        else if(color == Color.GREEN)
            proBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color_green));
        else if(color == Color.BLUE)
            proBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color_blue));

        proBar.setAnimation(growAnim);

        params2.height = 3;
        params2.width = value * 3;
        params2.gravity = Gravity.CENTER_VERTICAL;
        itemLayout.addView(proBar, params2);

        mainLayout.addView(itemLayout, params3);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Toast.makeText(getApplicationContext(), "onWindowFocusChanged : " + hasFocus, Toast.LENGTH_SHORT).show();

        if(hasFocus) {
            growAnim.start();
        } else {
            growAnim.reset();
        }
    }
}
