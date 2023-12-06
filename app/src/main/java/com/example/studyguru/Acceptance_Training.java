package com.example.studyguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Acceptance_Training extends AppCompatActivity {

    public FloatingActionButton button;

    private FirebaseFirestore firestore;

    private Handler handler;

    private char choice;

    private int move_ctr;

    private int charIndex = 0;

    private Runnable runnable;

    TextView A_path;

    private String lastDocumentKey = null;
    TextView B_path;

    List<String> dialoguesList = new ArrayList<>();;

    List<String> correct_answersList = new ArrayList<>();
    List<String> wrong_answersList = new ArrayList<>();

    TextView wizard_dialogue;

    TextView string_textview;

    int dialogue_counter = 0;

    int state;

    private String fullText = "";

    private View character;

    private View stone_gate_forB;

    private  String answer;

    private String answer_copy;

    private int currentPosition = 0;

    private int move_limit;

    int random_correct;

    int random_wrong;

    String wrong_choice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_training);

        this.firestore = FirebaseFirestore.getInstance();

        button = findViewById(R.id.next_button);

        wizard_dialogue = findViewById(R.id.txtWizarddialogue);

        string_textview = findViewById(R.id.string_textview);

        state = 0;

        move_ctr = 0;


        handler = new Handler();


        firestore.collection("Dialogue_for_Acceptance_Training")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String b = doc.getString("Dialogue");
                                wrong_choice = doc.getString("wrong_choice");
                                dialoguesList.add(b);
                                lastDocumentKey = doc.getId();
                            }
                            displayTextWithAnimation(dialoguesList.get(dialogue_counter));
                        } else {
                            Log.d("training", "Failed: " + task.getException());
                        }
                    }
                });

        firestore.collection("Strings_for_Acceptance_Training")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String b = doc.getString("correct_answer");
                                String a = doc.getString("wrong_answer");
                                // Check if 'a' is not null before adding it to the list
                                if (a != null) {
                                    wrong_answersList.add(a);
                                    lastDocumentKey = doc.getId();
                                }

                                if (b != null) {
                                    correct_answersList.add(b);
                                }
                            }

                            Random random = new Random();

                            // Make sure the wrong_answersList is not empty before getting a random element
                            if (!wrong_answersList.isEmpty()) {
                                random_wrong = random.nextInt(wrong_answersList.size());
                                answer = wrong_answersList.get(random_wrong);
                                answer_copy = answer;
                                move_limit = answer.length();
                            } else {
                                // Handle the case where wrong_answersList is empty
                                // For example, set a default value for 'answer' or take appropriate action
                                answer = "Default Answer";
                                answer_copy = answer;
                                move_limit = answer.length();
                            }
                        } else {
                            Log.d("training", "Failed: " + task.getException());
                        }
                    }
                });


        // Get references to your background and character ImageViews
        ImageView background1 = findViewById(R.id.imageView4);
        ImageView background2 = findViewById(R.id.imageView4_2);

        //A path image
        ImageView path_forA = findViewById(R.id.path_forA);
        ImageView path_forA2 = findViewById(R.id.path_forA2);

        //B path image
        ImageView path_forB = findViewById(R.id.path_forB);
        ImageView path_forB2 = findViewById(R.id.path_forB2);

        //Stone Gate A
        ImageView stone_gate_forA = findViewById(R.id.stone_gate_forA);
        ImageView stone_gate_forA2 = findViewById(R.id.stone_gate_forA2);

        //Stone Gate B
        stone_gate_forB = findViewById(R.id.stone_gate_forB);
        ImageView stone_gate_forB2 = findViewById(R.id.stone_gate_forB2);

        //Letter A
        A_path = findViewById(R.id.path2_A_txtView);
        TextView A_path2 = findViewById(R.id.path2_A_txtView2);

        //Letter B
        B_path = findViewById(R.id.path2_B_txtView);
        TextView B_path2 = findViewById(R.id.path2_B_txtView2);

        //Knight
        character = findViewById(R.id.knight1);
        ImageView character2 = findViewById(R.id.knight2);

        //Chest
        ImageView chest = findViewById(R.id.treasure_chest);

        chest.setVisibility(View.GONE);


        float distanceToMove = 1218;

        // Create a ValueAnimator for the vertical movement
        ValueAnimator backgroundAnimator = ValueAnimator.ofFloat(0, distanceToMove);
        backgroundAnimator.setDuration(5000); // Adjust duration as needed


        backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                float translationY = value;

                background1.setTranslationY(value);
                background2.setTranslationY(value - background1.getHeight());

                //path A
                path_forA.setTranslationY(value);
                path_forA2.setTranslationY(value - background1.getHeight());

                //path B
                path_forB.setTranslationY(value);
                path_forB2.setTranslationY(value - background1.getHeight());

                //stone gate for A
                stone_gate_forA.setTranslationY(value);
                stone_gate_forA2.setTranslationY(value - background1.getHeight());

                //stone gate for B
                stone_gate_forB.setTranslationY(value);
                stone_gate_forB2.setTranslationY(value - background1.getHeight());

                //Letter A
                A_path.setTranslationY(value);
                A_path2.setTranslationY(value - background1.getHeight());

                //Letter B
                B_path.setTranslationY(value);
                B_path2.setTranslationY(value - background1.getHeight());

                //Chest
                chest.setTranslationY(value - background1.getHeight());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charIndex = 0;
                dialogue_counter++;
                displayTextWithAnimation(dialoguesList.get(dialogue_counter));

                if(dialogue_counter == 1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            string_textview.setText(answer_copy);
                        }
                    }, 9000);
                }

                if(dialogue_counter == 3){
                    Random random = new Random();
                    int correct_ans = random.nextInt(correct_answersList.size());
                    answer = correct_answersList.get(correct_ans);
                    answer_copy = answer;
                    move_limit = answer.length();
                    string_textview.setText(answer_copy);
                    A_path2.setVisibility(View.VISIBLE);
                    B_path2.setVisibility(View.VISIBLE);
                }
            }
        });

        A_path2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 'a';


                if (Character.compare(choice, answer.charAt(move_ctr)) == 0 && move_ctr < move_limit) {

                    move_ctr++;

                    subtractOneLetter();

                    switch (state) {
                        case 0:
                            state = 3;
                            break;
                        case 1:
                            state = 2;
                            break;
                        case 2:
                            state = 2;
                            if(move_limit <= move_ctr){
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        path_forA2.setVisibility(View.GONE);
                                        path_forB2.setVisibility(View.GONE);
                                        stone_gate_forA2.setVisibility(View.GONE);
                                        stone_gate_forB2.setVisibility(View.GONE);
                                        A_path2.setVisibility(View.GONE);
                                        B_path2.setVisibility(View.GONE);
                                        chest.setVisibility(View.VISIBLE);

                                        charIndex = 0;
                                        dialogue_counter++;
                                        displayTextWithAnimation(dialoguesList.get(dialogue_counter));
                                    }
                                },2000);
                            }
                            break;
                        case 3:
                            state = 3;
                            if(move_limit <= move_ctr){
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        A_path2.setVisibility(View.GONE);
                                        B_path2.setVisibility(View.GONE);
                                        charIndex = 0;
                                        dialogue_counter++;
                                        displayTextWithAnimation(dialoguesList.get(dialogue_counter));
                                        state = 0;
                                        move_ctr = 0;
                                        move_limit = 0;
                                    }
                                },2000);
                            }
                            break;
                    }

                    if (state == 1) {
                        translate(character, stone_gate_forA);
                    } else {
                        translate(character, stone_gate_forA2);
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backgroundAnimator.start();
                        }
                    }, 2000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    translate(character, stone_gate_forA);

                                    handler.postDelayed(this, 700);
                                }
                            };

                            handler.post(runnable);
                        }
                    }, 2500);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.removeCallbacks(runnable);
                            translate(character, character2);
                        }
                    }, 6500);

                } else{
                    if (state == 0) {
                        translate(character, stone_gate_forA);
                    } else {
                        translate(character, stone_gate_forA2);
                    }
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
                            charIndex = 0;
                            displayTextWithAnimation(wrong_choice);
                        }
                    }, 1800);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            charIndex = 0;
                            displayTextWithAnimation("Continue");
                        }
                    }, 9000);
                }
            }
        });



        B_path2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 'b';

                if (Character.compare(choice, answer.charAt(move_ctr)) == 0 && move_ctr < move_limit) {

                    move_ctr++;

                    subtractOneLetter();

                    switch (state) {
                        case 0:
                            state = 1;
                            break;
                        case 1:
                            state = 3;
                            break;
                        case 2:
                            state = 2;
                            if(move_limit <= move_ctr){
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        path_forA2.setVisibility(View.GONE);
                                        path_forB2.setVisibility(View.GONE);
                                        stone_gate_forA2.setVisibility(View.GONE);
                                        stone_gate_forB2.setVisibility(View.GONE);
                                        A_path2.setVisibility(View.GONE);
                                        B_path2.setVisibility(View.GONE);
                                        chest.setVisibility(View.VISIBLE);

                                        charIndex = 0;
                                        dialogue_counter++;
                                        displayTextWithAnimation(dialoguesList.get(dialogue_counter));
                                    }
                                },2000);
                            }
                            break;
                        case 3:
                            state = 3;
                            if(move_limit <= move_ctr) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        A_path2.setVisibility(View.GONE);
                                        B_path2.setVisibility(View.GONE);
                                        charIndex = 0;
                                        dialogue_counter++;
                                        displayTextWithAnimation(dialoguesList.get(dialogue_counter));
                                        state = 0;
                                        move_ctr = 0;
                                        move_limit = 0;
                                    }
                                }, 2000);
                            }
                            break;
                    }

                    if (state == 0) {
                        translate(character, stone_gate_forB);
                    } else {
                        translate(character, stone_gate_forB2);
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backgroundAnimator.start();
                        }
                    }, 2000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    translate(character, stone_gate_forB);

                                    handler.postDelayed(this, 700);
                                }
                            };

                            handler.post(runnable);
                        }
                    }, 2500);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.removeCallbacks(runnable);
                            translate(character, character2);
                        }
                    }, 6500);

                } else{
                    if (state == 0) {
                        translate(character, stone_gate_forB);
                    } else {
                        translate(character, stone_gate_forB2);
                    }
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
                            charIndex = 0;
                            displayTextWithAnimation(wrong_choice);
                        }
                    }, 1800);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Your delayed code here
                            charIndex = 0;
                            displayTextWithAnimation("Continue");
                        }
                    }, 9000);
                }

                handler.removeCallbacksAndMessages(runnable);
            }
        });


    }


    private void translate(View viewToMove, View target) {

        viewToMove.animate()
                .x(target.getX())
                .y(target.getY())
                .setDuration(1000)
                .start();

        Log.d("translate", String.valueOf(state));

    }

    private void wrong_choice(View viewToMove, View target){
        // Delayed action with a Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your delayed code here
                translate(viewToMove, target);
            }
        }, 1500);  // 1000 milliseconds (1 second) delay

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your delayed code here
                displayTextWithAnimation("Wrong choice");
            }
        }, 1800);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // To prevent memory leaks, remove callbacks when the activity is destroyed
    }


    private void subtractOneLetter() {
        if (currentPosition + 1 < answer_copy.length()) {
            String updatedString = answer_copy.substring(1);
            answer_copy = updatedString;
            string_textview.setText(updatedString);
        } else {
// Notify the user that there are no more letters to subtract
            if(state == 1 || state == 2)
                string_textview.setText("Congratulations!");
            else
                string_textview.setText("You're Trapped!");
        }
    }
}