package com.example.studyguru;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterControl {
    boolean RegisterStatus;
    FirebaseFirestore firestore;
    DocumentReference ref;

    public RegisterControl(){
        this.firestore = FirebaseFirestore.getInstance();
        this.ref = firestore.collection("user").document();
    }

    public void registerUser(EditText username, EditText email, EditText password, EditText con_pass, Context context) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        String gmailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (username.getText().toString().equals("")) {
            Toast.makeText(context, "Please type a username", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().equals("") || !email.getText().toString().matches(emailPattern)) {
            Toast.makeText(context, "Please type a valid email id", Toast.LENGTH_SHORT).show();
        } else if (!email.getText().toString().matches(gmailPattern)) {
            Toast.makeText(context, "Only Gmail addresses are allowed", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(context, "Please type a password", Toast.LENGTH_SHORT).show();
        } else if (!con_pass.getText().toString().equals(password.getText().toString())) {
            Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show();
        } else {
            firestore.collection("user")
                    .whereEqualTo("Email", email.getText().toString()) // Check if the email already exists
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Email already exists
                                Toast.makeText(context, "Sorry, this email is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                // Email does not exist, proceed with registration
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("Name", username.getText().toString());
                                reg_entry.put("Email", email.getText().toString());
                                reg_entry.put("Password", password.getText().toString());

                                firestore.collection("user")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                                                username.setText("");
                                                email.setText("");
                                                password.setText("");
                                                con_pass.setText("");
                                                Intent intent = new Intent(context, Login_main.class);
                                                context.startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error", e.getMessage());
                        }
                    });
        }
    }

    public boolean isRegisterStatus() {
        return RegisterStatus;
    }
}
