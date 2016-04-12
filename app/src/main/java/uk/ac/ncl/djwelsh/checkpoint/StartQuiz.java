package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;

public class StartQuiz extends AppCompatActivity {

    private Quiz quiz = null;
    private Subject subject = null;
    private Deck deck = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        Intent intent = getIntent();
        subject = intent.getParcelableExtra("subject");

        CardsDataSource cardsDB = new CardsDataSource(this);
        cardsDB.open();

        deck = new Deck(cardsDB.getCardBySubject(String.valueOf(subject.getId())));
        quiz = new Quiz(subject, deck);
    }

    public void startEasiestToHardest(View view) {
        Collections.sort(deck.cards, new Comparator<Card>() {

            @Override
            public int compare(Card lhs, Card rhs) {
                if (Integer.valueOf(lhs.getRating()) < Integer.valueOf(rhs.getRating())) {
                    return -1;
                } else if (Integer.valueOf(lhs.getRating()) > Integer.valueOf(rhs.getRating())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        quiz.setQuizType("easiestToHardest");
        quiz.setCorrectCount(0);
        quiz.setIncorrectCount(0);

        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
    }

    public void startQuickFire(View view) {

        quiz.setQuizType("quickFire");
        quiz.setCorrectCount(0);
        quiz.setIncorrectCount(0);

        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quiz", quiz);

        System.out.println("PRE:    " + quiz.toString());
        startActivity(intent);
    }

    public void startRandom(View view) {

        quiz.setQuizType("random");
        quiz.setCorrectCount(0);
        quiz.setIncorrectCount(0);

        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
    }

    public void startByPercentWrong() {
        Collections.sort(deck.cards, new Comparator<Card>(){

            @Override
            public int compare(Card lhs, Card rhs) {
                return Double.compare(lhs.getPercentCorrect(), rhs.getPercentCorrect());
            }
        });
    }
}
