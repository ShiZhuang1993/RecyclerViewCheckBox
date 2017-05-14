package com.bawei.recyclerviewcheckbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.recyclerviewcheckbox.R;
import com.bawei.recyclerviewcheckbox.bean.Bean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * 类用途：
 * 作者：ShiZhuangZhuang
 * 时间：2017/5/13 10:55
 */

public class Two_RecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Bean.DataBean> list;
    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private View view;

    public Two_RecAdapter(Context context, ArrayList<Bean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    //视图1
    public class ViewHolderss extends RecyclerView.ViewHolder {
        private TextView one_te;
        private ImageView one_imager;

        public ViewHolderss(View root) {
            super(root);
            one_te = (TextView) root.findViewById(R.id.one_te);
            one_imager = (ImageView) root.findViewById(R.id.one_imager);
        }
    }

    //视图2
    public class ViewHolderi extends RecyclerView.ViewHolder {
        private TextView two_te;
        private ImageView two_imager;
        private ImageView two_imager1;

        public ViewHolderi(View root) {
            super(root);
            two_te = (TextView) root.findViewById(R.id.two_te);
            two_imager = (ImageView) root.findViewById(R.id.two_imager);
            two_imager1 = (ImageView) root.findViewById(R.id.two_imager1);
        }
    }

    //视图3
    public class ViewHolders extends RecyclerView.ViewHolder {
        private ImageView three_imager;
        private TextView three_te;

        public ViewHolders(View root) {
            super(root);
            three_te = (TextView) root.findViewById(R.id.three_te);
            three_imager = (ImageView) root.findViewById(R.id.three_imager);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int i = position % 3;
        if (i == 0) {
            return ONE;
        } else if (i == 1) {
            return TWO;
        } else if (i == 2) {
            return THREE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_itme,
                    parent, false);
            return new ViewHolderss(view);
        } else if (viewType == TWO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two_itme,
                    parent, false);
            return new ViewHolderi(view);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.three_itme,
                    parent, false);
            return new ViewHolders(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == 1) {
            ViewHolderss holderss = (ViewHolderss) holder;
            holderss.one_te.setText(list.get(position).getGoods_name());
            Glide.with(context).load(list.get(position).getGoods_img()).placeholder(R.mipmap
                    .aaaaw).skipMemoryCache(true).into
                    (holderss.one_imager);
        } else if (type == 2) {
            ViewHolderi holderi = (ViewHolderi) holder;
            holderi.two_te.setText(list.get(position).getGoods_name());
            Glide.with(context).load(list.get(position).getGoods_img()).placeholder(R.mipmap
                    .aaaaw).skipMemoryCache(true).into
                    (holderi.two_imager);
            Glide.with(context).load(list.get(position).getGoods_img()).placeholder(R.mipmap
                    .aaaaw).skipMemoryCache(true).into
                    (holderi.two_imager1);
        } else if (type == 3) {
            ViewHolders holders = (ViewHolders) holder;
            holders.three_te.setText(list.get(position).getGoods_name());
            Glide.with(context).load(list.get(position).getGoods_img()).placeholder(R.mipmap
                    .aaaaw).skipMemoryCache(true).into
                    (holders.three_imager);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
