package com.yuaihen.wcdxg.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import androidx.annotation.RawRes;

import com.yuaihen.wcdxg.base.BaseApplication;

import java.io.IOException;

public class SoundUtil {

    private static final String TAG = "SoundUtil";
    private static MediaPlayer mediaPlayer;

    public interface OnFinishMediaPlayerListener {
        void onFinish();
    }

    public static void playUrl(String url, OnFinishMediaPlayerListener listener) {
        stopMediaPlayer();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            //            AssetFileDescriptor fd = BaseApplication.Companion.getContext().getAssets().openFd("a.mp3");
            //            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            //设置音频流的类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                stopMediaPlayer();
                if (listener != null) {
                    listener.onFinish();
                }
            });
        } catch (IllegalArgumentException | IOException | IllegalStateException | SecurityException e) {
            e.printStackTrace();
        }
    }


    public static void playRaw(@RawRes int resId) {
        stopMediaPlayer();
        mediaPlayer = MediaPlayer.create(BaseApplication.Companion.getContext(), resId);
        try {
            //设置音频流的类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
            //            mediaPlayer.prepareAsync();
            //            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                stopMediaPlayer();

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMediaPlayer() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            Log.d(TAG, "stopMediaPlayer: ");
        }

    }

}
