package uk.ac.ncl.djwelsh.checkpoint;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class SubjectSelect extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "uk.ac.ncl.djwelsh.checkpoint.MESSAGE";

    private SubjectsDataSource datasource;
    private ArrayAdapter<Subject> adapter;

    private String[] mListValues = {"Home", "Subjects", "Results"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        toolbar.setTitle(null);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mListValues));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SubjectSelect.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        Intent a = new Intent(SubjectSelect.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case 1:
                        Intent b = new Intent(SubjectSelect.this, SubjectSelect.class);
                        startActivity(b);
                        break;
                    case 2:
                        Intent c = new Intent(SubjectSelect.this, QuizResults.class);
                        startActivity(c);
                        break;
                }
            }
        });

        setupDrawer();

        datasource = new SubjectsDataSource(this);
        datasource.open();

        List<Subject> values = datasource.getAllSubjects();

        adapter = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, values);
        ListView listView = (ListView) findViewById(R.id.subject_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = String.valueOf(id);
                System.out.println("MESSAGE TO ADD CARD:________________" + message + " " + message.getClass());
                Intent intent = new Intent(SubjectSelect.this, SubjectOverview.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
                Button button = (Button) findViewById(R.id.add_subject);
                button.setVisibility(View.INVISIBLE);
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                Button button = (Button) findViewById(R.id.add_subject);
                button.setVisibility(View.VISIBLE);
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
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
