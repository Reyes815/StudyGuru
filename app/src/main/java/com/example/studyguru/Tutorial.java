package com.example.studyguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class Tutorial extends AppCompatActivity {

    private int tutorialPart = 1;
    private ImageView knight;
    private ImageView knightOutline;
    private ImageView startVine;
    private ImageView startVineOutline;
    private ImageView vineA;
    private ImageView vineAOutline;
    private ImageView vineB;
    private ImageView vineBOutline;
    private TextView textDialogue;
    private ImageView pathAOutline;
    private ImageView pathBOutline;
    private ImageView flowerGoalOutline;
    private View overlayView;

    private List outlineList;
    private int blinkCounter = 0;
    private final int MAX_BLINKS = 5;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_page);

        // Get references for the ImageViews
        knight = findViewById(R.id.knight);
        knightOutline = findViewById(R.id.knightOutline);
        startVine = findViewById(R.id.startVine);
        startVineOutline = findViewById(R.id.startVineOutline);
        vineA = findViewById(R.id.vineA);
        vineAOutline = findViewById(R.id.vineAOutline);
        vineB = findViewById(R.id.vineB);
        vineBOutline = findViewById(R.id.vineBOutline);
        pathAOutline = findViewById(R.id.pathAOutline);
        pathBOutline = findViewById(R.id.pathBOutline);
        flowerGoalOutline = findViewById(R.id.flower_goal_outline);

        // Get references for the TextView
        textDialogue = findViewById(R.id.textDialogue);
        textDialogue.setVisibility(View.INVISIBLE);

        // Get references for the View
        overlayView = findViewById(R.id.overlayView);

        // Initialize a list for the ImageViewOutlines
        outlineList = new ArrayList<>();

        // Set the visibility for the ImageViewOutlines
        setVisibilityForList(outlineList, View.INVISIBLE);
        knightOutline.setVisibility(View.INVISIBLE);
        startVineOutline.setVisibility(View.INVISIBLE);
        vineAOutline.setVisibility(View.INVISIBLE);
        vineBOutline.setVisibility(View.INVISIBLE);
        pathAOutline.setVisibility(View.INVISIBLE);
        pathBOutline.setVisibility(View.INVISIBLE);
        flowerGoalOutline.setVisibility(View.INVISIBLE);

        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipDialogue();
            }
        });

        startBlinkingEffect();
    }

    private void startBlinkingEffect() {

        switch (tutorialPart) {
            case 1:
                textDialogue.setText("Shown on the screen are the path the knight should take.");
                outlineList.add(startVineOutline);
                outlineList.add(vineAOutline);
                outlineList.add(vineBOutline);
                break;
            case 2:
                textDialogue.setText("Your role is to guide the knight by inputting the right path A or B in the empty text field below.");
                outlineList.add(knightOutline);
                break;
            case 3:
                textDialogue.setText("Be careful as to there are paths that leads you back to where you came from.");
                break;
            case 4:
                textDialogue.setText("There are also paths that lets you continue to the next level.");
                break;
            case 5:
                textDialogue.setText("The goal is to reach the  pink flower to reach the next level.");
                outlineList.add(flowerGoalOutline);
                moveTextDialogue(0.195f);
                break;
            case 6:
                textDialogue.setText("Thats it! Good luck with your next adventure!");
                moveTextDialogue(0.122f);
                stopBlinkingEffect();
                break;
            case 7:
                Intent home = new Intent(this, HomePage.class);
                startActivity(home);
                break;
        }

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Toggle visibility of the outline list
                toggleVisibilityForList(outlineList);
                textDialogue.setVisibility(View.VISIBLE);

                if (blinkCounter != MAX_BLINKS * 2) {
                    if (tutorialPart == 2 && blinkCounter == 2) {
                        outlineList.add(pathAOutline);
                        outlineList.add(pathBOutline);
                    }
                    blinkCounter++;
                    handler.postDelayed(this, 500);
                } else {
                    skipDialogue();
                }
            }
        }, 2000); // Initial delay before starting the blinking effect
    }

    private void stopBlinkingEffect() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void setVisibilityForList(List<ImageView> imageViewList, int visibility) {
        for (ImageView image : imageViewList) {
            image.setVisibility(visibility);
        }
    }

    private void toggleVisibilityForList(List<ImageView> imageViewList) {
        for (ImageView image: imageViewList) {
            image.setVisibility(image.getVisibility() == View.VISIBLE
                    ? View.INVISIBLE
                    : View.VISIBLE);
        }
    }

    private void moveTextDialogue(float bias) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.setVerticalBias(R.id.textDialogue, bias);
        constraintSet.applyTo(constraintLayout);
    }

    private void skipDialogue() {
        blinkCounter = 0;
        setVisibilityForList(outlineList, View.INVISIBLE);
        outlineList.clear();
        tutorialPart++;
        startBlinkingEffect();
    }
}