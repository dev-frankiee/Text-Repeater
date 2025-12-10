package com.example.textrepeater;

import android.os.Bundle;
import android.view.WindowManager;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.view.View;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // Optional: Make it full screen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Start animations
        startAnimations();

        // Go to main activity after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(Launcher.this, Login.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 3200); // Total animation time
    }

    private void startAnimations() {
        View logo = findViewById(R.id.logo);
        View appName = findViewById(R.id.app_name);
        View tagline = findViewById(R.id.tagline);
        View loader = findViewById(R.id.loader_bar);

        // Logo float + scale animation
        ObjectAnimator floatY = ObjectAnimator.ofFloat(logo, "translationY", 0f, -40f, 0f);
        floatY.setDuration(6000);
        floatY.setRepeatCount(ObjectAnimator.INFINITE);
        floatY.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logo, "scaleX", 0.8f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logo, "scaleY", 0.8f, 1f);
        scaleX.setDuration(1200);
        scaleY.setDuration(1200);

        // Fade in app name and tagline
        appName.setAlpha(0f);
        appName.setTranslationY(50f);
        tagline.setAlpha(0f);
        tagline.setTranslationY(30f);

        appName.animate().alpha(1f).translationY(0f).setDuration(1000).setStartDelay(600).start();
        tagline.animate().alpha(1f).translationY(0f).setDuration(1000).setStartDelay(900).start();

        // Loading bar animation
        loader.setScaleX(0f);
        loader.animate().scaleX(1f).setDuration(2000).setStartDelay(1200)
                .setInterpolator(new DecelerateInterpolator()).start();

        // Start floating and scale
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.start();
        floatY.start();
    }
}