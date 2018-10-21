package zhawenting.electroniccontroller.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package zhawenting.electroniccontroller.bean
 * @date 2018/10/18 14:07
 */
public class MenuEntity {
    String menuName;
    int menuID;
    boolean isChecked;

    public MenuEntity(int menuID,String menuName) {
        this.menuName = menuName;
        this.menuID = menuID;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }
}
