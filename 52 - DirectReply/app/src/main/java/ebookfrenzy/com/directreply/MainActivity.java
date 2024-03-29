package ebookfrenzy.com.directreply;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int notificationId = 101;
    private static String KEY_TEXT_REPLY = "key_text_reply";
    NotificationManager notificationManager;
    private String channelID = "directly.news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(channelID, "DirectReply News", "Example News Channel");

        handleIntent();
    }

    private  void handleIntent()
    {
        Intent intent = this.getIntent();

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {

            TextView myTextView = (TextView) findViewById(R.id.textView);
            String inputString = remoteInput.getCharSequence(KEY_TEXT_REPLY)
                    .toString();
            myTextView.setText(inputString);

            Notification repliedNotification = new Notification.Builder(this, channelID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentText("Reply received")
                    .build();

            notificationManager.notify(notificationId, repliedNotification);
        }


    }

    protected void createNotificationChannel(String id, String name, String description)
    {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[] {100, 200, 300, 400,
                500, 400, 300, 200, 400});

        notificationManager.createNotificationChannel(channel);
    }

    public void sendNotification(View view) {
        String replyLable = "Enter your reply here";
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLable)
                .build();

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        final Icon icon = Icon.createWithResource(MainActivity.this,
                android.R.drawable.ic_dialog_info);

        Notification.Action replyAction = new Notification.Action.Builder(icon,
                "Reply",
                resultPendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        Notification newMessageNotification = new Notification.Builder(this, channelID)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("My Notification")
                .setContentText("This is a test Message from Vinh")
                .addAction(replyAction)
                .build();

//        NotificationManager notificationManager =
//                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notificationId, newMessageNotification);

            notificationManager.notify(notificationId, newMessageNotification);

    }
}
