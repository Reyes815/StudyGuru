package com.example.studyguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Formal_Representation_Training extends AppCompatActivity {

    private FirebaseFirestore firestore;

    public FloatingActionButton button;

    private String lastDocumentKey = null;

    // Initialize ImageViews
    private ImageView formalDefNote;
    private ImageView foldedMap;
    private ImageView arrow;
    private ImageView arrow2;

    // Initialize Views
    private View overlayView;

    List<String> dialoguesList = new ArrayList<>();
    ;

    private TextView textView;
    TextView wizard_dialogue;

    int dialogue_counter = 0;
    private int max_dialogue = 0;

    // Initialize strings that will store the input from the note popup
    private String gStates;
    private String gAlphabet;
    private String gInitialState;
    private String gFinalState;
    private boolean noteWasOpened = false;
    Dialog dialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training5);
        dialog = new Dialog(this);
        this.firestore = FirebaseFirestore.getInstance();

        // Get references for the Button
        button = findViewById(R.id.next_button);

        // Get references for the ImageViews
        formalDefNote = findViewById(R.id.formalDefNote);
        foldedMap = findViewById(R.id.foldedMap);
        arrow = findViewById(R.id.arrow);
        arrow2 = findViewById(R.id.arrow2);

        // Get references for the Views
        overlayView = findViewById(R.id.overlayView);

        // Get references for the TextView
        wizard_dialogue = findViewById(R.id.txtWizarddialogue);

        // Initialize visibility
        wizard_dialogue.setVisibility(View.INVISIBLE);
        arrow.setVisibility(View.INVISIBLE);
        arrow2.setVisibility(View.INVISIBLE);
        formalDefNote.setVisibility(View.INVISIBLE);
        foldedMap.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("Strings_for_Formal_Representation")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        // Document exists, you can access its data
                                        String states = doc.getString("States");
                                        String alphabet = doc.getString("Alphabet");
                                        String finalState = doc.getString("Final State");
                                        String initialState = doc.getString("Initial State");

                                        if (gStates.matches(states) && gAlphabet.matches(alphabet) && gFinalState.matches(finalState) && gInitialState.matches(initialState)) {
                                            Toast.makeText(Formal_Representation_Training.this, "Success", Toast.LENGTH_SHORT).show();
                                            showGamePopupSuccess();
                                        }
                                    }
                                }
                            }
                        });
            }
        });

        // Popup dialog open functionality
        formalDefNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp(R.layout.formal_representation_note_popup);
            }
        });
        foldedMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp(R.layout.formal_representation_map_popup);
            }
        });

        firestore.collection("Dialogue_for_Formal_Representation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Tap the screen to proceed.", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String b = doc.getString("Dialogue");
                                dialoguesList.add(b);
                                lastDocumentKey = doc.getId();
                                max_dialogue++;
                            }
                            System.out.println(dialoguesList);
                            wizard_dialogue.setText(dialoguesList.get(0));
                            wizard_dialogue.setVisibility(View.VISIBLE);
                            dialogue_counter++;
                        } else {
                            Log.d("training", "Failed: " + task.getException());
                        }
                    }
                });

        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogue_counter < max_dialogue) {
                    System.out.println(dialogue_counter);
                    wizard_dialogue.setText(dialoguesList.get(dialogue_counter));

                    if (dialogue_counter == 2) {
                        arrow.setVisibility(View.VISIBLE);
                        formalDefNote.setVisibility(View.VISIBLE);
                    } else if (dialogue_counter == 4) {
                        arrow2.setVisibility(View.VISIBLE);
                        foldedMap.setVisibility(View.VISIBLE);
                    } else if (dialogue_counter == 5) {
                        button.setVisibility(View.VISIBLE);
                    } else {
                        arrow.setVisibility(View.INVISIBLE);
                        arrow2.setVisibility(View.INVISIBLE);
                    }

                    dialogue_counter++;
                } else {
                    arrow2.setVisibility(View.INVISIBLE);
                    wizard_dialogue.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void popUp(int layoutResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customView = getLayoutInflater().inflate(layoutResource, null);
        builder.setView(customView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        if (layoutResource == R.layout.formal_representation_note_popup) {
            noteWasOpened = true;
            ImageView scroll = customView.findViewById(R.id.empty_open_scroll);
            EditText tfStates = customView.findViewById(R.id.tfStates);
            EditText tfAlphabet = customView.findViewById(R.id.tfAlphabet);
            EditText tfInitial = customView.findViewById(R.id.tfInitial);
            EditText tfFinal = customView.findViewById(R.id.tfFinal);

            scroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            if (noteWasOpened) {
                tfStates.setText(gStates);
                tfAlphabet.setText(gAlphabet);
                tfInitial.setText(gInitialState);
                tfFinal.setText(gFinalState);
            }

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    gStates = tfStates.getText().toString();
                    gAlphabet = tfAlphabet.getText().toString();
                    gInitialState = tfInitial.getText().toString();
                    gFinalState = tfFinal.getText().toString();
                }
            });
        }
        dialog.show();
    }



    private void showGamePopupSuccess() {
        dialog.setContentView(R.layout.game_popup);
        Button close = dialog.findViewById(R.id.close_btn);
        Button prev = dialog.findViewById(R.id.prev_btn);
        Button home = dialog.findViewById(R.id.home_btn);
        Button next = dialog.findViewById(R.id.next_btn);
        TextView score = dialog.findViewById(R.id.textView3);
        String text = "Congratulations, you have familiarized Formal Definition!";
        score.setText(text);
        prev.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        home.setVisibility(View.GONE);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Formal_Representation_Training.this, AssessmentPage.class);
                intent.putExtra("type", "Assessment_for_Formal_Representation");
                startActivity(intent);
                dialog.dismiss();

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_popup = new Intent(getApplicationContext(), HomePage.class);
                startActivity(home_popup);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}