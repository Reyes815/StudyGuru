package com.example.studyguru;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginControl {
    private boolean LoginStatus;
    private FirebaseFirestore firestore;

    public LoginControl(){
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void verifyLogin(EditText email, EditText password, Context context){
        if (email.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter valid password", Toast.LENGTH_SHORT).show();
        }
        firestore.collection("client")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean incorrect = false;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String a1 = email.getText().toString().trim();
                                String b1 = password.getText().toString().trim();
                                String a = doc.getString("Email");
                                String b = doc.getString("Password");
                                if(a1.equalsIgnoreCase("") || b1.equalsIgnoreCase("")){

                                    return ;
                                }
                                if (a != null && a.equalsIgnoreCase(a1) && b != null && b.equalsIgnoreCase(b1)) {
                                    Intent home = new Intent(context, HomePage.class);
                                    // go to the home page after successful login
                                    context.startActivity(home);
                                    Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show();

                                    break;
                                }else{
                                    incorrect = true;
                                }
                            }
                            if(incorrect){
                                Toast.makeText(context, "Incorrect Email or Password. Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public boolean getLoginStatus() {
        return LoginStatus;
    }
}
