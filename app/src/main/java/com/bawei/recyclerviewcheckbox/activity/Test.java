package com.bawei.recyclerviewcheckbox.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bawei.recyclerviewcheckbox.R;
import com.bawei.recyclerviewcheckbox.adapter.Two_RecAdapter;
import com.bawei.recyclerviewcheckbox.bean.Bean;
import com.bawei.recyclerviewcheckbox.utils.DividerItemDecoration;

import java.util.ArrayList;

public class Test extends AppCompatActivity {

    private RecyclerView test_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        test_rec = (RecyclerView) findViewById(R.id.test_rec);
    }

    private void initData() {
        Intent intent = getIntent();
        ArrayList<Bean.DataBean> list = intent.getParcelableArrayListExtra("list");
        Log.e("-----s--------", list.size() + "");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        test_rec.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        test_rec.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager
                .VERTICAL, Color.RED, 5));
        test_rec.setItemAnimator(new DefaultItemAnimator());
        Two_RecAdapter adapter = new Two_RecAdapter(this,list);
        test_rec.setAdapter(adapter);
    }


}
