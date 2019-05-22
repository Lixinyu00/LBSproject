package com.example.lxy.lbsproject.ui.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.CheckIn;
import com.example.lxy.lbsproject.data.model.MyUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CheckInActivity extends Activity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button btn_back;
    private Button btn_check;
    private TextView tv_name;
    private TextView tv_classes;

    private String userName;

    private Context context = this;

    private MyUser myUser;
    private String times;
    private int type = 0;
    private String obID;
    private List<ScanResult> results;
    private String bssid="";

    private CheckInAdapter checkInAdapter;
    private WifiManager wifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        init();
        setlistener();
        setdata();
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_check);
        btn_back = findViewById(R.id.btn_check_back);
        btn_check = findViewById(R.id.btn_check);
        tv_name = findViewById(R.id.tv_check_name);
        tv_classes = findViewById(R.id.tv_check_classes);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_check.setOnClickListener(this);
    }

    private void setdata() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        BmobQuery<MyUser> query = new BmobQuery<>();//查询数据
        query.addWhereEqualTo("username", userName);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(final List<MyUser> list, BmobException e) {
                if (e == null) {
                    myUser = list.get(0);
                    tv_name.setText(list.get(0).getName());
                    tv_classes.setText(list.get(0).getClasses());
                } else {
                    Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        wifiManager= (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        results = wifiManager.getScanResults();
        Log.e("11111", "setdata: "+results.size() );
        checkInAdapter = new CheckInAdapter();
        checkInAdapter.setData(results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(checkInAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        checkInAdapter.setOnItemClickListener(new CheckInAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                bssid = results.get(position).BSSID;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check_back:
                finish();
                break;
            case R.id.btn_check:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(System.currentTimeMillis());
                times = formatter.format(date);
                if (type == 0) {
                    if (bssid.equals("a4:50:46:ee:f6:e5")||bssid.equals("34:96:72:5c:23:4d")){
                        type = 1;
                        btn_check.setText("签退");
                        CheckIn checkIn = new CheckIn();
                        checkIn.setDay(times.substring(0, 10));
                        checkIn.setClasses(myUser.getClasses());
                        checkIn.setUser(userName);
                        checkIn.setTimeIn(times.substring(11, 16));
                        checkIn.setMac(bssid);
                        checkIn.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    obID = s;
                                    Toast.makeText(context, "签到成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "签到失败！" + e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(context, "请选择正确的wifi！" , Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (bssid.equals("a4:50:46:ee:f6:e5")||bssid.equals("34:96:72:5c:23:4d")){
                        type = 0;
                        btn_check.setText("签到");
                        CheckIn checkIn = new CheckIn();
                        checkIn.setTimeOut(times.substring(11, 16));
                        checkIn.update(obID, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(context, "签退成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "签退失败！" + e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(context, "请选择正确的wifi！" , Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
