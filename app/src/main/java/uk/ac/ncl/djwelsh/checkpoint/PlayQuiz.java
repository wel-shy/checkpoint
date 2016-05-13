package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Activity that manages playing the quiz.
 */
public class PlayQuiz extends AppCompatActivity {

    private Quiz quiz;
    private int deckPos = 0;
    private Deck deck;

    private boolean isQuestion = true;
    private TextView points;
    private TextView infContainer;
    private TextView timeRemaining;
    private Button wrongAns;
    private Button rightAns;
    private int currentPoints = 0;
    private CountDownTimer timer;
    private RatingBar difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        // Get quiz
        Intent intent = getIntent();
        quiz = intent.getParcelableExtra("quiz");

        // Get deck
        deck = quiz.getDeck();

        // Set view
        points = (TextView) findViewById(R.id.current_points);
        infContainer = (TextView) findViewById(R.id.infContainer);
        timeRemaining = (TextView) findViewById(R.id.timer);
        wrongAns = (Button) findViewById(R.id.wrongAns);
        rightAns = (Button) findViewById(R.id.rightAns);
        difficulty = (RatingBar) findViewById(R.id.r_bar);

        // Set points
        points.setText(String.valueOf(currentPoints));

        // Display first card.
        displayCard(deck.getCard(deckPos), isQuestion);

        // Add timer to quiz
        switch (quiz.getQuizType()) {
            case "quickFire":
                timeRemaining.setVisibility(View.VISIBLE);
                timer = new CountDownTimer(120000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String remaining = "" + millisUntilFinished / 1000;
                        timeRemaining.setText(remaining);
                    }

                    @Override
                    public void onFinish() {
                        finishQuiz();
                    }
                };

                timer.start();
                break;

            case "random":

                break;

            case "easiestToHardest":

                break;
        }
    }

    /**
     * Display a card
     *
     * @param card Card to display
     * @param isQuestion Is the current view a question
     */
    public void displayCard(Card card, boolean isQuestion) {

        if (isQuestion) {

            // Show question - Hide buttons
            infContainer.setText(card.getQuestion());
            infContainer.setBackground(getResources().getDrawable(R.drawable.inverted_box));
            infContainer.setTextColor(getResources().getColor(R.color.colorAccent));
            difficulty.setRating(Float.valueOf(deck.getCard(deckPos).getRating()));
            wrongAns.setVisibility(View.INVISIBLE);
            rightAns.setVisibility(View.INVISIBLE);

        } else {

            // Show answer - Show buttons
            infContainer.setBackground(getResources().getDrawable(R.drawable.main_button_box));
            infContainer.setTextColor(getResources().getColor(R.color.colorAccent));
            infContainer.setText(card.getAnswer());
            wrongAns.setVisibility(View.VISIBLE);
            rightAns.setVisibility(View.VISIBLE);

        }
    }

    /**
     * If the current state is a question then show the answer.
     */
    public void containerPress(View view) {

        isQuestion = false;
        displayCard(deck.getCard(deckPos), isQuestion);
    }

    /**
     * Update the card if the answer is correct.
     *
     * @param view
     */
    // TODO add points rating.
    public void correctAnswer(View view) {

        // Update correct count
        deck.getCard(deckPos).setNumCorrect(deck.getCard(deckPos).getNumCorrect() + 1);

        // Update points
        float p = Float.valueOf(deck.getCard(deckPos).getRating()) * 10;
        quiz.setPoints((int) (quiz.getPoints() + p));
        points.setText(String.valueOf(quiz.getPoints()));

        // Set to question and update counter
        isQuestion = true;
        deckPos ++;

        // If not last card - Update card
        if(!(deckPos == deck.deckLength)) {
            displayCard(deck.getCard(deckPos), isQuestion);
        } else {
            finishQuiz();
        }

        // Set quiz counts
        quiz.setCorrectCount(quiz.getCorrectCount() + 1);
    }

    /**
     * Update card if answer is incorrect.
     *
     * @param view activity view
     */
    public void incorrectAnswer(View view) {

        // Update correct count
        deck.getCard(deckPos).setNumIncorrect(deck.getCard(deckPos).getNumIncorrect() + 1);

        // Set to question and update counter
        isQuestion = true;
        deckPos ++;

        // If not last card - Update card
        if(!(deckPos == deck.deckLength)) {
            displayCard(deck.getCard(deckPos), isQuestion);
        } else {
            finishQuiz();
        }

        // Set quiz counts.
        quiz.setIncorrectCount(quiz.getIncorrectCount() + 1);
    }

    /**
     * Finish quiz if max is reached or timer finishes.
     */
    private void finishQuiz() {

        // Cancel timer
        if(timer != null) {
            timer.cancel();
        }

        // Update text.
        infContainer.setText("FINISHED");

        // Update card correct values
        for (int i = 0; i < deck.deckLength; i++) {
            deck.getCard(i).updateCard(this);
        }

        // Save quiz
        QuizDataSource quizDB = new QuizDataSource(this);
        quizDB.open();
        quizDB.storeQuiz(quiz);
        quizDB.close();

        // Show results
        Intent intent = new Intent(this, ViewQuizResults.class);
        intent.putExtra("subject", quiz.getSubject());
        startActivity(intent);
    }
}
