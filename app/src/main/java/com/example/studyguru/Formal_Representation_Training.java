package com.example.studyguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
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
    private ImageView formalDefMap;

    // Zoom in functionality
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    List<String> dialoguesList = new ArrayList<>();;

    TextView wizard_dialogue;

    EditText States_txtfield;
    EditText Alphabet_txtfield;
    EditText Initial_txtfield;
    EditText Final_txtfield;

    int dialogue_counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training5);

        this.firestore = FirebaseFirestore.getInstance();

        wizard_dialogue = findViewById(R.id.txtWizarddialogue);

        button = findViewById(R.id.next_button);

        States_txtfield = findViewById(R.id.States_txtfield);
        Alphabet_txtfield = findViewById(R.id.Alphabet_txtfield);
        Initial_txtfield = findViewById(R.id.Initial_txtfield);
        Final_txtfield = findViewById(R.id.Final_txtfield);

        // Get references for the ImageViews
        formalDefMap = findViewById(R.id.formalDefMap);

        // Initialize zoom in functionality
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        formalDefMap.setOnClickListener(new View.OnClickListener() {
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(States_txtfield.getText().toString().isEmpty()){
                    Toast.makeText(Formal_Representation_Training.this, "Please input something", Toast.LENGTH_SHORT).show();
                } else if(Alphabet_txtfield.getText().toString().isEmpty()){
                    Toast.makeText(Formal_Representation_Training.this, "Please input something", Toast.LENGTH_SHORT).show();
                } else if(Initial_txtfield.getText().toString().isEmpty()){
                    Toast.makeText(Formal_Representation_Training.this, "Please input something", Toast.LENGTH_SHORT).show();
                } else if(Final_txtfield.getText().toString().isEmpty()){
                    Toast.makeText(Formal_Representation_Training.this, "Please input something", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void popUp(int layoutResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customView = getLayoutInflater().inflate(layoutResource, null);
        builder.setView(customView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        // Set the onTouchListener to handle pinch-to-zoom gestures
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // Limit the scale factor to a certain range if needed
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

            // Apply the scale factor to the image view
            formalDefMap.setScaleX(scaleFactor);
            formalDefMap.setScaleY(scaleFactor);

            return true;
        }
    }
}