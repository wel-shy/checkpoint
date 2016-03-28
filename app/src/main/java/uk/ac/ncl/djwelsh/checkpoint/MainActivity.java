package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "uk.ac.ncl.djwelsh.checkpoint.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user clicks send button
     *
     * @param view
     */
    public void launchApp(View view) {

        Intent intent = new Intent(this, SubjectSelect.class);
        startActivity(intent);
        finish();
    }
}
