package com.bawei.recyclerviewcheckbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.recyclerviewcheckbox.R;
import com.bawei.recyclerviewcheckbox.bean.Bean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 类用途：
 * 作者：ShiZhuangZhuang
 * 时间：2017/5/12 20:22
 */
public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {
    //数据源
    private ArrayList<Bean.DataBean> list;
    private Context context;
    //是否显示单选框,默认false
    private boolean isshowBox = false;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    //接口实例
    private RecyclerViewOnItemClickListener onItemClickListener;

    public RecAdapter(ArrayList<Bean.DataBean> list, Context context) {
        this.context = context;
        this.list = list;
        initMap();
    }


    //初始化map集合,默认为不选中
    private void initMap() {
        for (int i = 0; i < list.size(); i++) {
            map.put(i, false);
        }
    }

    //视图管理
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CheckBox checkBox;
        private View root;
        private ImageView pic;

        public ViewHolder(View root) {
            super(root);
            this.root = root;
            pic = (ImageView) root.findViewById(R.id.imager);
            title = (TextView) root.findViewById(R.id.tv);
            checkBox = (CheckBox) root.findViewById(R.id.cb);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //绑定视图管理者
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getGoods_name());
        Glide.with(context).load(list.get(position).getGoods_img()).placeholder(R.mipmap
                .aaaaw).skipMemoryCache(true).into
                (holder.pic);
        //长按显示/隐藏
        if (isshowBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim);
        //设置checkBox显示的动画
        if (isshowBox)
            holder.checkBox.startAnimation(animation);
        //设置Tag
        holder.root.setTag(position);
        //设置checkBox改变监听
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用map集合保存
                map.put(position, isChecked);
            }
        });
        // 设置CheckBox的状态
        if (map.get(position) == null) {
            map.put(position, false);
        }
        holder.checkBox.setChecked(map.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,
                false);
        ViewHolder vh = new ViewHolder(root);
        //为Item设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        return vh;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onItemClickListener.onItemClickListener(v, (Integer) v.getTag());
        }
    }

    //长按事件
    @Override
    public boolean onLongClick(View v) {
        //不管显示隐藏，清空状态
        initMap();
        return onItemClickListener != null && onItemClickListener.onItemLongClickListener(v,
                (Integer) v.getTag());
    }

    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener
                                                           onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        isshowBox = !isshowBox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }

    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }
}