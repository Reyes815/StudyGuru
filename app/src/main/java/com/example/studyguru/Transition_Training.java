package com.example.studyguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transition_Training extends AppCompatActivity {

    private Handler handler;
    public FloatingActionButton button;

    private FirebaseFirestore firestore;

    private char choice;

    private int charIndex = 0;

    private String fullText = "";

    private Runnable runnable;

    ImageView keyA;

    private String lastDocumentKey = null;
    ImageView keyB;

    List<String> dialoguesList = new ArrayList<>();;

    TextView wizard_dialogue;

    int dialogue_counter = 0;

    ImageView wizard;
    DocumentReference adventure_check2;
    Map<String, Object> status = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_training);

        this.firestore = FirebaseFirestore.getInstance();
        adventure_check2 = firestore.collection("Training Levels").document("acceptance");
        status.put("unlocked", "true");
        button = findViewById(R.id.next_button);

        wizard = findViewById(R.id.wizard_imgview);
        wizard.setBackgroundResource(R.drawable.idle_wizard2);
        AnimationDrawable idleW = (AnimationDrawable) wizard.getBackground();
        idleW.start();

        wizard_dialogue = findViewById(R.id.txtWizarddialogue);

        handler = new Handler();


        firestore.collection("Dialogue_Training2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String b = doc.getString("Dialogue");
                                dialoguesList.add(b);
                                lastDocumentKey = doc.getId();
                            }
                            displayTextWithAnimation(dialoguesList.get(0));
                        } else {
                            Log.d("training", "Failed: " + task.getException());
                        }
                    }
                });



        // Get references to your background and character ImageViews
        ImageView background1 = findViewById(R.id.imageView4);
        ImageView background2 = findViewById(R.id.imageView4_2);
        ImageView path = findViewById(R.id.path_forA);
        ImageView path2 = findViewById(R.id.path_forB);
        ImageView stone_gate = findViewById(R.id.stone_gate);
        ImageView stone_gate2 = findViewById(R.id.stone_gate2);
        ImageView character = findViewById(R.id.knight1);
        ImageView character2 = findViewById(R.id.knight2);

        // Set the initial position of the duplicate background
        background1.setY(0); // Start the first background at the top
        background2.setY(-background1.getHeight()); // Start the second background above the first one
        path2.setY(-background1.getHeight());
        stone_gate2.setY(-background1.getHeight());

        float totalBackgroundHeight = background1.getHeight() + background2.getHeight();
        // Calculate the distance you want the background to move vertically
        float distanceToMove = 1218;

        // Create a ValueAnimator for the vertical movement
        ValueAnimator backgroundAnimator = ValueAnimator.ofFloat(0, distanceToMove);
        backgroundAnimator.setDuration(5000); // Adjust duration as needed
        //backgroundAnimator.setRepeatCount(ValueAnimator.INFINITE); // Repeat the animation

        // Update the vertical position of the backgrounds during animation
        backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                background1.setTranslationY(value);
                background2.setTranslationY(value - background1.getHeight());
                path.setTranslationY(value);
                path2.setTranslationY(value - background1.getHeight());
                stone_gate.setTranslationY(value);
                stone_gate2.setTranslationY(value - background1.getHeight());
            }
        });

        // Start the initial background animation
        //backgroundAnimator.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customView = getLayoutInflater().inflate(R.layout.custom_dialoug_for_training2, null);
        builder.setView(customView);


        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.show();

        keyA = customView.findViewById(R.id.Key_imageview_A);
        keyB = customView.findViewById(R.id.Key_imageview_B);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charIndex = 0;
                dialogue_counter++;
                if(dialogue_counter == 5){
                    button.setEnabled(true);
                }

                if(dialogue_counter == 6){
                    adventure_check2.update(status);
                    Intent intent = new Intent(Transition_Training.this, AssessmentPage.class);
                    intent.putExtra("type", "Assessment_for_Transition_Training");
                    startActivity(intent);
                }

                if(dialogue_counter == 1){
                    button.setEnabled(false);
                }

                if(dialogue_counter != 5){
                    displayTextWithAnimation(dialoguesList.get(dialogue_counter));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            dialog.show();
                        }
                    }, 5000);
                }else {

                    displayTextWithAnimation(dialoguesList.get(7));
                }

            }
        });






        keyA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 'A';
                charIndex = 0;

                if (dialogue_counter == 1) {
                    translate(character, stone_gate);
                    dialog.dismiss();
                    // Delayed action with a Handler
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            translate(character, character2);
                        }
                    }, 1500);  // 1000 milliseconds (1 second) delay

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            displayTextWithAnimation(dialoguesList.get(5));
                        }
                    }, 1800);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            dialog.show();
                        }
                    }, 6000);  // 1000 milliseconds (1 second) delay
                }

                if(dialogue_counter == 3){
                    translate(character, stone_gate2);
                    dialog.dismiss();
                    // Delayed action with a Handler
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            translate(character, character2);
                        }
                    }, 1500);  // 1000 milliseconds (1 second) delay

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            displayTextWithAnimation(dialoguesList.get(5));
                        }
                    }, 1800);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            dialog.show();
                        }
                    }, 3000);  // 1000 milliseconds (1 second) delay
                }

                if(dialogue_counter == 2 || dialogue_counter == 4){
                    backgroundAnimator.start();
                    dialog.dismiss();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(dialogue_counter < 4){
                                displayTextWithAnimation(dialoguesList.get(6));
                            } else if (dialogue_counter == 4) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        stone_gate2.setVisibility(View.GONE);
                                    }
                                }, 1000);
                            }
                        }
                    }, 1000 );


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            button.performClick();
                        }
                    }, 7000 );
                }

            }
        });

        keyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 'B';
                charIndex = 0;
                if(dialogue_counter == 1 || dialogue_counter == 3){
                    backgroundAnimator.start();
                    dialog.dismiss();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(dialogue_counter < 4){
                                displayTextWithAnimation(dialoguesList.get(6));
                            }
                        }
                    }, 1000 );

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            button.performClick();
                        }
                    }, 7000 );

                }

                if(dialogue_counter == 2){
                    translate(character, stone_gate2);
                    dialog.dismiss();
                    // Delayed action with a Handler
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            translate(character, character2);
                        }
                    }, 1500);  // 1000 milliseconds (1 second) delay


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            displayTextWithAnimation(dialoguesList.get(5));
                        }
                    }, 1800);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            dialog.show();
                        }
                    }, 6000);  // 1000 milliseconds (1 second) delay
                }
            }
        });
    }


    private void translate(View viewToMove, View target) {

        viewToMove.animate()
                .x(target.getX())
                .y(target.getY())
                .setDuration(1000)
                .start();
    }


    public void displayTextWithAnimation(final String text) {
        if (text != null) {
            fullText = text;
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (charIndex < fullText.length()) {
                        String partialText = fullText.substring(0, charIndex + 1);
                        wizard_dialogue.setText(partialText);
                        charIndex++;
                        if (charIndex < fullText.length()) {
                            handler.postDelayed(this, 30); // Adjust the delay time between characters
                        }
                    }
                }
            };
            handler.postDelayed(runnable, 50); // Initial delay before starting animation
        }
    }
}