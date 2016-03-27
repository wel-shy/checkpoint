package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubjectOverview extends AppCompatActivity {

    private SubjectsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String rawId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        long id = Long.parseLong(rawId);
        id += 1;

        System.out.println(id);

        datasource = new SubjectsDataSource(this);
        datasource.open();

        Subject subject = datasource.getSubject(id);

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(subject.getName());

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(textView);
    }
}
