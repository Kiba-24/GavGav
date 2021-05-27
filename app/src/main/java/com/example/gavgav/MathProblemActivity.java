package com.example.gavgav;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import java.util.concurrent.ThreadLocalRandom;

import static com.example.gavgav.MainActivity.game;


public class MathProblemActivity extends AppCompatActivity {
    TextView task, rAnswers;
    EditText answer;
    Button buttonAnswer;
    ImageButton btBack, btWeb;
    Spinner spinner;
    float correctAnswer;
    int difficultyTask;
    int newCoins;
    int rightProblems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_problem);
        Game game = MainActivity.game;
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
        btWeb = (ImageButton) findViewById(R.id.btWeb);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonAnswer) {
                    String gamerAnswer = answer.getText().toString();
                    if (difficultyTask != 3 && isNumeric(gamerAnswer) && Integer.parseInt(gamerAnswer) == correctAnswer) {
                        Toast.makeText(getApplicationContext(), "верно", Toast.LENGTH_SHORT).show();
                        newCoins = newCoins + (int)Math.pow(difficultyTask, 3);
                        Log.d("coins ", newCoins + " "+difficultyTask);


                        rightProblems = rightProblems + 1;
                        rAnswers.setText("Верныйх ответов: " + rightProblems+"/3");

                        makeProblem(difficultyTask);

                    } else if(difficultyTask == 3 && isNumeric(gamerAnswer) && Float.parseFloat(gamerAnswer) == correctAnswer){
                        Toast.makeText(getApplicationContext(), "верно", Toast.LENGTH_SHORT).show();
                        newCoins = newCoins + difficultyTask;


                        rightProblems = rightProblems + 1;
                        rAnswers.setText("Верныйх ответов: " + rightProblems+"/3");

                        makeProblem(difficultyTask);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "неверно", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(v.getId()==R.id.btBack){
                    goBack(0);
                }
                else if(v.getId()==R.id.btWeb){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ibb.co/H7shR7D"));
                    startActivity(intent);
                }


            }
        };


        buttonAnswer.setOnClickListener(listener);
        btBack.setOnClickListener(listener);
        btWeb.setOnClickListener(listener);


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
                    int min1 = -10; // Начальное значение диапазона - "от"
                    int max1 = 10; // Конечное значение диапазона - "до"
                    int a1 = ThreadLocalRandom.current().nextInt(min1, max1 + 1);
                    int b1 = ThreadLocalRandom.current().nextInt(min1, max1 + 1);
                    int z1 = min1 + (int) (Math.random() * max1);
                    if (z1%2==0) { //плюс
                        if(a1 >= 0 && b1>=0) {
                            task.setText(a1 + "+" + b1 + "=");
                            correctAnswer = a1 + b1;
                        }
                        else if(a1 >= 0 && b1<0) {
                            task.setText(a1 + "+(" + b1 + ")=");
                            correctAnswer = a1 + b1;
                        }
                        else if(a1 < 0 && b1<0) {
                            task.setText(a1 + "+(" + b1 + ")=");
                            correctAnswer = a1 + b1;
                        }
                        else if(a1 < 0 && b1>=0) {
                            task.setText(a1 + "+" + b1 + "=");
                            correctAnswer = a1 + b1;
                        }
                    } else{ //минус
                        if(a1 >= 0 && b1>=0) {
                            task.setText(a1 + "-" + b1 + "=");
                            correctAnswer = a1 - b1;
                        }
                        else if(a1 >= 0 && b1<0) {
                            task.setText(a1 + "-(" + b1 + ")=");
                            correctAnswer = a1 - b1;
                        }
                        else if(a1 < 0 && b1<0) {
                            task.setText(a1 + "-(" + b1 + ")=");
                            correctAnswer = a1 - b1;
                        }
                        else if(a1 < 0 && b1>=0) {
                            task.setText(a1 + "-" + b1 + "=");
                            correctAnswer = a1 - b1;
                        }
                    }

                    break;
                case 2:
                    int min2 = -100; // Начальное значение диапазона - "от"
                    int max2 = 100; // Конечное значение диапазона - "до"
                    int a2 = ThreadLocalRandom.current().nextInt(min2, max2 + 1);
                    int b2 = ThreadLocalRandom.current().nextInt(min2, max2 + 1);
                    int z2 = min2 + (int) (Math.random() * max2);
                    if (z2%2==0) { //плюс
                        if(a2 >= 0 && b2>=0) {
                            task.setText(a2 + "+" + b2 + "=");
                            correctAnswer = a2 + b2;
                        }
                        else if(a2 >= 0 && b2<0) {
                            task.setText(a2 + "+(" + b2 + ")=");
                            correctAnswer = a2 + b2;
                        }
                        else if(a2 < 0 && b2<0) {
                            task.setText(a2 + "+(" + b2 + ")=");
                            correctAnswer = a2 + b2;
                        }
                        else if(a2 < 0 && b2>=0) {
                            task.setText(a2 + "+" + b2 + "=");
                            correctAnswer = a2 + b2;
                        }
                    } else{ //минус
                        if(a2 >= 0 && b2>=0) {
                            task.setText(a2 + "-" + b2 + "=");
                            correctAnswer = a2 - b2;
                        }
                        else if(a2 >= 0 && b2<0) {
                            task.setText(a2 + "-(" + b2 + ")=");
                            correctAnswer = a2 - b2;
                        }
                        else if(a2 < 0 && b2<0) {
                            task.setText(a2 + "-(" + b2 + ")=");
                            correctAnswer = a2 - b2;
                        }
                        else if(a2 < 0 && b2>=0) {
                            task.setText(a2 + "-" + b2 + "=");
                            correctAnswer = a2 - b2;
                        }
                    }
                    break;
                case 3:
                    int min3 = -10; // Начальное значение диапазона - "от"
                    int max3 = 10; // Конечное значение диапазона - "до"
                    float a3 = ThreadLocalRandom.current().nextInt(min3, max3 + 1);
                    float b3 = ThreadLocalRandom.current().nextInt(min3, max3 + 1);
                    a3 = (float) round(a3, 1);
                    b3 = (float) round(b3, 1);
                    int z3 = min3 + (int) (Math.random() * max3);
                    if (z3%2==0) { //плюс
                        if(a3 >= 0 && b3>=0) {
                            task.setText(a3 + "+" + b3 + "=");
                            correctAnswer = a3 + b3;
                        }
                        else if(a3 >= 0 && b3<0) {
                            task.setText(a3 + "+(" + b3 + ")=");
                            correctAnswer = a3 + b3;
                        }
                        else if(a3 < 0 && b3<0) {
                            task.setText(a3 + "+(" + b3 + ")=");
                            correctAnswer = a3 + b3;
                        }
                        else if(a3 < 0 && b3>=0) {
                            task.setText(a3 + "+" + b3 + "=");
                            correctAnswer = a3 + b3;
                        }
                    } else{ //минус
                        if(a3 >= 0 && b3>=0) {
                            task.setText(a3 + "-" + b3 + "=");
                            correctAnswer = a3 - b3;
                        }
                        else if(a3 >= 0 && b3<0) {
                            task.setText(a3 + "-(" + b3 + ")=");
                            correctAnswer = a3 - b3;
                        }
                        else if(a3 < 0 && b3<0) {
                            task.setText(a3 + "-(" + b3 + ")=");
                            correctAnswer = a3 - b3;
                        }
                        else if(a3 < 0 && b3>=0) {
                            task.setText(a3 + "-" + b3 + "=");
                            correctAnswer = a3 - b3;
                        }
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
        Log.d("go home", "" + newCoins + " " + rightProblems);
        int whatO = game.getWhatObject();
        if (rightProblems == 3){
            switch (whatO) { //1 - будка, 2 - куст, 3 - мяч, 4 - миска
                case 1:
                    game.setArcSleep(0);
                    break;
                case 2:
                    game.setArcNeed(0);
                    break;
                case 3:
                    game.setArcHappy(0);
                    break;
                case 4:
                    game.setArcEat(0);
                    break;
            }
        }
        game.setNewCoins(newCoins);
        finish();
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


}







