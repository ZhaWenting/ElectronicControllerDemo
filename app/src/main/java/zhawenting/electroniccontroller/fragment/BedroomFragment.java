package zhawenting.electroniccontroller.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.adapter.FixtureAdapter;
import zhawenting.electroniccontroller.base.BaseFragment;
import zhawenting.electroniccontroller.entity.FixtureEntity;
import zhawenting.electroniccontroller.entity.WeatherEntity;
import zhawenting.electroniccontroller.myservice.BedroomFixtureApi;
import zhawenting.electroniccontroller.myservice.WeatherApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class BedroomFragment extends BaseFragment implements FixtureAdapter.ICallback {

    FixtureAdapter listAdapter;
    List<FixtureEntity> listItem;
    Context context;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    private CameraManager manager;
    private Camera camera = null;
    public BedroomFragment() {

    }

    @Override
    protected void iniData() {
        context = getActivity();
        listItem = new ArrayList();
        listItem.add(new FixtureEntity("Temperature", "Loading..."));

        // Show the last known state of the bedroom
        if (systemParams.getString("Bedroom_light1", "") != null) {
            String bedroom_light1 = systemParams.getString("Bedroom_light1", "");
//            showShortToast(bedroom_light1);
            if (!TextUtils.isEmpty(bedroom_light1)) {
                listItem.add(new FixtureEntity("Light1", bedroom_light1));
            } else {
                listItem.add(new FixtureEntity("Light1", "Off"));
            }
        }

        if (systemParams.getString("Bedroom_light2", "") != null) {
            String bedroom_light2 = systemParams.getString("Bedroom_light2", "");
//            showShortToast(bedroom_light2);
            if (!TextUtils.isEmpty(bedroom_light2)) {
                listItem.add(new FixtureEntity("Light2", bedroom_light2));
            } else {
                listItem.add(new FixtureEntity("Light2", "Off"));
            }
        }

        if (systemParams.getString("Bedroom_AC", "") != null) {
            String bedroom_ac = systemParams.getString("Bedroom_AC", "");
            if (!TextUtils.isEmpty(bedroom_ac)) {
                listItem.add(new FixtureEntity("AC", bedroom_ac));
            } else {
                listItem.add(new FixtureEntity("AC", "Off"));
            }
        }

        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            manager.getCameraIdList();
        } catch (CameraAccessException e) {
            Log.e("error", e.getMessage());
        }
    }


    @Override
    protected void iniView() {

        listAdapter = new FixtureAdapter(context, listItem);
        listAdapter.setListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(listAdapter);
    }

    public void setTemperature(int temperature){
        (listItem.get(0)).setFixtureState(temperature + " ℃");
        listAdapter.notifyItemChanged(0);
        if (temperature > 25) {
            (listItem.get(3)).setFixtureState("Off");
            listAdapter.notifyItemChanged(3);
        } else if (temperature < 25) {
            (listItem.get(3)).setFixtureState("On");
            listAdapter.notifyItemChanged(3);
        }
    }

    @Override
    public int getLayoutRes() {
        return (R.layout.fragment_bedroom);
    }



    //Persist the data for bedroom
    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveStates();
        }
    }

    public void saveStates() {
        showShortToast(listItem.get(1).getFixtureState());
        systemParams.setString("Bedroom_light1", listItem.get(1).getFixtureState());
        systemParams.setString("Bedroom_light2", listItem.get(2).getFixtureState());
        systemParams.setString("Bedroom_AC", listItem.get(3).getFixtureState());
    }


    @Override
    public void fixutureAdapterCallback(int type, int position) {
        switch (position){
            case 1:
            case 2:
                if(type==0)
                    lightSwitch(true);
                else
                    lightSwitch(false);
                break;
        }
    }

    private void lightSwitch(final boolean lightStatus) {
        if (lightStatus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                final PackageManager pm = context.getPackageManager();
                final FeatureInfo[] features = pm.getSystemAvailableFeatures();
                for (final FeatureInfo f : features) {
                    if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        if (null == camera) {
                            camera = Camera.open();
                        }
                        final Camera.Parameters parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                    }
                }
            }
        }
    }
}
