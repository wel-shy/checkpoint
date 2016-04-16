package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class AddCard extends AppCompatActivity {

    Subject subject;
    int cardCount = 1;

    private CardsDataSource datasource;

    public final static String EXTRA_MESSAGE = "uk.ac.ncl.djwelsh.checkpoint.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Intent intent = getIntent();
        subject = intent.getParcelableExtra("subject");

//        System.out.println("SUBJECT ID:_______________" + subject.getId());
        datasource = new CardsDataSource(this);
        datasource.open();
    }

    /**
     * Save a card to the database.
     *
     * TODO fix rating being saved as 'subject'
     *
     * @param view
     */
    public void saveCard(View view) {
        EditText rawCardName = (EditText) findViewById(R.id.card_title);
        EditText rawQuestion = (EditText) findViewById(R.id.card_overview_question);
        EditText rawAnswer = (EditText) findViewById(R.id.card_question);
        RatingBar rawDifficulty = (RatingBar) findViewById(R.id.card_difficulty);

        String[] data = {
                rawCardName.getText().toString(),               // Name
                rawQuestion.getText().toString(),               // Question
                rawAnswer.getText().toString(),                 // Answer
                "0",                                            // Correct Count
                "0",                                            // Incorrect Count
                String.valueOf(subject.getId()),                // Subject
                Integer.toString(rawDifficulty.getNumStars())   // Difficulty
        };

        datasource.createCard(data);
        cardCount++;

        rawCardName.setText("");
        rawQuestion.setText("");
        rawAnswer.setText("");
        rawDifficulty.setNumStars(0);

        TextView counter = (TextView) findViewById(R.id.card_counters);
        counter.setText("Card " + cardCount);
    }

    /**
     * Finish creating deck and transfer back to subject overview.
     *
     * TODO Fix null subject
     *
     * @param view
     */
    public void finishDeck(View view) {

        Intent intent = new Intent(this, SubjectOverview.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}
