package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SubjectOverview extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "uk.ac.ncl.djwelsh.checkpoint.MESSAGE";

    private SubjectsDataSource subjectDB;
    private CardsDataSource cardsDB;
    private ArrayAdapter<Card> adapter;
    Subject subject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_overview);

        Intent intent = getIntent();
        String rawId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        long id = Long.parseLong(rawId);
        id += 1;

        System.out.println(id);

        subjectDB = new SubjectsDataSource(this);
        subjectDB.open();


        subject = subjectDB.getSubject(id);
        subjectDB.close();

        TextView textView = (TextView) findViewById(R.id.overview_title);
        textView.setText(subject.getName());

        cardsDB = new CardsDataSource(this);
        cardsDB.open();

        List<Card> values = cardsDB.getCardBySubject(Long.toString(subject.getId()));

        adapter = new ArrayAdapter<Card>(this, android.R.layout.simple_list_item_1, values);
        ListView listView = (ListView) findViewById(R.id.card_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubjectOverview.this, CardOverview.class);

                Card card = cardsDB.getCard(id);

                System.out.println(card.getQuestion());

                intent.putExtra("card", card);
                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);
    }

    public void addNewCard(View view) {

        Intent intent = new Intent(this, AddCard.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }

    public void selectQuizType (View view) {

        Intent intent = new Intent(this, StartQuiz.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}
