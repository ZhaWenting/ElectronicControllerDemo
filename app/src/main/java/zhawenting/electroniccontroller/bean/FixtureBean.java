package zhawenting.electroniccontroller.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package zhawenting.electroniccontroller.bean
 * @date 2018/10/18 14:07
 */
public class FixtureBean implements Parcelable{
    String fixtureName;
    String fixtureState;


    public FixtureBean() {
    }

    public FixtureBean(String fixtureName, String fixtureState) {
        this.fixtureName = fixtureName;
        this.fixtureState = fixtureState;
    }


    public String getFixtureState() {

        return fixtureState;
    }

    public void setFixtureState(String fixtureState) {
        this.fixtureState = fixtureState;
    }

    public String getFixtureName() {
        return fixtureName;
    }

    public void setFixtureName(String fixtureName) {
        this.fixtureName = fixtureName;
    }

    protected FixtureBean(Parcel in) {
        fixtureName = in.readString();
        fixtureState = in.readString();
    }

    public static final Creator<FixtureBean> CREATOR = new Creator<FixtureBean>() {
        @Override
        public FixtureBean createFromParcel(Parcel in) {
            return new FixtureBean(in);
        }

        @Override
        public FixtureBean[] newArray(int size) {
            return new FixtureBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fixtureName);
        dest.writeString(this.fixtureState);
    }
}
