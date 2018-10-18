package zhawenting.electroniccontroller.activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.base.ActivityCollector;
import zhawenting.electroniccontroller.base.BaseActivity;
import zhawenting.electroniccontroller.fragment.BedroomFragment;
import zhawenting.electroniccontroller.fragment.KitchenFragment;
import zhawenting.electroniccontroller.fragment.LivingRoomFragment;
import zhawenting.electroniccontroller.util.CheckPermissionUtils;
import zhawenting.electroniccontroller.webservice.BedroomFixtureApi;

public class MainActivity extends BaseActivity {

    private BedroomFragment bedroomFragment;
    private KitchenFragment kitchenFragment;
    private LivingRoomFragment livingroomFragment;

    @BindView(R.id.vp_content)
    FrameLayout vpContent;
    @BindView(R.id.living_room)
    RadioButton livingRoom;
    @BindView(R.id.bedroom)
    RadioButton bedroom;
    @BindView(R.id.kitchen)
    RadioButton kitchen;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initPermission();
        fragmentManager = getSupportFragmentManager();
        bedroom.performClick();

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

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    @OnClick({R.id.living_room, R.id.bedroom, R.id.kitchen,R.id.exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.living_room:
                setTabSelection(0);
                break;
            case R.id.bedroom:
                setTabSelection(1);
                break;
            case R.id.kitchen:
                setTabSelection(2);
                break;
            case R.id.exit:
                if (bedroomFragment instanceof BedroomFragment)
                    bedroomFragment.saveStates();
                ActivityCollector.finishAll();
                System.exit(0);
                break;
        }
    }

    private void setTabSelection(int index) {
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (livingroomFragment == null) {
                    livingroomFragment = new LivingRoomFragment();
                    transaction.add(R.id.vp_content, livingroomFragment);
                } else
                    transaction.show(livingroomFragment);
                break;

            case 1:
                if (bedroomFragment == null) {
                    bedroomFragment = new BedroomFragment();
                    transaction.add(R.id.vp_content, bedroomFragment);
                } else
                    transaction.show(bedroomFragment);
                break;

            case 2:
                if (kitchenFragment == null) {
                    kitchenFragment = new KitchenFragment();
                    transaction.add(R.id.vp_content, kitchenFragment);
                } else
                    transaction.show(kitchenFragment);
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (kitchenFragment != null)
            transaction.hide(kitchenFragment);
        if (bedroomFragment != null)
            transaction.hide(bedroomFragment);
        if (livingroomFragment != null)
            transaction.hide(livingroomFragment);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (kitchenFragment == null && fragment instanceof KitchenFragment) {
            kitchenFragment = (KitchenFragment) fragment;
        } else if (livingroomFragment == null && fragment instanceof LivingRoomFragment) {
            livingroomFragment = (LivingRoomFragment) fragment;
        } else if (bedroomFragment == null && fragment instanceof BedroomFragment) {
            bedroomFragment = (BedroomFragment) fragment;
        }
    }

}
