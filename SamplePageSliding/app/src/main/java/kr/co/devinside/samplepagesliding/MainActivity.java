package kr.co.devinside.samplepagesliding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isPageOpen = false;

    Animation slidingAnimListener;

    Animation translateLeftAnim;
    Animation translateRightAnim;

    LinearLayout slidingPage01;
    Button button1;

    private GestureDetector mGestures = null;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingPage01 = (LinearLayout)findViewById(R.id.slidingPage01);
        button1 = (Button)findViewById(R.id.button1);

        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlidingPageAnimationListner animListner = new SlidingPageAnimationListner();
        translateLeftAnim.setAnimationListener(animListner);
        translateRightAnim.setAnimationListener(animListner);

        // 제스처로 인식하면 복잡한 이벤트 처리를 좀 더 간단하게 할 수 있습니다.
        mGestures = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    // fling 이벤트가 발생할 때 처리합니다.
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        //textView1.append("\nonFling \n\tvelocityX = " + velocityX + "\n\tvelocityY=" + velocityY);
                        /*if(isPageOpen){
                            slidingPage01.startAnimation(translateRightAnim);
                        } else {
                            slidingPage01.setVisibility(View.VISIBLE);
                            slidingPage01.startAnimation(translateLeftAnim);
                        }*/
                        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                            return false;

                        // right to left swipe
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                            if(!isPageOpen) {
                                slidingPage01.setVisibility(View.VISIBLE);
                                slidingPage01.startAnimation(translateLeftAnim);
                            }
                        }
                        // left to right swipe
                        else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                            if(isPageOpen)
                                slidingPage01.startAnimation(translateRightAnim);
                        }
                        // down to up swipe
                        else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                            Toast.makeText(getApplicationContext(), "Swipe up", Toast.LENGTH_SHORT).show();
                        }
                        // up to down swipe
                        else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                            Toast.makeText(getApplicationContext(), "Swipe down", Toast.LENGTH_SHORT).show();
                        }

                        return super.onFling(e1, e2, velocityX, velocityY);
                    }

                    // scroll 이벤트가 발생할 때 처리합니다.
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        //textView1.append("\nonScroll \n\tdistanceX = " + distanceX + "\n\tdistanceY = " + distanceY);

                        return super.onScroll(e1, e2, distanceX, distanceY);
                    }
                });
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mGestures != null) {
            return mGestures.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);
        }
    }

    public void onButton1Clicked(View v) {
        if(isPageOpen){
            slidingPage01.startAnimation(translateRightAnim);
        } else {
            slidingPage01.setVisibility(View.VISIBLE);
            slidingPage01.startAnimation(translateLeftAnim);
        }
    }

    private class SlidingPageAnimationListner implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isPageOpen) {
                slidingPage01.setVisibility(View.INVISIBLE);

                button1.setText("Open");
                isPageOpen = false;
            } else {
                button1.setText("Close");
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
