package com.yash.newsindia;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends AppCompatActivity {

    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animationView = findViewById(R.id.anim);

        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished == 1000)
                    animationView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();

            }
        };

        timer.start();

        final Intent intent=new Intent(getApplicationContext(),ScrollingActivity.class);

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {


                animationView.setVisibility(View.INVISIBLE);


                startActivity(intent);



            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }
}
