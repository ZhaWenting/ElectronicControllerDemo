package zhawenting.electroniccontroller.activity;

import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.base.ActivityCollector;
import zhawenting.electroniccontroller.base.BaseActivity;
import zhawenting.electroniccontroller.config.Constants;
import zhawenting.electroniccontroller.entity.TabEntity;
import zhawenting.electroniccontroller.entity.WeatherEntity;
import zhawenting.electroniccontroller.fragment.BedroomFragment;
import zhawenting.electroniccontroller.fragment.KitchenFragment;
import zhawenting.electroniccontroller.fragment.LivingRoomFragment;
import zhawenting.electroniccontroller.myservice.BedroomFixtureApi;
import zhawenting.electroniccontroller.myservice.WeatherApi;
import zhawenting.electroniccontroller.util.AssetFile;
import zhawenting.electroniccontroller.util.CheckPermissionUtils;
import zhawenting.electroniccontroller.util.ViewFindUtils;

public class MainActivity extends BaseActivity {

    private BedroomFragment bedroomFragment;
    private KitchenFragment kitchenFragment;
    private LivingRoomFragment livingroomFragment;

    private static final int FREQUENT = 5000;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private View mDecorView;
    private String[] mTitles;
    private int[] mIconUnselectIds;
    private int[] mIconSelectIds;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout mTabLayout;

    BedroomFixtureApi service;
    Call<WeatherEntity> weatherBeanCall;
    WeatherApi weatherService;


    boolean onUserTouch = false;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initPermission();
        AssetFile.copyFilesFromAssets(getBaseContext(), "secret_garden.mp3", Environment.getExternalStorageDirectory().getAbsolutePath()+"/");

        mTitles = new String[]{"Bedroom","Living Room","Kitchen"};
        mIconUnselectIds = new int[mTitles.length];
        mIconSelectIds = new int[mTitles.length];

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tablayout);

        for (int i = 0; i < 3; i++) {
            mIconSelectIds[i] = R.mipmap.ic_launcher;
            mIconUnselectIds[i] = R.mipmap.ic_launcher;
            switch (mTitles[i]){
                case "Bedroom":
                    bedroomFragment = new BedroomFragment();
                    mFragments.add(bedroomFragment);
                    break;
                case "Living Room":
                    livingroomFragment = new LivingRoomFragment();
                    mFragments.add(livingroomFragment);
                    break;
                case "Kitchen":
                    kitchenFragment = new KitchenFragment();
                    mFragments.add(kitchenFragment);
                    break;
            }
        }

        mTabLayout.setTabData(mTabEntities,this, R.id.fl_change, mFragments);
        mTabLayout.setCurrentTab(0);

        weatherService = retrofit2.create(WeatherApi.class);
        weatherBeanCall = weatherService.getWeather();
        updateTemp();
        weatherTimer.schedule(weatherTask,FREQUENT,FREQUENT);

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
                if(bedroomFragment.isVisible())
                    bedroomFragment.setTemperature((int)temperature);
                if(kitchenFragment.isVisible())
                    kitchenFragment.setTemperature((int)temperature);
                if(livingroomFragment.isVisible())
                    livingroomFragment.setTemperature((int)temperature);
            }

            @Override
            public void onFailure(Call<WeatherEntity> call, Throwable t) {
                showShortToast(getString(R.string.NetworkError));
            }
        });
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (bedroomFragment instanceof BedroomFragment) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                showShortToast("Click twice to exit");
                exitTime = System.currentTimeMillis();
            } else {
                bedroomFragment.onKeyDown(keyCode, event);
                ActivityCollector.finishAll();
                System.exit(0);
            }
            return true;
        }
        return false;
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
}
