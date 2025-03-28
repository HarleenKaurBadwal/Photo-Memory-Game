package com.example.memorygame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView[] imageViews = new ImageView[8];
    private Integer[] imageResources = {
            R.drawable.pic1, R.drawable.pic1,
            R.drawable.pic2, R.drawable.pic2,
            R.drawable.pic3, R.drawable.pic3,
            R.drawable.pic5, R.drawable.pic5
    };
    private int firstSelection = -1;
    private boolean isBusy = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupGame();
    }

    private void setupGame() {
        // Shuffle Images
        List<Integer> imagesList = Arrays.asList(imageResources);
        Collections.shuffle(imagesList);
        imagesList.toArray(imageResources);

        // Initialize ImageViews
        for (int i = 0; i < imageViews.length; i++) {
            String imgID = "img" + i;
            int resID = getResources().getIdentifier(imgID, "id", getPackageName());
            imageViews[i] = findViewById(resID);
            imageViews[i].setImageResource(R.drawable.blank);
            imageViews[i].setTag(i);
            imageViews[i].setOnClickListener(this::imageClicked);
        }
    }

    private void imageClicked(View view) {
        if (isBusy) return;

        int index = (int) view.getTag();
        ((ImageView) view).setImageResource(imageResources[index]);

        if (firstSelection == -1) {
            firstSelection = index;
        } else if (firstSelection == index) {
            return;
        } else {
            isBusy = true;
            checkMatch(firstSelection, index);
            firstSelection = -1;
        }
    }

    private void checkMatch(int first, int second) {
        if (imageResources[first].equals(imageResources[second])) {
            Toast.makeText(this, "Matched!", Toast.LENGTH_SHORT).show();
            imageViews[first].setOnClickListener(null);
            imageViews[second].setOnClickListener(null);
            isBusy = false;
        } else {
            Toast.makeText(this, "Not Matched!", Toast.LENGTH_SHORT).show();
            handler.postDelayed(() -> {
                imageViews[first].setImageResource(R.drawable.blank);
                imageViews[second].setImageResource(R.drawable.blank);
                isBusy = false;
            }, 2000);
        }
    }
}
