package zhawenting.electroniccontroller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.bean.FixtureBean;

/**
 * description：
 * creater：zhawenting
 * date：2018/9/11 0011
 */
public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.MyHolder> {

    Context context;
    List<FixtureBean> fixtureBeans;

    public FixtureAdapter(Context context, List<FixtureBean> deviceBeans) {
        this.context = context;
        this.fixtureBeans = deviceBeans;
    }

    public void update(List<FixtureBean> deviceBeans) {
        this.fixtureBeans = deviceBeans;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        FixtureBean deviceBean = fixtureBeans.get(position);
        holder.fixtureName.setText(deviceBean.getFixtureName());
        holder.fixtureState.setText(deviceBean.getFixtureState());
        if(position!=0) {
            if ("On".equals(holder.fixtureState.getText())) {
                holder.fixtureState.setText("Off");
            }
            else {
                holder.fixtureState.setText("On");
            }
        }else {
            holder.button.setVisibility(View.INVISIBLE);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("On".equals(holder.fixtureState.getText())) {
                    holder.fixtureState.setText("Off");
                }
                else {
                    holder.fixtureState.setText("On");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return fixtureBeans.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView fixtureName;
        TextView fixtureState;
        TextView button;

        public MyHolder(View itemView) {
            super(itemView);
            fixtureName = itemView.findViewById(R.id.fixture_name);
            fixtureState = itemView.findViewById(R.id.fixture_state);
            button = itemView.findViewById(R.id.button);
        }
    }
}
