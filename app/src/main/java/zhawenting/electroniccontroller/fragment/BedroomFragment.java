package zhawenting.electroniccontroller.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.sql.Time;
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
import zhawenting.electroniccontroller.bean.FixtureBean;
import zhawenting.electroniccontroller.bean.WeatherBean;
import zhawenting.electroniccontroller.myservice.BedroomFixtureApi;
import zhawenting.electroniccontroller.myservice.WeatherApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class BedroomFragment extends BaseFragment implements View.OnTouchListener {

    FixtureAdapter listAdapter;
    List<FixtureBean> listItem;
    Context context;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    BedroomFixtureApi service;
    Call<WeatherBean> weatherBeanCall;
    WeatherApi weatherService;

    boolean onUserTouch = false;
    private static final int FREQUENT = 5000;

    public BedroomFragment() {

    }

    @Override
    protected void iniData() {
        listItem = new ArrayList();
        listItem.add(new FixtureBean("Temperature", "Loading..."));

        // Show the last known state of the bedroom
        if (systemParams.getString("Bedroom_light1", "") != null) {
            String bedroom_light1 = systemParams.getString("Bedroom_light1", "");
//            showShortToast(bedroom_light1);
            if (!TextUtils.isEmpty(bedroom_light1)) {
                listItem.add(new FixtureBean("Light1", bedroom_light1));
            } else {
                listItem.add(new FixtureBean("Light1", "Off"));
            }
        }

        if (systemParams.getString("Bedroom_light2", "") != null) {
            String bedroom_light2 = systemParams.getString("Bedroom_light2", "");
//            showShortToast(bedroom_light2);
            if (!TextUtils.isEmpty(bedroom_light2)) {
                listItem.add(new FixtureBean("Light2", bedroom_light2));
            } else {
                listItem.add(new FixtureBean("Light2", "Off"));
            }
        }

        if (systemParams.getString("Bedroom_AC", "") != null) {
            String bedroom_ac = systemParams.getString("Bedroom_AC", "");
            if (!TextUtils.isEmpty(bedroom_ac)) {
                listItem.add(new FixtureBean("AC", bedroom_ac));
            } else {
                listItem.add(new FixtureBean("AC", "Off"));
            }
        }

        weatherService = retrofit2.create(WeatherApi.class);
        weatherBeanCall = weatherService.getWeather();

        updateTemp();
        weatherTimer.schedule(weatherTask,FREQUENT,FREQUENT);
    }

    private void updateTemp() {
        weatherBeanCall.clone().enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                double temperature = response.body().getConsolidated_weather().get(0).getThe_temp();
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
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                showShortToast(getString(R.string.NetworkError));
            }
        });
    }

    @Override
    protected void iniView() {
        context = getActivity();
        listAdapter = new FixtureAdapter(context, listItem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(listAdapter);
    }


    @Override
    public int getLayoutRes() {
        return (R.layout.fragment_bedroom);
    }

    Timer weatherTimer = new Timer();
    TimerTask weatherTask = new TimerTask() {
        @Override
        public void run() {
            if (!onUserTouch)
                updateTemp();
        }
    };


    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
        return false;
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
    public void onDestroy() {
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
