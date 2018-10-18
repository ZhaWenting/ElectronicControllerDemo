package zhawenting.electroniccontroller.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import zhawenting.electroniccontroller.BuildConfig;
import zhawenting.electroniccontroller.config.Constants;

/**
 * Created by Administrator on 2016/9/27.
 */
public class LogUtil {

    static boolean isSaveFile = false;

    public static void i(Object msg) {
        if (msg == null) {
            LogUtil.i("null");
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.i("ElectronicController:", msg.toString());
            if (isSaveFile) {
                FileUtils.writeString(Constants.LOG_PATH, msg.toString());
            }

        }
    }

    public static void d(Object msg) {
        if (msg == null) {
            LogUtil.d("null");
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.d("ElectronicController:", msg.toString());
            if (isSaveFile) {
                FileUtils.writeString(Constants.LOG_PATH, msg.toString());
            }

        }
    }


    public static void e(Object msg) {
        if (msg == null) {
            LogUtil.e("null");
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.e("TakeCard:", msg.toString());
            if (isSaveFile) {
                FileUtils.writeString(Constants.LOG_PATH, msg.toString());
            }

        }
    }

    public static void e(Object msg, boolean isSaveFile) {
        if (msg == null) {
            LogUtil.e("null");
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        if (isSaveFile) {
            FileUtils.writeString(Constants.LOG_PATH + df.format(new Date()), msg.toString());
        }

    }
}
