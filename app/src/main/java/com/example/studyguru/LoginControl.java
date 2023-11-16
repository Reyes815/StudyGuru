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

    private User user;

    public LoginControl(){
        this.firestore = FirebaseFirestore.getInstance();
        this.user = new User();
    }



    public void verifyLogin(EditText email, EditText password, Context context){
        if (email.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(context, "Please enter valid password", Toast.LENGTH_SHORT).show();
        }
        firestore.collection("user")
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
                                user.setEmail(a);
                                user.setPassword(b);
                                if(a1.equalsIgnoreCase("") || b1.equalsIgnoreCase("")){

                                    return ;
                                }
                                if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(a1) && user.getPassword() != null && user.getPassword().equalsIgnoreCase(b1)) {
                                    // start the introduction after successful login
                                    Intent introduction = new Intent(context, IntroductionActivity.class);
                                    context.startActivity(introduction);
                                    Toast.makeText(context, "The story begins!", Toast.LENGTH_SHORT).show();

//                                    Intent home = new Intent(context, HomePage.class);
//                                    // go to the home page after successful login
//                                    context.startActivity(home);
//                                    Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show();

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
