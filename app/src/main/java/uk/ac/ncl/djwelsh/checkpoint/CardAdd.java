package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * An activity to add a card to a subject.
 */
public class CardAdd extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Subject subject;
    int cardCount = 1;
    private CardsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Set navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get subject
        Intent intent = getIntent();
        subject = intent.getParcelableExtra("subject");
        datasource = new CardsDataSource(this);

        // Change title
        getSupportActionBar().setTitle("Add Cards to " + subject.getName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home :
                Intent a = new Intent(CardAdd.this, MainActivity.class);
                startActivity(a);
                break;
            case R.id.nav_subjects :
                Intent b = new Intent(CardAdd.this, SubjectList.class);
                startActivity(b);
                break;
            case R.id.nav_results :
                Intent c = new Intent(CardAdd.this, ViewQuizResults.class);
                startActivity(c);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Save a card to the database.
     *
     * @param view This view.
     */
    public void saveCard(View view) {

        // Get data
        EditText rawCardName = (EditText) findViewById(R.id.card_title);
        EditText rawQuestion = (EditText) findViewById(R.id.card_overview_question);
        EditText rawAnswer = (EditText) findViewById(R.id.card_question);
        RatingBar rawDifficulty = (RatingBar) findViewById(R.id.card_difficulty);

        // Parse to array
        String[] data = {
                rawCardName.getText().toString(),               // Name
                rawQuestion.getText().toString(),               // Question
                rawAnswer.getText().toString(),                 // Answer
                "0",                                            // Correct Count
                "0",                                            // Incorrect Count
                String.valueOf(subject.getId()),                // Subject
                Float.toString(rawDifficulty.getRating())       // Difficulty
        };

        // Write to database
        datasource.open();
        datasource.createCard(data);
        datasource.close();
        cardCount++;

        // Reset fields to null.
        rawCardName.setText("");
        rawQuestion.setText("");
        rawAnswer.setText("");
        rawDifficulty.setNumStars(0);

        // Update counter
        TextView counter = (TextView) findViewById(R.id.card_counters);
        counter.setText("Card " + cardCount);
    }

    /**
     * Finish adding cards and return to subject overview.
     *
     * @param view This view.
     */
    public void finishDeck(View view) {

        Intent intent = new Intent(this, ViewSubject.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}
