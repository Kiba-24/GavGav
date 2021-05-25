package com.example.gavgav;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MathProblemActivity extends AppCompatActivity {
    TextView task, rAnswers;
    EditText answer;
    Button buttonAnswer;
    ImageButton btBack;
    Spinner spinner;
    float correctAnswer;
    int difficultyTask;
    int newCoins;
    int rightProblems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_problem);
        newCoins = 0;
        rightProblems = 0;
        spinner = findViewById(R.id.spinnerClass);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.class_problem,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        difficultyTask = spinner.getSelectedItemPosition();
        task = (TextView) findViewById(R.id.task);
        rAnswers = (TextView) findViewById(R.id.rAnswers);
        answer = (EditText) findViewById(R.id.answer);
        buttonAnswer = (Button) findViewById(R.id.buttonAnswer);
        btBack = (ImageButton) findViewById(R.id.btBack);
        View.OnClickListener listener = new View.OnClickListener() { 
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonAnswer) {
                    String gamerAnswer = answer.getText().toString();
                    if (Integer.parseInt(gamerAnswer) == correctAnswer) {
                        Toast.makeText(getApplicationContext(), "верно", Toast.LENGTH_SHORT).show();
                        newCoins = newCoins + difficultyTask;


                        rightProblems = rightProblems + 1;
                        rAnswers.setText("Верныйх ответов: " + rightProblems+"/3");

                        makeProblem(difficultyTask);
                    } else {
                        Toast.makeText(getApplicationContext(), "неверно", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(v.getId()==R.id.btBack){
                    goBack(0);
                }


            }
        };


        buttonAnswer.setOnClickListener(listener);
        btBack.setOnClickListener(listener);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                difficultyTask = parent.getSelectedItemPosition();
                makeProblem(difficultyTask);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        };
        spinner.setOnItemSelectedListener(itemSelectedListener);


    }
    private void makeProblem(int difficultyTask){
        if (difficultyTask != 0) {
            answer.setText("");
            switch (difficultyTask) {
                case 1:
                    int min1 = 0; // Начальное значение диапазона - "от"
                    int max1 = 10; // Конечное значение диапазона - "до"
                    int a1 = min1 + (int) (Math.random() * max1);
                    int b1 = min1 + (int) (Math.random() * max1);
                    correctAnswer = a1 + b1;
                    if ((a1 % b1) % 2== 0) {
                        if(a1 >= b1) {
                            task.setText(a1 + "-" + b1 + "=");
                            correctAnswer = a1 - b1;
                        }
                        else{task.setText(b1 + "-" + a1 + "=");
                            correctAnswer = b1 - a1;}
                    } else{
                        task.setText(a1 + "+" + b1 + "=");
                        correctAnswer = b1 + a1;
                    }

                    break;
                case 2:
                    int min2 = 0; // Начальное значение диапазона - "от"
                    int max2 = 100; // Конечное значение диапазона - "до"
                    int a2 = min2 + (int) (Math.random() * max2);
                    int b2 = min2 + (int) (Math.random() * max2);
                    correctAnswer = a2 + b2;
                    if ((a2 % b2) % 2== 0) {
                        if(a2 >= b2) {
                            task.setText(a2 + "-" + b2 + "=");
                            correctAnswer = a2 - b2;
                        }
                        else{task.setText(b2 + "-" + a2 + "=");
                            correctAnswer = b2 - a2;}
                    } else{
                        task.setText(a2 + "+" + b2 + "=");
                        correctAnswer = b2 + a2;
                    }
                    break;
                case 3:
                    int min3 = 0; // Начальное значение диапазона - "от"
                    int max3 = 10; // Конечное значение диапазона - "до"
                    float a3 = (float) (min3 +  (Math.random() * max3));
                    float b3 = (float) (min3 +  (Math.random() * max3));
                    a3 = (float) round(a3, 1);
                    b3 = (float) round(b3, 1);
                    correctAnswer =  a3 + b3;
                    if ((a3 % b3) % 2== 0) {
                        if(a3 >= b3) {
                            task.setText(a3 + "-" + b3 + "=");
                            correctAnswer = a3 - b3;
                        }
                        else{task.setText(b3 + "-" + a3 + "=");
                            correctAnswer = b3 - a3;}
                    } else{
                        task.setText(a3 + "+" + b3 + "=");
                        correctAnswer = b3 + a3;
                    }
                    break;



            }
            if (rightProblems == 3){
                goBack(newCoins);
            }
        }
    }
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    private void goBack(int newCoins){
        Intent i = new Intent();
        Log.d("go home", "" + newCoins + " " + rightProblems);
        i.putExtra("newCoins", newCoins);
        setResult(RESULT_OK, i);
        finish();
    }


}







