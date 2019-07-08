package ebookfrenzy.com.multiwindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchIntent(View view) {
        Intent i = new Intent(this, SecondActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK|
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
