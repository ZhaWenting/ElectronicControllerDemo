package zhawenting.electroniccontroller.fragment;


import android.app.Fragment;
import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class LivingRoomFragment extends BaseFragment{

    FixtureAdapter listAdapter;
    List<FixtureBean> listItem;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    Context context;

    public LivingRoomFragment() {
        // Required empty public constructor
    }

    @Override
    protected void iniData() {
        listItem = new ArrayList();
        listItem.add(new FixtureBean("Temperature", "Loading..."));
        listItem.add(new FixtureBean("Light", "Off"));
        listItem.add(new FixtureBean("TV", "Off"));
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
        return (R.layout.fragment_livingroom);
    }

}
