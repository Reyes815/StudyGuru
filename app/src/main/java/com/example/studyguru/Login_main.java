package com.example.studyguru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Login_main extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Running login");

        View rootView = findViewById(android.R.id.content);
        LoginUI loginUI = new LoginUI(rootView);

        Button loginButton = loginUI.getLoginButton();
        Button registerButton = loginUI.getRegisterButton();
        EditText emailTextbox = loginUI.getEmailTextbox();
        EditText passwordTextbox = loginUI.getPasswordTextbox();

        LoginControl loginControl = new LoginControl();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginControl.verifyLogin(emailTextbox, passwordTextbox, Login_main.this);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                //go to register for no acc
                startActivity(intent);
            }
        });
    }
}