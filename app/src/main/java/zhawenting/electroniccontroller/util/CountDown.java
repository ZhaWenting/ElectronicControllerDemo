package zhawenting.electroniccontroller.util;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package zhawenting.electroniccontroller.util
 * @date 2018/10/21 12:37
 */
public class CountDown {


    public static String timeCount(int time) {
        while (time > 0) {
            time--;
            try {
                Thread.sleep(1000);
                int hh = time / 60 / 60 % 60;
                int mm = time / 60 % 60;
                int ss = time % 60;
                return hh+":"+mm+":"+ss;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

}
