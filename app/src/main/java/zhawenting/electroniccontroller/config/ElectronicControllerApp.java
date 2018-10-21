package zhawenting.electroniccontroller.config;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import zhawenting.electroniccontroller.util.CrashHandler;

/**
 * 类描述：全局控制类
 * 创建人：Administrator
 * 创建时间：2017/9/25 0025
 */

public class ElectronicControllerApp extends Application {
    private final String TAG = this.getClass().toString();
    public Uri mImage;
    //单例，只生成一个app对象
    public static ElectronicControllerApp instance;

    public static ElectronicControllerApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mImage = null;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(), this);

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        System.exit(0);
    }

}
