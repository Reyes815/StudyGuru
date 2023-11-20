package com.example.studyguru;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class Tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_page);

        // Get references to the ImageViews
        ImageView vineStart = findViewById(R.id.vineStart);
        ImageView vineA = findViewById(R.id.vineA);
        ImageView vineB = findViewById(R.id.vineB);

        // Get references to the TextViews
        TextView pathATextView = findViewById(R.id.path_A_txtView);
        TextView pathBTextView = findViewById(R.id.path_B_txtView);

        // Apply the wiggle animation to vineStart
        ObjectAnimator wiggleStart = createWiggleAnimation(vineStart);
        wiggleStart.start();

        // Apply the wiggle animation to vineA
        ObjectAnimator wiggleA = createWiggleAnimation(vineA);
        wiggleA.start();

        // Apply the wiggle animation to vineB
        ObjectAnimator wiggleB = createWiggleAnimation(vineB);
        wiggleB.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Reset the rotation and position after vineB animation completes
                vineB.setRotation(0f);
                vineA.setRotation(0f);
                vineStart.setRotation(0f);

                // Apply the wiggle animation to pathATextView after vineB animation completes
                ObjectAnimator wigglePathA = createWiggleAnimation(pathATextView);
                wigglePathA.start();

                // Apply the wiggle animation to pathBTextView after vineB animation completes
                ObjectAnimator wigglePathB = createWiggleAnimation(pathBTextView);
                wigglePathB.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Reset the rotation and position of the textPath
                        pathATextView.setRotation(0f);
                        pathBTextView.setRotation(0f);
                    }
                });
                wigglePathB.start();
            }
        });

        wiggleB.start();
    }

    private ObjectAnimator createWiggleAnimation(ImageView imageView) {
        ObjectAnimator wiggleAnimation = ObjectAnimator.ofFloat(imageView, "rotation", -10, 10);
        wiggleAnimation.setInterpolator(new LinearInterpolator());
        wiggleAnimation.setRepeatMode(ObjectAnimator.REVERSE);
        wiggleAnimation.setRepeatCount(3); // Set to 3 for three wiggles
        wiggleAnimation.setDuration(500); // Adjust the duration as needed
        return wiggleAnimation;
    }

    private ObjectAnimator createWiggleAnimation(TextView textView) {
        ObjectAnimator wiggleAnimation = ObjectAnimator.ofFloat(textView, "rotation", -5, 5);
        wiggleAnimation.setInterpolator(new LinearInterpolator());
        wiggleAnimation.setRepeatMode(ObjectAnimator.REVERSE);
        wiggleAnimation.setRepeatCount(3); // Set to 3 for three wiggles
        wiggleAnimation.setDuration(500); // Adjust the duration as needed
        return wiggleAnimation;
    }
}