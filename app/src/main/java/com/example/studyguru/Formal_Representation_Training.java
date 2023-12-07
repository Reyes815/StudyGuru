package com.example.studyguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Formal_Representation_Training extends AppCompatActivity {

    private FirebaseFirestore firestore;

    public FloatingActionButton button;

    private String lastDocumentKey = null;

    // Initialize ImageViews
    private ImageView formalDefNote;
    private ImageView foldedMap;

    List<String> dialoguesList = new ArrayList<>();;

    TextView wizard_dialogue;

    int dialogue_counter = 0;
    DocumentReference adventure_check2;
    Map<String, Object> status = new HashMap<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training5);

        this.firestore = FirebaseFirestore.getInstance();
        adventure_check2 = firestore.collection("Training Levels").document("languages");
        status.put("unlocked", "true");
        adventure_check2.update(status);
        wizard_dialogue = findViewById(R.id.txtWizarddialogue);

        button = findViewById(R.id.next_button);

        // Get references for the ImageViews
        formalDefNote = findViewById(R.id.formalDefNote);
        foldedMap = findViewById(R.id.foldedMap);

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

        firestore.collection("Dialogue_for_Acceptance_Training")
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
                            wizard_dialogue.setText(dialoguesList.get(0));
                            dialogue_counter++;
                        } else {
                            Log.d("training", "Failed: " + task.getException());
                        }
                    }
                });

    }

    // Initialize strings that will store the input from the note popup
    private String states;
    private String alphabet;
    private String initialState;
    private String finalState;
    private boolean noteWasOpened = false;

    private void popUp(int layoutResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customView = getLayoutInflater().inflate(layoutResource, null);
        builder.setView(customView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if (layoutResource == R.layout.formal_representation_note_popup) {
            EditText tfStates = customView.findViewById(R.id.tfStates);
            EditText tfAlphabet = customView.findViewById(R.id.tfAlphabet);
            EditText tfInitial = customView.findViewById(R.id.tfInitial);
            EditText tfFinal = customView.findViewById(R.id.tfFinal);
            Button btnSubmit = customView.findViewById(R.id.btnSubmit);

            if (noteWasOpened) {
                tfStates.setText(states);
                tfAlphabet.setText(alphabet);
                tfInitial.setText(initialState);
                tfFinal.setText(finalState);
            }

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    states = tfStates.getText().toString();
                    alphabet = tfAlphabet.getText().toString();
                    initialState = tfInitial.getText().toString();
                    finalState = tfFinal.getText().toString();

                    noteWasOpened = true;
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Database functionality here...
                }
            });
        }
        dialog.show();
    }

}