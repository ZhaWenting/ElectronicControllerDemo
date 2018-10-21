package zhawenting.electroniccontroller.config;

import android.os.Environment;

import java.io.File;

public abstract class Constants {

    public static final String DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ElectronicController/";
    public static final String APP_NAME = "ElectronicControllerDemo";
    public static final String LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ElectronicController/log.txt";
    public static final String BASE_URL = "http://localhost:8080";


    static {
        File file = new File(DIR_PATH);
        if (!file.exists())
            file.mkdirs();
    }


}
