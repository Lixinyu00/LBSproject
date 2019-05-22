package com.example.lxy.lbsproject.ui.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.CheckIn;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CheckInfActivity extends Activity implements View.OnClickListener {

    private Button btn_back;
    private RecyclerView recyclerView;

    private CheckInfAdapter adapter;

    private String userName;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinf);
        init();
        setlistener();
        setdata();
    }

    private void init() {
        btn_back = findViewById(R.id.btn_checkinf_back);
        recyclerView = findViewById(R.id.rv_checkinf);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
    }

    private void setdata() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        adapter = new CheckInfAdapter();
        BmobQuery<CheckIn> query = new BmobQuery<>();
        query.addWhereEqualTo("user", userName);
        query.findObjects(new FindListener<CheckIn>() {
            @Override
            public void done(List<CheckIn> list, BmobException e) {
                if (e == null) {
                    adapter.setData(list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
