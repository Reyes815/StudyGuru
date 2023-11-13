package com.example.studyguru;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Assessment_item extends AppCompatActivity {
    TextView answer_key;
    TextView question;
    EditText answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_item);

        answer_key = findViewById(R.id.answer_key);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);

    }

}
