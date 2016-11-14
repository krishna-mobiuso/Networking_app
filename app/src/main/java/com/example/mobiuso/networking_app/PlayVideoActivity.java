package com.example.mobiuso.networking_app;

/**
 * Created by mobiuso on 24/10/16.
 */

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.VideoView;
import android.widget.MediaController;
import android.util.Log;
import android.media.MediaPlayer;

public class PlayVideoActivity extends Activity {

    String TAG = "com.example.VideoPlayer";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final VideoView videoView =
                (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath(Constants.VIDEO_URL);

        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(new
                                                MediaPlayer.OnPreparedListener()  {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {Log.i(TAG,"Duration = " +
                                                                videoView.getDuration());
                                                    }
                                                });

        videoView.start();

    }
}
