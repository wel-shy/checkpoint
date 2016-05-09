package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class SubjectSelect extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private SubjectsDataSource datasource;
    private ArrayAdapter<Subject> adapter;

    private String[] mListValues = {"Home", "Subjects", "Results", "View Results"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        // Enable back and change icon on action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + getSupportActionBar().getTitle().toString() + "</font>"));
//
//        // Init drawer
//        mActivityTitle = getTitle().toString();
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//
//        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mListValues));
//        // Set the list's click listener
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        Intent a = new Intent(SubjectSelect.this, MainActivity.class);
//                        startActivity(a);
//                        break;
//                    case 1:
//                        Intent b = new Intent(SubjectSelect.this, SubjectSelect.class);
//                        startActivity(b);
//                        break;
//                    case 2:
//                        Intent c = new Intent(SubjectSelect.this, QuizResults.class);
//                        startActivity(c);
//                        break;
//                    case 3:
//                        Intent d = new Intent(SubjectSelect.this, ViewQuizResults.class);
//                        startActivity(d);
//                }
//            }
//        });
//
//        setupDrawer();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                Intent intent = new Intent(SubjectSelect.this, ViewSubject.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
    }

//    // Menu icons are inflated just as they were with actionbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    /**
     * Launch add a new subject activity.
     * @param view
     */
    public void launchAddSubject(View view)
    {
        Intent intent = new Intent(this, NewSubject.class);
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
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + "Navigation" + "</font>"));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                Button button = (Button) findViewById(R.id.add_subject);
                button.setVisibility(View.VISIBLE);
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + mActivityTitle + "</font>"));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        // Activate the navigation drawer toggle
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
        getMenuInflater().inflate(R.menu.view_quiz_results, menu);
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_subjects) {

        } else if (id == R.id.nav_results) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
