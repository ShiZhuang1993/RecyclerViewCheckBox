package com.bawei.recyclerviewcheckbox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView main_rec;
    private Button main_bt;
    private ArrayList<String> list;
    private RecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        main_bt = (Button) findViewById(R.id.main_bt);
        main_bt.setOnClickListener(this);
        main_rec = (RecyclerView) findViewById(R.id.main_rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        main_rec.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        main_rec.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager
                .VERTICAL, Color.RED, 5));
        main_rec.setItemAnimator(new DefaultItemAnimator());
        //添加数据
        /* File directory = Environment.getExternalStorageDirectory();
        File[] files = directory.listFiles();*/
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("zhangsan" + i);
        }
        adapter = new RecAdapter(list, this);
        main_rec.setAdapter(adapter);
        adapter.setRecyclerViewOnItemClickListener(new RecAdapter.RecyclerViewOnItemClickListener
                () {
            @Override
            public void onItemClickListener(View view, int position) {
                //点击事件
                //设置选中的项
                adapter.setSelectItem(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                //长按事件
                adapter.setShowBox();
                //设置选中的项
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    //toolbar获取条目
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "全选", Toast.LENGTH_SHORT).show();
                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, true);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.action_add:
                Map<Integer, Boolean> m = adapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    m.put(i, false);
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(MainActivity.this, "全不选", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bt:
                Intent intent = new Intent(this, Test.class);
                startActivity(intent);
        }
        //获取你选中的item
        Map<Integer, Boolean> map = adapter.getMap();
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)) {
                Log.e("TAG", "你选了第：" + i + "项");
            }
        }
    }

}
