package com.example.hanna.musicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Arek on 2016-12-03.
 */

public class
SongPlayer extends MediaPlayer {
    private String songPath;
    private MainActivity mainActivity;
    private String durationTimeCorrectFormat;
    private Handler handler;
    private Runnable runnable;
    private String path;
    private boolean isSetTimer = false;
    private enum PlayingMode { LOOP, PLAYLIST, RANDOM };

    public SongPlayer(MainActivity m)
    {
        this.mainActivity = m;
    }

    public void setSong(String path)
    {
        if (path.length() <= 0)
            return;

        if (isPlaying()){
            this.stop();
        }
        this.reset();
        this.path = path;

        try {
            setDataSource(path);
            prepare();
            start();
            setButtonsToPlayMode();
            setButtonsNextAndPrev();

            final SongPlayer thisSong = this;
            ProgressBar progressBar = (ProgressBar)mainActivity.findViewById(R.id.progressBarMusic);
            progressBar.setMax(thisSong.getDuration());

            durationTimeCorrectFormat = getTime(this.getDuration());
            writeValues();
            setTimer();

        }
        catch (IOException e){
            Toast.makeText(mainActivity, "Wystąpił problem z playerem", Toast.LENGTH_LONG).show();
        }

    }

    public void destroyCallback(){
        handler.removeCallbacks(runnable);
    }

    public void MoveTo(double percentageValue){
        seekTo((int)Math.round(percentageValue*getDuration()));
        if (!isSetTimer){
            setTimer();
        }
        writeValues();
    }

    public void setTimer(){
        final SongPlayer thisSong = this;
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                isSetTimer = false;

                if (thisSong.getCurrentPosition() < thisSong.getDuration()) {
                    handler.postDelayed(this, 1000);
                    isSetTimer = true;
                }
                else{
                    setButtonsToStopMode();
                    nextSong();
                }

                if (thisSong.isPlaying()) {
                    writeValues();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private String getTime(long miliseconds){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseconds);

        return dateFormat.format(calendar.getTime());
    }

    private void writeValues(){
        ProgressBar progressBar = (ProgressBar)mainActivity.findViewById(R.id.progressBarMusic);
        progressBar.setProgress(Math.round(this.getCurrentPosition()));
        ((TextView)mainActivity.findViewById(R.id.textviewDurationTime)).setText(getTime(this.getCurrentPosition()) + "/" + durationTimeCorrectFormat);
    }

    public void nextSong(){
        if (getPlayingMode() == PlayingMode.PLAYLIST) {
            setSong(mainActivity.getNextSongPath());
        }
        else if (getPlayingMode() == PlayingMode.LOOP) {
            setSong(path);
        }
        else {
            setSong(mainActivity.getRandomSongPath());
        }

    }

    public void prevSong(){
        setSong(mainActivity.getPrevSongPath());
    }

    private PlayingMode getPlayingMode(){
        if (((ToggleButton)mainActivity.findViewById(R.id.btn_loop)).isChecked()){
            return PlayingMode.LOOP;
        }
        else if (((ToggleButton)mainActivity.findViewById(R.id.btn_playlist)).isChecked()){
            return PlayingMode.PLAYLIST;
        }
        else {
            return PlayingMode.RANDOM;
        }
    }

    private void setButtonsToPlayMode(){
        mainActivity.findViewById(R.id.buttonPlay).setEnabled(false);
        mainActivity.findViewById(R.id.buttonPause).setEnabled(true);
        mainActivity.findViewById(R.id.buttonStop).setEnabled(true);
    }

    private void setButtonsToPauseMode(){
        mainActivity.findViewById(R.id.buttonPlay).setEnabled(true);
        mainActivity.findViewById(R.id.buttonPause).setEnabled(false);
        mainActivity.findViewById(R.id.buttonStop).setEnabled(true);
    }

    private void setButtonsToStopMode(){
        if (path!=null && this.getDuration()>0) {
            mainActivity.findViewById(R.id.buttonPlay).setEnabled(true);
        }
        else{
            mainActivity.findViewById(R.id.buttonPlay).setEnabled(false);
        }
        mainActivity.findViewById(R.id.buttonPause).setEnabled(false);
        mainActivity.findViewById(R.id.buttonStop).setEnabled(false);
    }

    public void setButtonsNextAndPrev(){
        boolean prevEnable, nextEnable;

        if (getPlayingMode() != PlayingMode.RANDOM) {
            int currID = mainActivity.getCurrentSongId();
            int maxID = mainActivity.getCountSongs();

            prevEnable = (currID == 0) ? false : true;
            nextEnable = (currID + 1 != maxID) ? true : false;
        }
        else{
            prevEnable = false;
            nextEnable = true;
        }

        mainActivity.findViewById(R.id.buttonPrevious).setEnabled(prevEnable);
        mainActivity.findViewById(R.id.buttonNext).setEnabled(nextEnable);
    }

    @Override
    public void start(){
        if (!isPlaying()) {
            super.start();
            if (path != null) {
                setButtonsToPlayMode();
            }
        }
    }

    @Override
    public void pause(){
        super.pause();
        setButtonsToPauseMode();
    }

    @Override
    public void stop(){
        if (this.isPlaying()) {
            //super.stop();
            pause();
            this.seekTo(0);
            writeValues();
        }
        else if (getCurrentPosition() > 0){
            this.seekTo(0);
            writeValues();
        }
        setButtonsToStopMode();
    }
}
