package zhawenting.electroniccontroller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.adapter.MenuAdapter;
import zhawenting.electroniccontroller.base.BaseActivity;
import zhawenting.electroniccontroller.entity.MenuEntity;

public class SlowCookerActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MenuAdapter listAdapter;
    private List<MenuEntity> listItem;

    @Override
    protected void initData() {
        listItem = new ArrayList<>();
        listItem.add(new MenuEntity(1, "Gingerbread Christmas pudding"));
        listItem.add(new MenuEntity(2, "Vegan curry"));
        listItem.add(new MenuEntity(3, "Thai red chicken curry"));
        listItem.add(new MenuEntity(4, "Limoncello tiramisu"));
        listItem.add(new MenuEntity(5, "Rhubarb gin and plum cake"));

    }

    @Override
    protected void initView() {
        listAdapter = new MenuAdapter(this, listItem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(listAdapter);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_slow_cooker;
    }


    @OnClick({R.id.tempTv, R.id.startTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tempTv:
                break;
            case R.id.startTv:
                Intent intent = new Intent();
                intent.putExtra("result", "start");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }


}
