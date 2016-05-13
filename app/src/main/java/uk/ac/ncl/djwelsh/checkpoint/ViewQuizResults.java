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
import android.widget.Spinner;
import android.widget.TextView;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Activity to view all quiz results.
 */
public class ViewQuizResults extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner subjectsList;
    List<Subject> subjects;
    XYSeries previousSeries;
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        subject = (Subject) getIntent().getParcelableExtra("subject");

        // Get all subjects
//        SubjectsDataSource subjectsDB = new SubjectsDataSource(this);
//        subjectsDB.open();
//        subjects = subjectsDB.getAllSubjects();
//        subjectsDB.close();
//
//        subjects.add(0, new Subject(0, "Select A Subject"));
//
//        // Map to spinner
//        subjectsList = (Spinner) findViewById(R.id.subject_select);
//        ArrayAdapter<Subject> subjectArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
//        subjectArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        subjectsList.setAdapter(subjectArrayAdapter);
//
//        subjectsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                // Set spinner white
//                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
//
//                // Get all quizzes
//                Subject sub = (Subject) parent.getItemAtPosition(position);
//                QuizDataSource quizDB = new QuizDataSource(ViewQuizResults.this);
//                quizDB.open();
//                List<Quiz> quizzes = quizDB.getQuizBySubject(String.valueOf(sub.getId()));
//                quizDB.close();
//
//                // set graph
//                TextView totalPointsView = (TextView) findViewById(R.id.total_points);
//                XYPlot plot = (XYPlot) findViewById(R.id.plot);
//
//                plot.removeSeries(previousSeries);
//
//                // Plot graph
//                ArrayList<Integer> scores = new ArrayList<Integer>();
//                int totalSubjectPoints = 0;
//                int i = 0;
//                while (i < quizzes.size()) {
//                    totalSubjectPoints += quizzes.get(i).getPoints();
//                    scores.add(quizzes.get(i).getPoints());
//                    i++;
//                }
//                int totalQuizzes = i;
//
//                // Set total points
//                if (sub.getId() != 0) {
//                    TextView subHeader = (TextView) findViewById(R.id.sub_select_title);
//                    subHeader.setText("Total " + sub.getName() + " points");
//                    totalPointsView.setText(String.valueOf(totalSubjectPoints));
//                } else {
//                    TextView subHeader = (TextView) findViewById(R.id.sub_select_title);
//                    subHeader.setText("");
//                    totalPointsView.setText(String.valueOf(""));
//                }
//
//                // create a couple arrays of y-values to plot:
//                Integer[] scoresArr = new Integer[scores.size()];
//                for(int j = 0; j < scores.size(); j++) {
//                    scoresArr[j] = scores.get(j);
//                    System.out.println(scoresArr[j]);
//                }
//
//                // Reduce to five values
//                Integer[] plotData;
//                if(scoresArr.length > 5 ){
//
//                    ArrayList<Integer> temp = new ArrayList<Integer>();
//                    for (int j = scoresArr.length - 1; j > scoresArr.length - 6; j--) {
//
//                    }
//                    plotData = temp.toArray(new Integer[temp.size()]);
//                    System.out.println("REDUCED");
//                } else {
//                    plotData = scoresArr;
//                }
//
//                // turn the above arrays into XYSeries':
//                // (Y_VALS_ONLY means use the element index as the x value)
//                XYSeries series1 = new SimpleXYSeries(Arrays.asList(plotData),
//                        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
//
//                // Plot with formatters
//                MyLineAndPointFormatter format = new MyLineAndPointFormatter();
//                format.setInterpolationParams(new CatmullRomInterpolator.Params(20, CatmullRomInterpolator.Type.Centripetal));
//                plot.addSeries(series1, format);
//
//                previousSeries = series1;
//                plot.redraw();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        getSupportActionBar().setTitle("Results");

        // Get all quizzes
        QuizDataSource quizDB = new QuizDataSource(ViewQuizResults.this);
        quizDB.open();
//        List<Quiz> quizzes = quizDB.getAllQuizzes();
        List<Quiz> quizzes = quizDB.getQuizBySubject(String.valueOf(subject.getId()));

        quizDB.close();

        // Sort by last played
        Collections.sort(quizzes, new Comparator<Quiz>() {
            @Override
            public int compare(Quiz lhs, Quiz rhs) {
                Date datel = new Date(lhs.getDate());
                Date dater = new Date(rhs.getDate());
                return datel.compareTo(dater);
            }
        });

        // Add to data set
        List<Quiz> quizResults = new ArrayList<Quiz>();
        if (quizzes.size() > 5) {
            for (int i = quizzes.size() - 1; i > quizzes.size() - 6; i--) {
                System.out.println(quizzes.get(i));
                quizResults.add(quizzes.get(i));
            }
        } else {
            quizResults = quizzes;
        }

        // Plot results
        TextView totalPointsView = (TextView) findViewById(R.id.total_points);
        XYPlot plot = (XYPlot) findViewById(R.id.plot);
        ViewQuizResults.drawPlot(quizResults, totalPointsView, plot);
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

        switch (id) {
            case R.id.nav_home :
                Intent a = new Intent(ViewQuizResults.this, MainActivity.class);
                startActivity(a);
                break;
            case R.id.nav_subjects :
                Intent b = new Intent(ViewQuizResults.this, SubjectList.class);
                startActivity(b);
                break;
            case R.id.nav_results :
                Intent c = new Intent(ViewQuizResults.this, SubjectResults.class);
                startActivity(c);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Draw a graph of previous quiz scores
     *
     * @param quizzes
     * @param totalPointsView
     * @param plot
     * @return
     */
    public static XYSeries drawPlot(List<Quiz> quizzes, TextView totalPointsView, XYPlot plot){
        int totalQuizzes;
        int totalSubjectPoints;
        String lastPlayed;

        // Plot graph
        ArrayList<Integer> scores = new ArrayList<Integer>();
        totalSubjectPoints = 0;
        int i = 0;
        while (i < quizzes.size()) {
            totalSubjectPoints += quizzes.get(i).getPoints();
            lastPlayed = quizzes.get(i).getDate();
            scores.add(quizzes.get(i).getPoints());
            i++;
        }
        totalQuizzes = i;

        // Set total points
        totalPointsView.setText(String.valueOf(totalSubjectPoints));

        // create a couple arrays of y-values to plot:
        Integer[] scoresArr = new Integer[scores.size()];
        for(int j = 0; j < scores.size(); j++) {
            scoresArr[j] = scores.get(j);
        }

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(scoresArr),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");

        MyLineAndPointFormatter format = new MyLineAndPointFormatter();
        format.setInterpolationParams(new CatmullRomInterpolator.Params(20, CatmullRomInterpolator.Type.Centripetal));
        plot.addSeries(series1, format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.setTicksPerDomainLabel(1);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        plot.getGraphWidget().setDomainLabelOrientation(-45);

        plot.getGraphWidget().setPaddingLeft(10);

        return series1;
    }
}
