package zhawenting.electroniccontroller.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.activity.SlowCookerActivity;
import zhawenting.electroniccontroller.activity.VideoActivity;
import zhawenting.electroniccontroller.adapter.FixtureAdapter;
import zhawenting.electroniccontroller.base.BaseFragment;
import zhawenting.electroniccontroller.entity.FixtureEntity;
import zhawenting.electroniccontroller.fixture.Light;
import zhawenting.electroniccontroller.fixture.Music;
import zhawenting.electroniccontroller.util.CountDown;

/**
 * A simple {@link Fragment} subclass.
 */
public class FixtureFragment extends BaseFragment implements FixtureAdapter.ICallback {

    private static final int MENU_REQUEST = 4;
    @BindView(R.id.timer)
    TextView timerTv;
    @BindView(R.id.timerLl)
    LinearLayout timerLl;
    Unbinder unbinder;
    //    private int[] roomID;
    private Context context;

    FixtureAdapter listAdapter;
    List<FixtureEntity> listItem;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private Light light;
    private Music music;


    public FixtureFragment() {

    }

    public FixtureFragment getInstance(Context context, List<FixtureEntity> listItem) {
        FixtureFragment fixtureFragment = new FixtureFragment();
        this.listItem = listItem;
        this.context = context;
        return fixtureFragment;
    }

    @Override
    protected void iniData() {
        context = getActivity();
    }

    @Override
    protected void iniView() {
//        roomID = new int[]{0, 1, 2};//Bedroom:0, Living Room:1, Kitchen:2
//        fixtureID = new int[]{0, 1, 2, 3, 4};//Light:0, AC:1, TV:2, Music:3, Slow cooker:4

        listAdapter = new FixtureAdapter(context, listItem, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(listAdapter);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_fixture;
    }

    @Override
    public void fixutureAdapterCallback(int type, int position) {
        FixtureEntity fixtureEntity = listItem.get(position);
        switch (fixtureEntity.getFixtureID()) {//Light:0, AC:1, TV:2, Music:3, Slow cooker:4
            case 0:
                if (light == null)
                    light = new Light(context);
                light.lightSwitch(type == 0 ? true : false);
                break;
            case 1:

                break;
            case 2:
                if ("Off".equals(fixtureEntity.getFixtureState()))
                    startActivity(new Intent(context, VideoActivity.class));
                break;
            case 3:
                if (music == null)
                    music = new Music(context);
                music.musicSwitch(type == 0 ? true : false);
                break;
            case 4:
                if ("Off".equals(fixtureEntity.getFixtureState()))
                    startActivityForResult(new Intent(context, SlowCookerActivity.class), MENU_REQUEST);
                else {
                    if (timer != null)
                        timer.cancel();
                    if (timerTask!=null)
                        timerTask.cancel();
                }
                break;

        }
    }

    public void setACState(int position, int temperature) {
        if (temperature > 25) {
            listItem.get(2).setFixtureState("Off");

        } else if (temperature < 25) {
            listItem.get(2).setFixtureState("On");
        }
        listAdapter.notifyItemChanged(position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MENU_REQUEST:
                currentTime = 3600;
                String result = data.getExtras().getString("result");
                String time = CountDown.timeCount(currentTime--);
                if (!"".equals(time)) {
                    timerTv.setText(time);
                    timerLl.setVisibility(View.VISIBLE);
                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            final String time = CountDown.timeCount(currentTime--);
                            if (!"".equals(time) && currentTime >= 0) {
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        timerTv.setText(time);
                                    }
                                });
                            }
                        }
                    };
                    timer.schedule(timerTask, 0, 1000);
                }
                break;
        }

    }

    int currentTime = 3600;
    Timer timer;
    TimerTask timerTask;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (timer != null)
            timer.cancel();
        if (timerTask!=null)
            timerTask.cancel();
    }
}
