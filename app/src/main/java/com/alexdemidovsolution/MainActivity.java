package com.alexdemidovsolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textToMove;
    Button btnEnglish;
    Button btnRussian;
    Context context;
    Resources resources;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;

        textToMove = findViewById(R.id.moving_text);
        btnEnglish = findViewById(R.id.english_button);
        btnRussian = findViewById(R.id.russian_button);

        //switching to English button
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(MainActivity.this, "en");
                resources = context.getResources();
                textToMove.setText(resources.getString(R.string.language));
            }
        });

        //switching to Russian button
        btnRussian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(MainActivity.this, "ru");
                resources = context.getResources();
                textToMove.setText(resources.getString(R.string.language));
            }
        });

        //stop animation on click
        textToMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToMove.animate().cancel();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            textToMove.setX(event.getRawX());
            textToMove.setY(event.getRawY());

            textToMove.setTextColor(resources.getColor(R.color.color_to_change));

            textToMove.animate().setStartDelay(5000);
            startAnimation();
        }

        return true;
    }

    public void startAnimation() {
        textToMove.animate().setDuration(3000);
        textToMove.animate().translationY(screenHeight).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                textToMove.animate().setStartDelay(0);
                textToMove.animate().translationY(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        textToMove.animate().translationY(screenHeight);
                    }
                });
            }
        });
    }
}