package zhawenting.electroniccontroller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.entity.MenuEntity;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyHolder>{

    Context context;
    List<MenuEntity> menuList;

    public MenuAdapter(Context context, List<MenuEntity> menuList) {
        this.context = context;
        this.menuList = menuList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        final MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        MenuEntity menuEntity = menuList.get(position);
        holder.menuName.setText(menuEntity.getMenuName());
        holder.menuID.setText(menuEntity.getMenuID()+"");

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView menuID;

        public MyHolder(View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menu_name);
            menuID = itemView.findViewById(R.id.menu_id);
        }
    }


    private ICallback callback;

    public interface ICallback {
        void menuCallback(int clickItem);
    }
}
