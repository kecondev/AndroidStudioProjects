package kr.co.devinside.fragmentexample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FragmentExampleActivity extends FragmentActivity implements ToolbarFragment.ToolbarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_example);
    }

    @Override
    public void onButtonClick(int fontsize, String text) {
        TextFragment textFragment = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.text_fragment);
        textFragment.changeTextProperties(fontsize, text);
    }
}
