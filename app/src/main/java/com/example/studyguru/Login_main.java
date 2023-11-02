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

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Button login = (Button) findViewById(R.id.login_button);
        Button register = (Button) findViewById(R.id.goToRegister_button);
        EditText email = (EditText) findViewById(R.id.email_address_login);
        EditText pwd = (EditText) findViewById(R.id.password_login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("")) {
                    Toast.makeText(Login_main.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                } else if (pwd.getText().toString().equals("")) {
                    Toast.makeText(Login_main.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
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
                                        String b1 = pwd.getText().toString().trim();
                                        String a = doc.getString("Email");
                                        String b = doc.getString("Password");
                                        if(a1.equalsIgnoreCase("") || b1.equalsIgnoreCase("")){

                                            return ;
                                        }
                                        if (a != null && a.equalsIgnoreCase(a1) && b != null && b.equalsIgnoreCase(b1)) {
                                            Intent home = new Intent(Login_main.this, HomePage.class);
                                            // go to the home page after successful login
                                            startActivity(home);
                                            Toast.makeText(Login_main.this, "Logged In", Toast.LENGTH_SHORT).show();

                                            break;
                                        }else{
                                                incorrect = true;
                                        }
                                    }
                                    if(incorrect){
                                        Toast.makeText(Login_main.this, "Incorrect Email or Password. Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                //go to register for no acc
                startActivity(intent);
            }
        });
    }
}