package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "uk.ac.ncl.djwelsh.checkpoint.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get text display
        TextView mTxtDisplay;
        mTxtDisplay = (TextView) findViewById(R.id.req_container);

        String urlString = "http://www.google.co.uk";

        // Check if application has internet connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mTxtDisplay.setText("Has connectivity");

            // Connect to google.
//            new ConnectGoogle().execute(urlString);

        } else {
            mTxtDisplay.setText("Has no connectivity");
        }
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

    /**
     * Asynchronous task to handle downloading google front page from URL.
     */
    private class ConnectGoogle extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String data = null;

            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream stream = conn.getInputStream();

                data = convertStreamToString(stream);
                stream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView mTxtDisplay;
            mTxtDisplay = (TextView) findViewById(R.id.req_container);
            mTxtDisplay.setText(result);
        }
    }

    /**
     * Convert stream to string --Stack overflow.
     * @param is
     * @return
     */
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
