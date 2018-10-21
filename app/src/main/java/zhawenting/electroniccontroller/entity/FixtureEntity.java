package zhawenting.electroniccontroller.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package zhawenting.electroniccontroller.bean
 * @date 2018/10/18 14:07
 */
public class FixtureEntity implements Parcelable{
    String fixtureName;
    String fixtureState;
    int fixtureID;
    int value;


    public FixtureEntity() {
    }

    public FixtureEntity(String fixtureName, String fixtureState) {
        this.fixtureName = fixtureName;
        this.fixtureState = fixtureState;
    }

    public FixtureEntity(String fixtureName, String fixtureState, int fixtureID,int value) {
        this.fixtureName = fixtureName;
        this.fixtureState = fixtureState;
        this.fixtureID = fixtureID;
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getFixtureID() {
        return fixtureID;
    }

    public void setFixtureID(int fixtureID) {
        this.fixtureID = fixtureID;
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

    protected FixtureEntity(Parcel in) {
        fixtureName = in.readString();
        fixtureState = in.readString();
    }

    public static final Creator<FixtureEntity> CREATOR = new Creator<FixtureEntity>() {
        @Override
        public FixtureEntity createFromParcel(Parcel in) {
            return new FixtureEntity(in);
        }

        @Override
        public FixtureEntity[] newArray(int size) {
            return new FixtureEntity[size];
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
