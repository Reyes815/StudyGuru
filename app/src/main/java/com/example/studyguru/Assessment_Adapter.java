package com.example.studyguru;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Assessment_Adapter extends RecyclerView.Adapter<Assessment_Adapter.AssessmentHolder> {
    private static final int Max_item = 10;
    private List<Assessment> localDataset;
    private static final int TEXT_CHANGE_LISTENER_KEY = R.id.text_change_listener_key;;

    private int textColor = Color.parseColor("#F07B3F");

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
        // Set a unique identifier for the EditText view, e.g., using the position
        holder.answer.setTag(position);

        // Set text color dynamically
        holder.answer_key.setTextColor(textColor);

        // Remove previous TextWatcher to avoid issues
        if (holder.answer.getTag(TEXT_CHANGE_LISTENER_KEY) != null) {
            holder.answer.removeTextChangedListener((TextWatcher) holder.answer.getTag(TEXT_CHANGE_LISTENER_KEY));
        }

        // Set the existing answer value in the EditText
        holder.answer.setText(current.getAnswer());

        // Create a new TextWatcher
        TextWatcher textChangeListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the dataset with the new answer when the text changes
                current.setAnswer(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text changes
            }
        };

        // Add the new TextWatcher to the EditText
        holder.answer.addTextChangedListener(textChangeListener);

        // Save the TextWatcher in the view's tag for future reference
        holder.answer.setTag(TEXT_CHANGE_LISTENER_KEY, textChangeListener);
    }

    @Override
    public int getItemCount() {
        return Math.min(localDataset.size(),Max_item);
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

    @Override
    public void onViewRecycled(@NonNull AssessmentHolder holder) {
        // Remove the TextChangeListener to avoid issues
        if (holder.answer.getTag(TEXT_CHANGE_LISTENER_KEY) != null) {
            holder.answer.removeTextChangedListener((TextWatcher) holder.answer.getTag(TEXT_CHANGE_LISTENER_KEY));
        }

        super.onViewRecycled(holder);
    }

    public void updateTextColor(int newColor) {
        this.textColor = newColor;
        notifyDataSetChanged(); // Notify the adapter that the data set has changed
    }
}
