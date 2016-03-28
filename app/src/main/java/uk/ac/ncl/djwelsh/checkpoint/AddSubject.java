package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class AddSubject extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "uk.ac.ncl.djwelsh.checkpoint.MESSAGE";

    private SubjectsDataSource datasource;
    private ArrayAdapter<Subject> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        datasource = new SubjectsDataSource(this);
        datasource.open();
    }

    public void saveSubject(View view)
    {
        EditText subjectName = (EditText) findViewById(R.id.add_subject_name);
        String name = subjectName.getText().toString();

        Subject sub = datasource.createSubject(name);

        Intent intent = new Intent(this, AddCard.class);
        intent.putExtra("subject", sub);
        startActivity(intent);
    }

    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    protected void onPause()
    {
        datasource.close();
        super.onPause();
    }
}
