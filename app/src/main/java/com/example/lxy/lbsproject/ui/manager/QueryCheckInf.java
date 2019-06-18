package com.example.lxy.lbsproject.ui.manager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.CheckIn;
import com.example.lxy.lbsproject.data.model.Notice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class QueryCheckInf extends Activity implements View.OnClickListener {

    private Button btn_back;
    private Button btn_query;
    private TextView tv_day;
    private TextView tv_time;
    private TextView tv_show;
    private EditText et_classes;
    private Context context = this;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private QueryCheckAdapter adapter;

    private int time;
    private int nums;
    private int timeIn;
    private int timeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querycheckin);
        init();
        setlistener();
    }

    private void init() {
        btn_back = findViewById(R.id.btn_query_check_back);
        btn_query = findViewById(R.id.btn_query_check);
        tv_day = findViewById(R.id.tv_query_check_day);
        tv_time = findViewById(R.id.tv_query_check_time);
        tv_show = findViewById(R.id.tv_query_show);
        et_classes = findViewById(R.id.et_query_check_classes);
        linearLayout = findViewById(R.id.ll_query);
        recyclerView = findViewById(R.id.rv_query);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        tv_day.setOnClickListener(this);
        tv_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query_check_back:
                finish();
                break;
            case R.id.btn_query_check:
                nums=0;
                if (et_classes.getText().toString().equals("")) {
                    Toast.makeText(this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    adapter=new QueryCheckAdapter();
                    time = getTime(tv_time.getText().toString());
                    BmobQuery<CheckIn> q1 = new BmobQuery<>();//查询数据
                    q1.addWhereEqualTo("day", tv_day.getText().toString());
                    BmobQuery<CheckIn> q2 = new BmobQuery<>();
                    q2.addWhereEqualTo("classes", et_classes.getText().toString());
                    List<BmobQuery<CheckIn>> queries = new ArrayList<BmobQuery<CheckIn>>();
                    queries.add(q1);
                    queries.add(q2);
                    BmobQuery<CheckIn> mainQuery = new BmobQuery<CheckIn>();
                    mainQuery.and(queries);
                    mainQuery.findObjects(new FindListener<CheckIn>() {
                        @Override
                        public void done(final List<CheckIn> list, BmobException e) {
                            if (e == null) {
                                linearLayout.setVisibility(View.VISIBLE);
                                adapter.setData(list,time);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                recyclerView.setAdapter(adapter);
                                for (int i = 0; i < list.size(); i++) {
                                    timeIn = getTime(list.get(i).getTimeIn());
                                    if (list.get(i).getTimeOut().equals("")){
                                        continue;
                                    }else {
                                        timeOut = getTime(list.get(i).getTimeOut());
                                    }
                                    if (time >= timeIn && time + 60 <= timeOut) {
                                        nums++;
                                    }
                                }
                                tv_show.setText(nums + "/"+list.size() );

                            } else {
                                Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.tv_query_check_day:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int mon = month + 1;
                        if (mon < 10) {
                            tv_day.setText(year + "-0" + mon + "-" + dayOfMonth);
                        } else {
                            tv_day.setText(year + "-" + mon + "-" + dayOfMonth);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tv_query_check_time:
                Calendar calendar1 = Calendar.getInstance();
                new TimePickerDialog(this,
                        // 绑定监听器
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                if (hourOfDay < 10) {
                                    if (minute<10){
                                        tv_time.setText("0" + hourOfDay + ":" +"0"+ minute);
                                    }else {
                                        tv_time.setText("0" + hourOfDay + ":" + minute);
                                    }
                                } else {
                                    if (minute<10){
                                        tv_time.setText(hourOfDay + ":" +"0"+ minute);
                                    }else {
                                        tv_time.setText(hourOfDay + ":" + minute);
                                    }
                                }
                            }
                        }
                        // 设置初始时间
                        , calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), true).show();
                break;
        }
    }

    private int getTime(String time) {
        int hour;
        int min;
        int mins;
        hour = Integer.valueOf(time.substring(0, 2));
        min = Integer.valueOf(time.substring(3, 5));
        mins = hour * 60 + min;
        Log.e("1111", "getTime: "+mins );
        return mins;
    }
}
