package com.example.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class QuestionAddScreen extends AppCompatActivity {

    private EditText editTextQuestion;
    private EditText editTextOption1;
    private EditText editTextOption2;
    private EditText editTextOption3;

    private Spinner spinnerCorrectAnswer;
    private Spinner spinnerDifficulty;
    private Spinner spinnerCategory;

    private Button buttonConfirm;
    private Button buttonBackToStartingScreen;

    private QuizDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_add_screen);

        dbHelper = QuizDbHelper.getInstance(this);

        editTextQuestion = findViewById(R.id.edit_text_question);
        editTextOption1 = findViewById(R.id.edit_text_option1);
        editTextOption2 = findViewById(R.id.edit_text_option2);
        editTextOption3 = findViewById(R.id.edit_text_option3);

        spinnerCorrectAnswer = findViewById(R.id.spinner_correct_answer);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        spinnerCategory = findViewById(R.id.spinner_category);

        buttonConfirm = findViewById(R.id.button_confirm);
        buttonBackToStartingScreen = findViewById(R.id.button_back_to_starting_screen);

        // Setup spinners
        ArrayAdapter<CharSequence> adapterCorrectAnswer = ArrayAdapter.createFromResource(
                this, R.array.correct_answers, android.R.layout.simple_spinner_item);
        adapterCorrectAnswer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCorrectAnswer.setAdapter(adapterCorrectAnswer);

        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(
                this, R.array.difficulty_levels, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from EditTexts and Spinners
                String question = editTextQuestion.getText().toString();
                String option1 = editTextOption1.getText().toString();
                String option2 = editTextOption2.getText().toString();
                String option3 = editTextOption3.getText().toString();

                // Check if any of the fields are empty
                if (question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty()) {
                    // Display a message indicating that all fields must be completed
                    Toast.makeText(QuestionAddScreen.this, "Completeaza toate campurile", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with adding the question to the database
                    int correctAnswerIndex = spinnerCorrectAnswer.getSelectedItemPosition() + 1;
                    String difficulty = spinnerDifficulty.getSelectedItem().toString();
                    String category = spinnerCategory.getSelectedItem().toString();

                    // Determine the category ID based on the selected category
                    int categoryID;
                    switch (category) {
                        case "Programare":
                            categoryID = Category.PROGRAMARE;
                            break;
                        case "Biologie":
                            categoryID = Category.BIOLOGIE;
                            break;
                        case "Istorie":
                            categoryID = Category.ISTORIE;
                            break;
                        default:
                            // Default to PROGRAMARE if no category matches
                            categoryID = Category.PROGRAMARE;
                            break;
                    }

                    // Add the question to the database
                    Question newQuestion = new Question(question, option1, option2, option3,
                            correctAnswerIndex, difficulty, categoryID);
                    dbHelper.addQuestion(newQuestion);

                    // Clear the EditText fields
                    editTextQuestion.getText().clear();
                    editTextOption1.getText().clear();
                    editTextOption2.getText().clear();
                    editTextOption3.getText().clear();

                    // Display a success message
                    Toast.makeText(QuestionAddScreen.this, "Intrebare adaugata cu succes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackToStartingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to StartingScreenActivity
                Intent intent = new Intent(QuestionAddScreen.this, StartingScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
