package zhawenting.electroniccontroller.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.adapter.FixtureAdapter;
import zhawenting.electroniccontroller.base.BaseFragment;
import zhawenting.electroniccontroller.bean.FixtureBean;
import zhawenting.electroniccontroller.config.Constants;
import zhawenting.electroniccontroller.myservice.MusicService;
import zhawenting.electroniccontroller.util.AssetFile;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenFragment extends BaseFragment implements FixtureAdapter.ICallback {
    FixtureAdapter listAdapter;
    List<FixtureBean> listItem;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    Context context;
    private MusicService musicService;

    public KitchenFragment() {
        // Required empty public constructor
    }


    @Override
    protected void iniData() {
        context = getActivity();
        listItem = new ArrayList();
        listItem.add(new FixtureBean("Temperature", "Loading..."));
        listItem.add(new FixtureBean("Light", "Off"));
        listItem.add(new FixtureBean("Music", "Off"));
        listItem.add(new FixtureBean("Slow Cooker", "Off"));

        //prepare music
        bindServiceConnection();
        musicService = new MusicService();
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

    @Override
    public int getLayoutRes() {
        return (R.layout.fragment_kitchen);
    }

    @Override
    public void fixutureAdapterCallback(int type,int position) {
        switch (type) {
            case 0:
                showLongToast("Music off");
                musicService.stop();
                break;
            case 1:
                showLongToast("Music on");
                musicService.play();

        }

    }

    private void bindServiceConnection() {
        Intent intent = new Intent(getActivity(), MusicService.class);
        context.startService(intent);
        context.bindService(intent, sc, context.BIND_AUTO_CREATE);
    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicService.onDestroy();
        context.unbindService(sc);
    }
}
