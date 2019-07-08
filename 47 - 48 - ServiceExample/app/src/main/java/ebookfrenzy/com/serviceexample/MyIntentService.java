package ebookfrenzy.com.serviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import org.jetbrains.annotations.Nullable;

public class MyIntentService extends IntentService
{
    private static final String TAG = "ServiceExample";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Intent Service started");
    }
}
