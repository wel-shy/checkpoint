package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class QuizResults extends AppCompatActivity {

    private String[] mListValues = {"Home", "Subjects", "Results"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private ListView subList;
    private ArrayAdapter<Subject> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Enable back and change icon on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + getSupportActionBar().getTitle().toString() + "</font>"));

        // Init drawer
        mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mListValues));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent a = new Intent(QuizResults.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case 1:
                        Intent b = new Intent(QuizResults.this, SubjectSelect.class);
                        startActivity(b);
                        break;
                    case 2:
                        Intent c = new Intent(QuizResults.this, QuizResults.class);
                        startActivity(c);
                        break;
                }
            }
        });
        setupDrawer();

        final SubjectsDataSource subjectDB = new SubjectsDataSource(this);
        subjectDB.open();
        List<Subject> values = subjectDB.getAllSubjects();
        subjectDB.close();

        adapter = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, values);
        ListView listView = (ListView) findViewById(R.id.results_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                subjectDB.open();
                Subject subject = subjectDB.getSubject(id);
                subjectDB.close();
                Intent intent = new Intent(QuizResults.this, SubjectOverview.class);
                intent.putExtra("subject", subject);
                startActivity(intent);
            }
        });
    }

    /**
     * Launch add a new subject activity.
     * @param view
     */
    public void launchAddSubject(View view)
    {
        Intent intent = new Intent(this, AddSubject.class);
        startActivity(intent);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
//                Button button = (Button) findViewById(R.id.add_subject);
//                button.setVisibility(View.INVISIBLE);
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + "Navigation" + "</font>"));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
//                Button button = (Button) findViewById(R.id.add_subject);
//                button.setVisibility(View.VISIBLE);
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + mActivityTitle + "</font>"));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
