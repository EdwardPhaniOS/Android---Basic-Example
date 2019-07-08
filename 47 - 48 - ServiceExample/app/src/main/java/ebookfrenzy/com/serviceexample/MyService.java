package ebookfrenzy.com.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    private static final String TAG = "ServiceExample";

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AsyncTask task = new SrvTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, startId);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
    }

    private class SrvTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            int startId = integers[0];
            int i = 0;
            while (i <= 3) {
                publishProgress(integers[0]);
                try {
                    Thread.sleep(10000);
                    i++;
                } catch (Exception e) {

                }
            }
            return ("Service complete" + startId);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, s);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Service Running" + values[0]);
        }
    }
}
