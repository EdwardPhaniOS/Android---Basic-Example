package ebookfrenzy.com.videoplayer;

import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Rational;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.util.Log;
import android.media.MediaPlayer;
import android.view.View;
import android.app.PendingIntent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_CODE = 101;
    private VideoView videoView;
    private MediaController mediaController;
    private BroadcastReceiver receiver;
    String TAG = "VideoPlayer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureVideoView();
    }

    private void configureVideoView()
    {
        videoView = (VideoView)findViewById(R.id.vdVw);
        //Offline video
//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
//        videoView.setVideoURI(uri);

        //Online video
        Uri uri = Uri.parse("https://www.ebookfrenzy.com/android_book/movie.mp4");
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//                Log.i(TAG, "Duration = " + videoView.getDuration());
//            }
//        });

        videoView.start();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        Button pipButton = (Button) findViewById(R.id.pipButton);

        if (isInPictureInPictureMode) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.ebookfrenzy.videoplayer.VIDEO_INFO");

            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(context, "My Sample Video",
                            Toast.LENGTH_LONG).show();
                }
            };

            registerReceiver(receiver, filter);
            createPipAction();

        } else {
            pipButton.setVisibility(View.VISIBLE);
            videoView.setMediaController(mediaController);

            if (receiver != null) {
                unregisterReceiver(receiver);
            }
        }
    }

    public void enterPipMode(View view)
    {
        Button pipButton = (Button) findViewById(R.id.pipButton);

        Rational rational = new Rational(videoView.getWidth(), videoView.getHeight());

        PictureInPictureParams params = new PictureInPictureParams.Builder()
                .setAspectRatio(rational)
                .build();

        pipButton.setVisibility(View.INVISIBLE);
        videoView.setMediaController(null);
        enterPictureInPictureMode(params);
    }

    private void createPipAction() {

        final ArrayList<RemoteAction> actions = new ArrayList<>();

        Intent actionIntent = new Intent("com.ebookfrenzy.videoplayer.VIDEO_INFO");
        final PendingIntent pendingIntent = PendingIntent
                .getBroadcast(MainActivity.this,
                        REQUEST_CODE, actionIntent, 0);

        final Icon icon = Icon.createWithResource(MainActivity.this,
                R.drawable.ic_info_24dp);
        RemoteAction remoteAction = new RemoteAction(icon, "Info",
                "Video Info", pendingIntent);

        actions.add(remoteAction);

        PictureInPictureParams params = new PictureInPictureParams.Builder()
                .setActions(actions).build();

        setPictureInPictureParams(params);
    }
}
