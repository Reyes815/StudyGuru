package com.example.studyguru;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

public class AssessmentPage extends AppCompatActivity {
    int load = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef;
    FloatingActionButton submit_button;
    private Assessment_Adapter adapter;
    private List<Assessment> dataset = new ArrayList<>();
    private int scoreUser;
    private RecyclerView rView;
    private String type;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_page);
        submit_button = findViewById(R.id.submit_button);
        rView = findViewById(R.id.rView);
        myDialog = new Dialog(this);
        scoreUser = 0;
        Intent intent = getIntent();
        if(intent != null ){
            type = intent.getStringExtra("type");
        }

        questionRef = db.collection(type);

        loadData();
        Log.d("AssessmentPage", String.valueOf(dataset.size()));
        setUpRecyclerView();

            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if the user's answers match the correct answers
//                    adapter.updateTextColor(Color.WHITE);
                    for (int i = 0; i < dataset.size(); i++) {
                            String userAnswer = String.valueOf(dataset.get(i).getAnswer());

                            String correctAnswer = dataset.get(i).getAnswer_key();

                        Log.d("AssessmentPage", "UserAnswer: " + userAnswer + " correctAnswer: " + correctAnswer);
                            if(userAnswer != null){
                                Pattern pattern = Pattern.compile(correctAnswer);
                                Matcher matcher = pattern.matcher(userAnswer);

                                if (userAnswer.toLowerCase().matches(correctAnswer)) {
                                    // User's answer is correct for this question
                                    Log.d("AssessmentPage", "Question " + (i + 1) + ": Correct!");
                                    scoreUser++;
                                } else {
                                    // User's answer is incorrect for this question
                                    //Log.d("AssessmentPage", correctAnswer + " sdsdfsdfsd");

                                    Log.d("AssessmentPage", "Question " + (i + 1) + ": Incorrect!");
                                }
                            }else{
                                Log.d("AssessmentPage", "Error");
                            }
                        }
                    if(scoreUser >= 6){
                        showGamePopupSuccess();
                    }else{
                        showGamePopupFail();
                    }

                    Log.d("AssessmentPage", String.valueOf(scoreUser));
                }
            });
    }



    private void setUpRecyclerView(){
        // Set a LinearLayoutManager to your RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rView.setLayoutManager(layoutManager);

        // Assuming questionRef is your data source
            Query query = questionRef.orderBy("priority", Query.Direction.DESCENDING);

        adapter = new Assessment_Adapter(dataset);
        rView.setAdapter(adapter);

    }

    private void loadData() {
        questionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful() && load == 0) {
                    dataset.clear(); // Clear existing data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert each document to your data model and add to the dataset
                            Assessment data = document.toObject(Assessment.class);
                            data.setAnswer("");

                            dataset.add(data);

                            //count++;
                    }
                    Collections.shuffle(dataset);
                    adapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed
                } else {
                    // Handle errors
                    Log.e("AssessmentPage", "Error fetching data: " + task.getException());
                }
            }
        });
    }

    private void showGamePopupFail() {
        myDialog.setContentView(R.layout.game_popup_fail);

        // Find the close button after setting the content view
        Button close = myDialog.findViewById(R.id.close_btn);
        Button home = myDialog.findViewById(R.id.home_btn);
        Button retry = myDialog.findViewById(R.id.next_btn);
        Button prev = myDialog.findViewById(R.id.prev_btn);
        TextView score = myDialog.findViewById(R.id.textView3);
        String text = "You have scored "+ scoreUser + " and have failed the assessment. Do better next time Soldier!";
        score.setText(text);

        prev.setVisibility(View.GONE);
        retry.setVisibility(View.GONE);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_popup = new Intent(getApplicationContext(), Training_Level_Menu.class);
                startActivity(home_popup);
            }
        });

//        retry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void showGamePopupSuccess() {
        myDialog.setContentView(R.layout.game_popup);
        Button close = myDialog.findViewById(R.id.close_btn);
        Button prev = myDialog.findViewById(R.id.prev_btn);
        Button home = myDialog.findViewById(R.id.home_btn);
        Button next = myDialog.findViewById(R.id.next_btn);
        TextView score = myDialog.findViewById(R.id.textView3);
        String text = "You have scored "+ scoreUser + " and have passed the assessment. Congratulations Soldier!";
        score.setText(text);
        prev.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_popup = new Intent(getApplicationContext(), Training_Level_Menu.class);
                startActivity(home_popup);
            }
        });
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent next_popup = new Intent(getApplicationContext(), AdventureLevel2.class);
//                startActivity(next_popup);
//            }
//        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
