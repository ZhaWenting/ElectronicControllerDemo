package zhawenting.electroniccontroller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.entity.FixtureEntity;


public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.MyHolder> {

    Context context;
    List<FixtureEntity> fixtureEntities;

    public FixtureAdapter(Context context, List<FixtureEntity> deviceBeans) {
        this.context = context;
        this.fixtureEntities = deviceBeans;
    }

    public FixtureAdapter(Context context, List<FixtureEntity> deviceBeans,ICallback callback) {
        this.context = context;
        this.fixtureEntities = deviceBeans;
        this.callback = callback;
    }

    public void update(List<FixtureEntity> deviceBeans) {
        this.fixtureEntities = deviceBeans;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        FixtureEntity deviceBean = fixtureEntities.get(position);
        holder.fixtureName.setText(deviceBean.getFixtureName());
        holder.fixtureState.setText(deviceBean.getFixtureState());

//        if ("On".equals(holder.fixtureState.getText())) {
//            if ("Music".equals(holder.fixtureName)) {
//                callback.fixutureAdapterCallback(1,position);
//            }
//        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("On".equals(holder.fixtureState.getText())) {
                    callback.fixutureAdapterCallback(0,position);
                    fixtureEntities.get(position).setFixtureState("Off");
                    holder.fixtureState.setText("Off");
                } else {
                    callback.fixutureAdapterCallback(1,position);
                    fixtureEntities.get(position).setFixtureState("On");
                    holder.fixtureState.setText("On");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return fixtureEntities.size();
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


    private ICallback callback;

    public void setListener(ICallback callback) {
        this.callback = callback;
    }

    public interface ICallback {
        void fixutureAdapterCallback(int type,int position);
    }
}
