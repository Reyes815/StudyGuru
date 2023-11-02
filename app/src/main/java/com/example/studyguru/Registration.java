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

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference ref = firestore.collection("client").document();

        Button reg = (Button) findViewById(R.id.registration_button);
        EditText email = (EditText) findViewById(R.id.email_address_reg);
        EditText username = (EditText) findViewById(R.id.username_reg);
        EditText password = (EditText) findViewById(R.id.password_reg);
        EditText con_pass = (EditText) findViewById(R.id.confirm_password_reg);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

                if(username.getText().toString().equals("")) {
                    Toast.makeText(Registration.this, "Please type a username", Toast.LENGTH_SHORT).show();
                    //check username
                }else if(email.getText().toString().equals("") || !email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(Registration.this, "Please type an email id", Toast.LENGTH_SHORT).show();
                    //check email
                }else if(password.getText().toString().equals("")){
                    Toast.makeText(Registration.this, "Please type a password", Toast.LENGTH_SHORT).show();
                    //check pass
                }else if(!con_pass.getText().toString().equals(password.getText().toString())){
                    Toast.makeText(Registration.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                    //re-check pass
                }else {
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(Registration.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("Name", username.getText().toString());
                                reg_entry.put("Email", email.getText().toString());
                                reg_entry.put("Password", password.getText().toString());

                                //   String myId = ref.getId();
                                firestore.collection("client")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(Registration.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                                username.setText("");
                                                email.setText("");
                                                password.setText("");
                                                con_pass.setText("");
                                                Intent intent = new Intent(getApplicationContext(), Login_main.class);
                                                startActivity(intent);
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
                    });
                }
            }
        });
    }
}