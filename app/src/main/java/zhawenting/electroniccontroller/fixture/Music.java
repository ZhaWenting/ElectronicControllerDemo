package zhawenting.electroniccontroller.fixture;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import zhawenting.electroniccontroller.myservice.MusicService;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package zhawenting.electroniccontroller.fixture
 * @date 2018/10/20 21:50
 */
public class Music {
    private MusicService musicService;
    Context context;

    public Music(Context context) {
        this.context = context;
        //prepare music
        bindServiceConnection();
        musicService = new MusicService();
    }

    public void musicSwitch(boolean state){
        if(state)
            musicService.stop();
        else
            musicService.play();
    }


    private void bindServiceConnection() {
        Intent intent = new Intent(context, MusicService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };
}
