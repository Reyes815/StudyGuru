package com.example.studyguru;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {
    Button btnTutorial;

    Button btnTraining;

    Button btnPlaynow;

    ImageView training_button;
    ImageView adventure_button;

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
        btnPlaynow = findViewById(R.id.btnPlayNow);

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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customView = getLayoutInflater().inflate(R.layout.custom_dialogue_pop_up_play_now, null);
        builder.setView(customView);


        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnPlaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        training_button = customView.findViewById(R.id.training_button);
        adventure_button = customView.findViewById(R.id.adventure_button);

        training_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), primaryTraining.class);
                startActivity(intent);
            }
        });

        adventure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdventureLevel1.class);
                startActivity(intent);
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