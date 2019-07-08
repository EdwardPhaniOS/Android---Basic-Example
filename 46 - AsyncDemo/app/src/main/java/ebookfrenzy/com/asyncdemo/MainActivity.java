package ebookfrenzy.com.asyncdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView = (TextView) findViewById(R.id.myTextView);
    }

    public void buttonClick(View view) {
        AsyncTask task = new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //task.execute();
        //AsyncTask task = new MyTask().executeOnExecutor();
        //task.cancel(true);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

        public MyTask() {
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            int i = 0;
            while (i <= 10) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                    i++;
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }

            return "Button Pressed!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int cpuCore = Runtime.getRuntime().availableProcessors();
            myTextView.setText("Counter = " + values[0] + "====CPU CORES: " + cpuCore);
        }

        @Override
        protected void onPostExecute(String s) {
            myTextView.setText(s);
        }

    }
}
