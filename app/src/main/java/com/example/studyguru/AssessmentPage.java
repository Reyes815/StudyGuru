package com.example.studyguru;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssessmentPage extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef = db.collection("questions");

    private Assessment_Adapter adapter;
    private List<Assessment> dataset = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_page);
        loadData();
        setUpRecyclerView();
        System.out.println("IM");
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
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                System.out.println("Im in");
                if (task.isSuccessful()) {
                    dataset.clear(); // Clear existing data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert each document to your data model and add to the dataset
                        System.out.println("Added");
                        Assessment data = document.toObject(Assessment.class);
                        dataset.add(data);
                    }
                    adapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed
                } else {
                    // Handle errors
                    System.out.println("Error");
                    Log.e("AssessmentPage", "Error fetching data: " + task.getException());
                }
            }
        });
    }
}
