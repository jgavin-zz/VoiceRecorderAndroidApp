package com.example.jgavin.voicerecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.IOException;
public class VoiceRecorder extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;;

    private MediaRecorder mRecorder = null;

    private MediaPlayer   mPlayer = null;

    private void onRecord() {
        boolean start;
        Button myButton = (Button) findViewById(R.id.startRecordingButton);
        String buttonString = myButton.getText().toString();
        if(buttonString.equals("Start Recording")){
            start = true;
        }
        else{
            start = false;
        }

        if (start) {
            try {
                myButton.setText("Stop Recording");
                startRecording();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            myButton.setText("Start Recording");
            Button playButton = (Button) findViewById(R.id.startPlayingButton);
            playButton.setEnabled(true);
            stopRecording();
        }
    }

    private void onPlay() {
        boolean start;
        Button myButton = (Button) findViewById(R.id.startPlayingButton);
        String buttonString = myButton.getText().toString();
        if(buttonString.equals("Start Playing")){
            start = true;
        }
        else{
            start = false;
        }
        if (start) {
            myButton.setText("Stop Playing");
            startPlaying();
        } else {
            myButton.setText("Start Playing");
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() throws IOException {
        mRecorder = new MediaRecorder();
        AudioRecordTest();
        //mFileName = Environment.getExternalStorageDirectory().getAbsolutePath().substring(8) + "/audio/" +  System.currentTimeMillis()+ ".3gp";
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_voice_recorder);
        Button myRecordButton = (Button) findViewById(R.id.startRecordingButton);
        myRecordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord();
            }
        });
        Button myPlayButton = (Button) findViewById(R.id.startPlayingButton);
        myPlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPlay();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
