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

import java.util.HashMap;
import java.util.Map;

public class RegisterControl {
    boolean RegisterStatus;
    FirebaseFirestore firestore;
    DocumentReference ref;

    public RegisterControl(){
        this.firestore = FirebaseFirestore.getInstance();
        this.ref = firestore.collection("client").document();
    }

    public void registerUser(EditText username, EditText email, EditText password, EditText con_pass, Context context){
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        if(username.getText().toString().equals("")) {
            Toast.makeText(context, "Please type a username", Toast.LENGTH_SHORT).show();
            //check username
        }else if(email.getText().toString().equals("") || !email.getText().toString().matches(emailPattern)) {
            Toast.makeText(context, "Please type an email id", Toast.LENGTH_SHORT).show();
            //check email
        }else if(password.getText().toString().equals("")){
            Toast.makeText(context, "Please type a password", Toast.LENGTH_SHORT).show();
            //check pass
        }else if(!con_pass.getText().toString().equals(password.getText().toString())){
            Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show();
            //re-check pass
        }else {
            ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists())
                    {
                        Toast.makeText(context, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                                        RegisterStatus = true;
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
                                        RegisterStatus = false;
                                    }
                                });
                    }
                }
            });
        }
    }

    public boolean isRegisterStatus() {
        return RegisterStatus;
    }
}
