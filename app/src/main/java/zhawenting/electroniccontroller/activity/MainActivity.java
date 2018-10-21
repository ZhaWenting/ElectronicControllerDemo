package zhawenting.electroniccontroller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.base.ActivityCollector;
import zhawenting.electroniccontroller.base.BaseActivity;
import zhawenting.electroniccontroller.entity.FixtureEntity;
import zhawenting.electroniccontroller.entity.TabEntity;
import zhawenting.electroniccontroller.entity.WeatherEntity;
import zhawenting.electroniccontroller.fragment.FixtureFragment;
import zhawenting.electroniccontroller.myservice.BedroomFixtureApi;
import zhawenting.electroniccontroller.myservice.WeatherApi;
import zhawenting.electroniccontroller.util.AssetFile;
import zhawenting.electroniccontroller.util.CheckPermissionUtils;
import zhawenting.electroniccontroller.util.ViewFindUtils;

public class MainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.title)
    TextView titleTv;
    @BindView(R.id.temperature)
    TextView temperatureTv;
//    private BedroomFragment bedroomFragment;
//    private KitchenFragment kitchenFragment;
//    private LivingRoomFragment livingroomFragment;

    private FixtureFragment bedroomFragment;
    private FixtureFragment kitchenFragment;
    private FixtureFragment livingroomFragment;

    private static final int FREQUENT = 5000;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private View mDecorView;
    private String[] mTitles;
    private int[] roomID;
    private int[] mIconUnselectIds;
    private int[] mIconSelectIds;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout mTabLayout;

    BedroomFixtureApi service;
    Call<WeatherEntity> weatherBeanCall;
    WeatherApi weatherService;

    boolean onUserTouch = false;
    private List<FixtureEntity> kitchenFixtureList;
    private List<FixtureEntity> livingroomFixtureList;
    private List<FixtureEntity> bedroomFixtureList;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initPermission();
        AssetFile.copyFilesFromAssets(getBaseContext(), "secret_garden.mp3", Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
        AssetFile.copyFilesFromAssets(getBaseContext(), "advisorConn_login.mp4", Environment.getExternalStorageDirectory().getAbsolutePath() + "/");

        weatherService = retrofit2.create(WeatherApi.class);
        weatherBeanCall = weatherService.getWeather();
        updateTemp();


    }

    @Override
    protected void initData() {

        mTitles = new String[]{"Bedroom", "Living Room", "Kitchen"};
        roomID = new int[]{0, 1, 2};//Bedroom:0, Living Room:1, Kitchen:2
        mIconUnselectIds = new int[mTitles.length];
        mIconSelectIds = new int[mTitles.length];

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        for (int i = 0; i < 3; i++) {
            mIconSelectIds[i] = R.mipmap.ic_launcher;
            mIconUnselectIds[i] = R.mipmap.ic_launcher;
        }

        //Show the last known state
        Gson gson = new Gson();

        bedroomFixtureList = new ArrayList<>();
        if (systemParams.getString("bedroomStr") != null) {
            String bedroomStr = systemParams.getString("bedroomStr");
            bedroomFixtureList = gson.fromJson(bedroomStr, new TypeToken<List<FixtureEntity>>() {
            }.getType());
        } else {
            //Create some data
            bedroomFixtureList.add(new FixtureEntity("Light", "Off", 0, 0));
            bedroomFixtureList.add(new FixtureEntity("Light", "Off", 0, 0));
            bedroomFixtureList.add(new FixtureEntity("AC", "Off", 1, 0));
        }

        kitchenFixtureList = new ArrayList<>();
        if (systemParams.getString("kitchenStr") != null) {
            String kitchenStr = systemParams.getString("kitchenStr");
            kitchenFixtureList = gson.fromJson(kitchenStr, new TypeToken<List<FixtureEntity>>() {
            }.getType());
        } else {
            kitchenFixtureList.add(new FixtureEntity("Light", "Off", 0, 0));
            kitchenFixtureList.add(new FixtureEntity("TV", "Off", 2, 0));
        }

        livingroomFixtureList = new ArrayList<>();
        if (systemParams.getString("livingroomStr") != null) {
            String livingroomStr = systemParams.getString("livingroomStr");
            livingroomFixtureList = gson.fromJson(livingroomStr, new TypeToken<List<FixtureEntity>>() {
            }.getType());
        } else {
            livingroomFixtureList.add(new FixtureEntity("Light", "Off", 0, 0));
            livingroomFixtureList.add(new FixtureEntity("Music", "Off", 3, 0));
            livingroomFixtureList.add(new FixtureEntity("Slow Cooker", "Off", 4, 0));
        }


        bedroomFragment = new FixtureFragment();//Light:0, AC:1, TV:2, Music:3, Slow cooker:4
        bedroomFragment.getInstance(this, bedroomFixtureList);
        mFragments.add(bedroomFragment);

        kitchenFragment = new FixtureFragment();
        kitchenFragment.getInstance(this, kitchenFixtureList);
        mFragments.add(kitchenFragment);

        livingroomFragment = new FixtureFragment();
        livingroomFragment.getInstance(this, livingroomFixtureList);
        mFragments.add(livingroomFragment);

        mDecorView = getWindow().getDecorView();
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tablayout);
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
        mTabLayout.setOnTabSelectListener(this);
        mTabLayout.setCurrentTab(0);

    }

    Timer weatherTimer = new Timer();
    TimerTask weatherTask = new TimerTask() {
        @Override
        public void run() {
            if (!onUserTouch)
                updateTemp();
        }
    };

    private void updateTemp() {
        weatherBeanCall.clone().enqueue(new Callback<WeatherEntity>() {
            @Override
            public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                double temperature = response.body().getConsolidated_weather().get(0).getThe_temp();
                temperatureTv.setText((int) temperature + " ℃");
                FixtureFragment bedroodFragment = (FixtureFragment) mFragments.get(0);
                if (bedroodFragment != null && bedroodFragment.isVisible()) {
                    bedroodFragment.setACState(2, (int) temperature);
                }
            }

            @Override
            public void onFailure(Call<WeatherEntity> call, Throwable t) {
                showShortToast(getString(R.string.NetworkError));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        weatherTimer = new Timer();
        weatherTask = new TimerTask() {
            @Override
            public void run() {
                if (!onUserTouch)
                    updateTemp();
            }
        };
        weatherTimer.schedule(weatherTask, 2000, FREQUENT);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((System.currentTimeMillis() - exitTime) > 1000) {
            showShortToast("Click twice to exit");
            exitTime = System.currentTimeMillis();
        } else {
            //list to json
            Gson gson = new Gson();
            //Persist data when exit.
            String bedroomStr = gson.toJson(bedroomFixtureList);
            systemParams.setString("bedroomStr", bedroomStr);
            String kitchenStr = gson.toJson(kitchenFixtureList);
            systemParams.setString("kitchenStr", kitchenStr);
            String livingroomStr = gson.toJson(livingroomFixtureList);
            systemParams.setString("livingroomStr", livingroomStr);

            ActivityCollector.finishAll();
            System.exit(0);
        }
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onUserTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                onUserTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                onUserTouch = false;
                break;
        }
        return true;
    }


    private void initPermission() {
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length != 0) {
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (weatherTask != null) {
            weatherTask.cancel();
        }
        if (weatherTimer != null) {
            weatherTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (weatherTask != null) {
            weatherTask.cancel();
            weatherTask = null;
        }
        if (weatherTimer != null) {
            weatherTimer.cancel();
            weatherTimer = null;
        }
    }

    @Override
    public void onTabSelect(int position) {
        titleTv.setText(mTitles[position]);
    }

    @Override
    public void onTabReselect(int position) {
        titleTv.setText(mTitles[position]);
    }



}
