package zhawenting.electroniccontroller.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/9/27.
 */
public class FileUtils {


    //写文件
    public static void writeString(String fileName, String content) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //写文件
    public static void writeString(File fileName, String content) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
