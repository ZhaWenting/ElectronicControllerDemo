package zhawenting.electroniccontroller.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：Activity管理器
 * 创建人：Administrator
 * 创建时间：2017/9/25 0025
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity : activities) {
            if(!activity.isFinishing())
                activity.finish();
        }
    }
}
