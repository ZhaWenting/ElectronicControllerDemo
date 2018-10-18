package zhawenting.electroniccontroller.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhawenting.electroniccontroller.config.Constants;
import zhawenting.electroniccontroller.config.SystemParams;

/**
 * Created by Administrator on 2016/10/10.
 */
public abstract class BaseFragment extends Fragment {
    protected Retrofit retrofit;
    protected  Retrofit retrofit2;
    private Context context;
    protected SystemParams systemParams;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View layout = View.inflate(context, getLayoutRes(), null);
        ButterKnife.bind(this, layout);
        //初始化网络请求对象
//        retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        retrofit2 = new Retrofit.Builder()
                .baseUrl("https://www.metaweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //初始化缓存全局变量
        systemParams = SystemParams.getInstance();
        SystemParams.init(getContext());

        iniData();
        iniView();
        return layout;

    }

    protected void iniData(){

    };

    protected abstract void iniView();

    // short吐司
    public void showShortToast(final String text) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // long吐司
    public void showLongToast(final String text) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public abstract int getLayoutRes();

}
