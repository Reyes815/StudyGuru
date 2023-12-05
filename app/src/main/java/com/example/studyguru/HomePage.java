package com.example.studyguru;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {
    Button btnTutorial;

    Button btnTraining;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    public Toolbar toolbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        btnTutorial = findViewById(R.id.btnTutorial);
        btnTraining = findViewById(R.id.btnTraining);
        toolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        User currUser = intent.getParcelableExtra("user");

        if (currUser != null) {
            // Access and use currUser

            NavigationView navigationView = findViewById(R.id.home_page_navview);

            View headerview = navigationView.getHeaderView(0);

            TextView userNameTextView = headerview.findViewById(R.id.Username_curr_user);
            TextView userEmailTextView = headerview.findViewById(R.id.Email_curr_user);

            userEmailTextView.setText(currUser.getEmail());
            userNameTextView.setText("Wazzup");
        } else {


            // Handle the case where currUser is null
        }



        setSupportActionBar(toolbar); // Assuming you have a Toolbar with id 'toolbar'
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = findViewById(R.id.home_page_layout);
        // Set up the ActionBarDrawerToggle with the DrawerLayout
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(intent);
            }
        });

        System.out.println("Running homepage");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}