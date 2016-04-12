package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CardOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_overview);

        Intent intent = getIntent();
        Card card = (Card) intent.getParcelableExtra("card");

        TextView textView = (TextView) findViewById(R.id.card_overview_name);
        textView.setText(card.getName());

        textView = (TextView) findViewById(R.id.card_overview_question);
        textView.setText(card.getQuestion());

        textView = (TextView) findViewById(R.id.card_overview_answer);
        textView.setText(card.getAnswer());
    }
}
