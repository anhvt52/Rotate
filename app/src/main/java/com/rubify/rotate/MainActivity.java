package com.rubify.rotate;

import android.media.MediaPlayer;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    static int BASE_ROTATE = 360 * 30;
    float currentDeg = 0.0f;

    Button rotateButton;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);
        rotateButton = (Button)findViewById(R.id.button);
        rotateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        float deg = (float)(BASE_ROTATE + Math.random() * 360);
        float nextDeg = currentDeg + deg;
        Log.i("Main", "onClick: " + currentDeg + " " + nextDeg);

        RotateAnimation rotate = new RotateAnimation(currentDeg, nextDeg, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(17_000);
//        rotate.setInterpolator(new FastOutSlowInInterpolator());
        Interpolator interpolator;
        interpolator = PathInterpolatorCompat.create(0.2f, 0.5f, 0.4f, 0.95f);
        rotate.setInterpolator(interpolator);
        rotate.setFillAfter(true);
        rotate.setFillEnabled(true);
//        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                playBgMusic();
                rotateButton.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                stopBgMusic();
                rotateButton.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        currentDeg = nextDeg;
        imageView.startAnimation(rotate);

    }

    private void playBgMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_bg);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void stopBgMusic() {
        Log.i("Main", "stopBgMusic: ");
        mediaPlayer.stop();
    }
}
