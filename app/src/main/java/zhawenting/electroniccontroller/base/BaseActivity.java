package zhawenting.electroniccontroller.base;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhawenting.electroniccontroller.config.Constants;
import zhawenting.electroniccontroller.config.SystemParams;
import zhawenting.electroniccontroller.util.LogUtil;

/**
 * 类描述：Activity基类
 * 创建人：Zhawenting
 * 创建时间：2017/9/25 0025
 */

public abstract class BaseActivity extends FragmentActivity {

    protected Retrofit retrofit;
    protected  Retrofit retrofit2;
    protected SystemParams systemParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        systemParams = SystemParams.getInstance();
        SystemParams.init(this);


        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit2 = new Retrofit.Builder()
                .baseUrl("https://www.metaweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        initData();
        initView();
    }

    public abstract int getLayoutResID();

    protected abstract void initView();

    protected void initData() {
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    //不带bundle参数的跳转界面
    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        this.finish();
        ActivityCollector.removeActivity(this);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        this.finish();
        ActivityCollector.removeActivity(this);
    }

    public void openActivityAndCloseThis(Intent intent) {
        startActivity(intent);
        this.finish();
        ActivityCollector.removeActivity(this);
    }

    public void showShortToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLongToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }





}
