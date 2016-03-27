package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SubjectSelect extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "uk.ac.ncl.djwelsh.checkpoint.MESSAGE";

    private SubjectsDataSource datasource;
    private ArrayAdapter<Subject> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);

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

    /**
     * Launch add a new subject activity.
     * @param view
     */
    public void launchAddSubject(View view)
    {
        Intent intent = new Intent(this, AddSubject.class);
        startActivity(intent);
    }
}
