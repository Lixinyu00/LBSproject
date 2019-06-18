package com.example.lxy.lbsproject.ui.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
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

    private Button btn_back;
    private Button btn_check;
    private TextView tv_name;
    private TextView tv_classes;
    private TextView tv_in;
    private TextView tv_out;

    private String userName;

    private Context context = this;

    private MyUser myUser;
    private String times;
    private int type = 0;
    private String obID;
    private List<ScanResult> results;
    private String bssid = "";

    private WifiManager wifiManager;
    private boolean isIn;
    private int j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        init();
        setlistener();
        setdata();
        handler.postDelayed(runnable, 1000);
    }

    private void init() {
        btn_back = findViewById(R.id.btn_check_back);
        btn_check = findViewById(R.id.btn_check);
        tv_name = findViewById(R.id.tv_check_name);
        tv_classes = findViewById(R.id.tv_check_classes);
        tv_in = findViewById(R.id.tv_checkin_in);
        tv_out = findViewById(R.id.tv_checkin_out);
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
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.startScan();
            results = wifiManager.getScanResults();
            j = -1;
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).BSSID.equals("fc:d7:33:1b:28:a0") || results.get(i).BSSID.equals("01:80:c2:00:00:03")) {
                    j = i;
                }
            }
            if (j != -1) {
                int wifi_level = WifiManager.calculateSignalLevel(results.get(j).level, 5);
                if (wifi_level >= 3) {
                    isIn = true;
                    bssid=results.get(j).BSSID;
                } else {
                    isIn = false;
                }
            } else {
                isIn = false;
            }
            handler.postDelayed(this, 1000);
        }
    };


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
                    if (isIn) {
                        type = 1;
                        btn_check.setText("签退");
                        tv_in.setText("上课打卡时间  " + times.substring(11, 16));
                        tv_out.setText("下课打卡时间");
                        CheckIn checkIn = new CheckIn();
                        checkIn.setDay(times.substring(0, 10));
                        checkIn.setClasses(myUser.getClasses());
                        checkIn.setName(myUser.getName());
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
                    } else {
                        Toast.makeText(context, "不在签到范围！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (isIn) {
                        type = 0;
                        btn_check.setText("签到");
                        tv_out.setText("下课打卡时间  " + times.substring(11, 16));
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
                    } else {
                        Toast.makeText(context, "不在签到范围！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
