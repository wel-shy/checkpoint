package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import javax.security.auth.SubjectDomainCombiner;

public class AddCard extends AppCompatActivity {

    String subject;
    int cardCount = 0;

    private CardsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Intent intent = getIntent();
        String rawId = intent.getStringExtra(SubjectOverview.EXTRA_MESSAGE);
        long id = Long.parseLong(rawId);

        subject = String.valueOf(id);
        System.out.println("SUBJECT ID:_______________" + id);
        datasource = new CardsDataSource(this);
        datasource.open();
    }

    /**
     * Save a card to the database.
     *
     * @param view
     */
    public void saveCard(View view) {
        EditText rawCardName = (EditText) findViewById(R.id.card_title);
        EditText rawQuestion = (EditText) findViewById(R.id.card_question);
        EditText rawAnswer = (EditText) findViewById(R.id.card_answer);
        RatingBar rawDifficulty = (RatingBar) findViewById(R.id.card_difficulty);

        String[] data = {
                rawCardName.getText().toString(),               // Name
                rawQuestion.getText().toString(),               // Question
                rawAnswer.getText().toString(),                 // Answer
                subject,                                        // Subject
                Integer.toString(rawDifficulty.getNumStars())   // Difficulty
        };

        datasource.createCard(data);
        cardCount++;

        rawCardName.setText("");
        rawQuestion.setText("");
        rawAnswer.setText("");
        rawDifficulty.setNumStars(0);
    }
}
