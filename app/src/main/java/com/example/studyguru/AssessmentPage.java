package com.example.studyguru;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class AssessmentPage extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef = db.collection("questions");
    FloatingActionButton submit_button;
    private Assessment_Adapter adapter;
    private List<Assessment> dataset = new ArrayList<>();
    private List<String> userAnswers = new ArrayList<>();
    private int scoreUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_page);
        submit_button = findViewById(R.id.submit_button);

        scoreUser = 0;

        loadData();
        setUpRecyclerView();

            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if the user's answers match the correct answers
                        for (int i = 0; i < dataset.size(); i++) {
                            Assessment assessment = dataset.get(i);
                            String correctAnswer = assessment.getAnswer_key();
                            String userAnswer = assessment.getAnswer();
                            Log.d("AssessmentPage", ""+ userAnswer);

                            if(userAnswer != null){
                                Pattern pattern = Pattern.compile(correctAnswer);
                                Matcher matcher = pattern.matcher(userAnswer);

                                if (userAnswer.matches(correctAnswer)) {
                                    // User's answer is correct for this question
                                    Log.d("AssessmentPage", "Question " + (i + 1) + ": Correct!");
                                    scoreUser++;
                                } else {
                                    // User's answer is incorrect for this question
                                    Log.d("AssessmentPage", "Question " + (i + 1) + ": Incorrect!");
                                }
                            }else{
                                Log.d("AssessmentPage", "User answers are not provided or not complete");
                            }
                        }

                    Log.d("AssessmentPage", "" + scoreUser);
                }
            });
    }


    private void setUpRecyclerView(){
        RecyclerView rView = findViewById(R.id.rView);

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
            int count = 1;
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    dataset.clear(); // Clear existing data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert each document to your data model and add to the dataset
                        if(count<=2) {
                            Assessment data = document.toObject(Assessment.class);
                            dataset.add(data);
                            count++;
                        }else{
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed
                } else {
                    // Handle errors
                    Log.e("AssessmentPage", "Error fetching data: " + task.getException());
                }
            }
        });
    }
}
