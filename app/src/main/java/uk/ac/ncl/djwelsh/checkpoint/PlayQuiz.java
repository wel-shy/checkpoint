package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class PlayQuiz extends AppCompatActivity {

    private Quiz quiz;
    private Card currentCard;
    private boolean isQuestion = false;
    private TextView points;
    private TextView infContainer;
    private TextView timeRemaining;
    private Button wrongAns;
    private Button rightAns;
    private int currentPoints = 0;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        Intent intent = getIntent();
        quiz = intent.getParcelableExtra("quiz");

        points = (TextView) findViewById(R.id.current_points);
        infContainer = (TextView) findViewById(R.id.infContainer);
        timeRemaining = (TextView) findViewById(R.id.timer);
        wrongAns = (Button) findViewById(R.id.wrongAns);
        rightAns = (Button) findViewById(R.id.rightAns);

        System.out.println("POS:    " + quiz.toString());
        System.out.println(quiz.getDeck().getCard(0));

        points.setText(String.valueOf(currentPoints));
        updateCard();
        infContainer.setText(currentCard.getQuestion());

        // Add timer to quiz
        if(Objects.equals(quiz.getQuizType(), "quickFire")) {
            timeRemaining.setVisibility(View.VISIBLE);
            timer = new CountDownTimer(120000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String remaining = "seconds remaining: " + millisUntilFinished / 1000;
                    timeRemaining.setText(remaining);
                }

                @Override
                public void onFinish() {
                    finishQuiz();
                }
            };

            timer.start();
        }
    }

    /**
     * Update question and hide buttons.
     */
    private void displayQuestion() {
        updateCard();
        infContainer.setText(currentCard.getQuestion());
        wrongAns.setVisibility(View.INVISIBLE);
        rightAns.setVisibility(View.INVISIBLE);
    }

    /**
     * If the current state is a question then show the answer.
     */
    public void containerPress(View view) {

        if(isQuestion) {
            displayAnswer();
            isQuestion = false;
        } else {
            isQuestion = true;
        }
    }

    private void displayAnswer() {
        infContainer.setText(currentCard.getAnswer());
        wrongAns.setVisibility(View.VISIBLE);
        rightAns.setVisibility(View.VISIBLE);
    }

    // TODO add points rating.
    public void correctAnswer(View view) {
        quiz.setCurrentCardIdx(quiz.getCurrentCardIdx() + 1);
        currentPoints += 10;
//        currentPoints += 10;
        quiz.setPoints(quiz.getPoints() + 10);
        quiz.setCorrectCount(quiz.getCorrectCount() + 1);
        points.setText(String.valueOf(currentPoints));
        currentCard.setNumCorrect(currentCard.getNumCorrect() + 1);
        currentCard.updataCard(this);

        if(quiz.getCurrentCardIdx() == quiz.getDeck().deckLength){
            finishQuiz();
        } else {
            updateCard();
            displayQuestion();
        }
    }

    public void incorrectAnswer(View view) {
        quiz.setCurrentCardIdx(quiz.getCurrentCardIdx() + 1);
        quiz.setIncorrectCount(quiz.getIncorrectCount() + 1);

        currentCard.setNumIncorrect(currentCard.getNumIncorrect() + 1);
        currentCard.updataCard(this);

        if(quiz.getCurrentCardIdx() == quiz.getDeck().deckLength){
            finishQuiz();
        } else {
            updateCard();
            displayQuestion();
        }
    }

    private void finishQuiz() {
        if(timer != null) {
            timer.cancel();
        }
        infContainer.setText("FINISHED");

//        quiz.setPoints(currentPoints);

        QuizDataSource quizDB = new QuizDataSource(this);
        quizDB.open();
        quizDB.storeQuiz(quiz);
        quizDB.close();

        Intent intent = new Intent(this, ViewQuizResults.class);
        intent.putExtra("subject", quiz.getSubject());
        startActivity(intent);
    }

    private void updateCard() {
        currentCard = quiz.getCurrentCard();
    }
}
