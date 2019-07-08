package com.example.eventexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = (Button) findViewById(R.id.myButton);
        final TextView statusText = (TextView) findViewById(R.id.statusText);

        myButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusText.setText("Button Clicked");
            }
        });

        myButton.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                statusText.setText("Long button clicked");
                return true;
            }
        });
    }
}
