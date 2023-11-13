package com.example.studyguru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Assessment_Adapter extends RecyclerView.Adapter<Assessment_Adapter.AssessmentHolder> {
    private List<Assessment> localDataset;
    public Assessment_Adapter(List<Assessment> dataSet){
        localDataset = dataSet;
    }
    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);
        return new AssessmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        Assessment current = localDataset.get(position);
        holder.question.setText(current.getQuestion());
        holder.answer_key.setText(current.getAnswer_key());
        holder.answer.setText(current.getAnswer());
    }

    @Override
    public int getItemCount() {
        return localDataset.size();
    }

    public static class AssessmentHolder extends RecyclerView.ViewHolder{
        TextView question;
        TextView answer_key;
        EditText answer;
        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer_key = itemView.findViewById(R.id.answer_key);
            answer = itemView.findViewById(R.id.answer);
        }

        public TextView getQuestion() {
            return question;
        }

        public TextView getAnswer_key() {
            return answer_key;
        }

        public EditText getAnswer() {
            return answer;
        }
    }
}
