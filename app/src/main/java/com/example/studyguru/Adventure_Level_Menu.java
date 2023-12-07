package com.example.studyguru;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Adventure_Level_Menu extends AppCompatActivity {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    DocumentReference adventure_check2 = firestore.collection("Adventure Level ").document("level 2");
    DocumentReference adventure_check3 = firestore.collection("Adventure Level ").document("level 3");
    DocumentReference adventure_check4 = firestore.collection("Adventure Level ").document("level 4");
    DocumentReference adventure_check5 = firestore.collection("Adventure Level ").document("level 5");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_menu);

        Button level_1 = findViewById(R.id.adventure_level_1_btn);
        Button level_2 = findViewById(R.id.adventure_level_2_btn);
        Button level_3 = findViewById(R.id.adventure_level_3_btn);
        Button level_4 = findViewById(R.id.adventure_level_4_btn);
        Button level_5 = findViewById(R.id.adventure_level_5_btn);
        Button home = findViewById(R.id.home_btn);

        level_2.setEnabled(false);
        level_3.setEnabled(false);
        level_4.setEnabled(false);
        level_5.setEnabled(false);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), HomePage.class);
                startActivity(home);
            }
        });


        level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent training = new Intent(getApplicationContext(), AdventureLevel1.class);
                startActivity(training);
            }
        });

        level_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent training = new Intent(getApplicationContext(), AdventureLevel2.class);
                startActivity(training);
            }
        });

        level_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent training = new Intent(getApplicationContext(), AdventureLevel3.class);
                startActivity(training);
            }
        });

        level_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent training = new Intent(getApplicationContext(), AdventureLevel4.class);
                startActivity(training);
            }
        });

        level_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent training = new Intent(getApplicationContext(), AdventureLevel5.class);
                startActivity(training);
            }
        });

        adventure_check2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Assuming 'status' is the field in your document
                        String unlocked = document.getString("Unlocked");
                        if (unlocked.equals("true")) {
                            level_2.setEnabled(true);
                        }
                    } else {
                        Log.d("Document does not exist", "No such document");
                    }
                } else {
                    Log.d("Failed to get doc", "get failed with ", task.getException());
                }
            }
        });

        adventure_check3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Assuming 'status' is the field in your document
                        String unlocked = document.getString("Unlocked");
                        if (unlocked.equals("true")) {
                            level_3.setEnabled(true);
                        }
                    } else {
                        Log.d("Document does not exist", "No such document");
                    }
                } else {
                    Log.d("Failed to get doc", "get failed with ", task.getException());
                }
            }
        });

        adventure_check4.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Assuming 'status' is the field in your document
                        String unlocked = document.getString("Unlocked");
                        if (unlocked.equals("true")) {
                            level_4.setEnabled(true);
                        }
                    } else {
                        Log.d("Document does not exist", "No such document");
                    }
                } else {
                    Log.d("Failed to get doc", "get failed with ", task.getException());
                }
            }
        });

        adventure_check5.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Assuming 'status' is the field in your document
                        String unlocked = document.getString("Unlocked");
                        if (unlocked.equals("true")) {
                            level_5.setEnabled(true);
                        }
                    } else {
                        Log.d("Document does not exist", "No such document");
                    }
                } else {
                    Log.d("Failed to get doc", "get failed with ", task.getException());
                }
            }
        });
    }
}
