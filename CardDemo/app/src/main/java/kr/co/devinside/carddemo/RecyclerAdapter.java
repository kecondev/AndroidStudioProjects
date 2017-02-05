package kr.co.devinside.carddemo;

import android.support.v7.widget.RecyclerView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by DevInside on 2017. 2. 5..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] titles = {"Chapter One", "Chapter Two", "Chapter Three", "Chapter Four",
                               "Chapter Five", "Chapter Six", "Chapter Seven", "Chapter Eight"};

    private String[] details = {"Item one details", "Item two details", "Item three details", "Item four details",
                                "Item five details", "Item six details", "Item seven details", "Item eight details"};

    private int[] images = {R.drawable.android_image_1, R.drawable.android_image_2, R.drawable.android_image_3, R.drawable.android_image_4,
                            R.drawable.android_image_5, R.drawable.android_image_6, R.drawable.android_image_7, R.drawable.android_image_8 };
}
