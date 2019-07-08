package com.example.applinkinglandmark.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;

import com.ebookfrenzy.applinking.AppLinkingActivity;
import com.ebookfrenzy.applinking.Landmark;
import com.ebookfrenzy.applinking.MyDBHandler;


public class LandmarkActivity extends AppCompatActivity {

    private static final String TAG = "Database";
    private TextView titleText;
    private TextView descriptionText;
    private Button deleteButton;
    private Landmark landmark = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ebookfrenzy.applinking.R.layout.activity_landmark);

        titleText = (TextView) findViewById(com.ebookfrenzy.applinking.R.id.titleText);
        descriptionText = (TextView) findViewById(com.ebookfrenzy.applinking.R.id.descriptionText);
        deleteButton = (Button) findViewById(com.ebookfrenzy.applinking.R.id.deleteButton);


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if (appLinkAction != null) {

            if (appLinkAction.equals("android.intent.action.VIEW")) {

                String landmarkId = appLinkData.getLastPathSegment();

                if (landmarkId != null) {
                    displayLandmark(landmarkId);
                }
            }
        } else {
            handleIntent(appLinkIntent);
        }


    }

    private void handleIntent(Intent intent) {

        String landmarkId = intent.getStringExtra(AppLinkingActivity.LANDMARK_ID);
        displayLandmark(landmarkId);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }




    public void deleteLandmark(View view) {

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        if (landmark != null) {
            dbHandler.deleteLandmark(landmark.getID());
            titleText.setText("");
            descriptionText.setText("");
            deleteButton.setEnabled(false);
        }
    }

    private void displayLandmark(String landmarkId) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        landmark =
                dbHandler.findLandmark(landmarkId);

        if (landmark != null) {

            if (landmark.getPersonal() == 0) {
                deleteButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
            }

            titleText.setText(landmark.getTitle());
            descriptionText.setText(landmark.getDescription());
        } else {
            titleText.setText("No Match Found");
        }
    }


}
