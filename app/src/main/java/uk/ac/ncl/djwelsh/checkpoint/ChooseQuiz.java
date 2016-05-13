package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Activity to select quiz type.
 */
public class ChooseQuiz extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Quiz quiz = null;
    private Subject subject = null;
    private Deck deck = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Select Quiz Type");

        // Get subject.
        Intent intent = getIntent();
        subject = intent.getParcelableExtra("subject");

        // Create a quiz using the subject to get all cards.
        CardsDataSource cardsDB = new CardsDataSource(this);
        cardsDB.open();
        deck = new Deck(cardsDB.getCardBySubject(String.valueOf(subject.getId())));
        quiz = new Quiz(subject, deck);
        cardsDB.close();
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
        getMenuInflater().inflate(R.menu.choose_quiz, menu);
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
                Intent a = new Intent(ChooseQuiz.this, MainActivity.class);
                startActivity(a);
                break;
            case R.id.nav_subjects :
                Intent b = new Intent(ChooseQuiz.this, SubjectList.class);
                startActivity(b);
                break;
            case R.id.nav_results :
                Intent c = new Intent(ChooseQuiz.this, SubjectResults.class);
                startActivity(c);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Sort cards by easiest to hardest and set quiz type.
     *
     * @param view
     */
    public void startEasiestToHardest(View view) {
        Collections.sort(deck.cards, new Comparator<Card>() {

            @Override
            public int compare(Card lhs, Card rhs) {
                if (Float.valueOf(lhs.getRating()) < Float.valueOf(rhs.getRating())) {
                    return -1;
                } else if (Float.valueOf(lhs.getRating()) > Float.valueOf(rhs.getRating())) {
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

    /**
     * Set quiz type to quickfire and launch quiz.
     *
     * @param view
     */
    public void startQuickFire(View view) {

        quiz.setQuizType("quickFire");
        quiz.setCorrectCount(0);
        quiz.setIncorrectCount(0);

        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quiz", quiz);

        startActivity(intent);
    }

    /**
     * Randomise quiz
     *
     * TODO ADD RANDOM
     *
     * @param view
     */
    public void startRandom(View view) {

        quiz.setQuizType("random");
        quiz.setCorrectCount(0);
        quiz.setIncorrectCount(0);

        quiz.getDeck().randomiseDeck();

        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
    }
}
