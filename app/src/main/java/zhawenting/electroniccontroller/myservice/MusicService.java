package zhawenting.electroniccontroller.myservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import zhawenting.electroniccontroller.config.Constants;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package alphabet.musicplayerdemo
 * @date 2018/10/18 22:52
 */
public class MusicService extends Service {
    public final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public MusicService() {
        initMediaPlayer();

    }
    public void initMediaPlayer() {
        try {
            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/secret_garden.mp3";
            mediaPlayer.setDataSource(file_path);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void play() {
        mediaPlayer.start();
    }
    public void stop() {
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}