package com.example.studyguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        View rootView = findViewById(android.R.id.content);
        RegisterUI registerUI = new RegisterUI(rootView);

        Button reg = registerUI.getReg();
        EditText email = registerUI.getEmail();
        EditText username = registerUI.getUsername();
        EditText password = registerUI.getPassword();
        EditText con_pass = registerUI.getCon_pass();

        RegisterControl registerControl = new RegisterControl();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerControl.registerUser(username,email,password,con_pass,Registration.this);
            }
        });
    }
}