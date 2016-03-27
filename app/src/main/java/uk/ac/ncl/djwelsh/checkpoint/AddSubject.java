package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddSubject extends AppCompatActivity {
    private SubjectsDataSource sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sdb = new SubjectsDataSource(this);
        sdb.open();
    }

    public void saveSubject(View view)
    {
        EditText subjectName = (EditText) findViewById(R.id.add_subject_name);
        String name = subjectName.getText().toString();

        Subject sub = sdb.createSubject(name);

        Intent intent = new Intent(this, SubjectSelect.class);
        startActivity(intent);
    }

    protected void onResume() {
        sdb.open();
        super.onResume();
    }

    protected void onPause()
    {
        sdb.close();
        super.onPause();
    }

}
