package com.example.lxy.lbsproject.ui.common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.Memo;
import com.example.lxy.lbsproject.data.model.Notice;
import com.example.lxy.lbsproject.data.model.Suggest;

import java.util.Calendar;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddActivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final int MEMO = 1;  //备忘录标识
    private static final int NOTICEMAN = 3; //公告管理
    private static final int SUGGEST = 5;  //意见建议

    private TextView tv_day;
    private TextView tv_time;
    private Button btn_back;
    private Button btn_cancel;
    private Button btn_save;
    private EditText et_add;
    private EditText et_title;
    private EditText et_select;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private RadioGroup radioGroup;

    private String userName;
    private int funType;
    private Context context = this;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        setlistener();
        setdata();
    }

    private void init() {
        tv_day = findViewById(R.id.tv_add_day);
        tv_time = findViewById(R.id.tv_add_time);
        btn_back = findViewById(R.id.btn_add_back);
        btn_cancel = findViewById(R.id.btn_add_cancel);
        btn_save = findViewById(R.id.btn_add_save);
        et_add = findViewById(R.id.et_add);
        et_title = findViewById(R.id.et_title);
        et_select = findViewById(R.id.et_select);
        relativeLayout = findViewById(R.id.rl_add);
        linearLayout = findViewById(R.id.ll_select);
        radioGroup = findViewById(R.id.rg_select);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        tv_day.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void setdata() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        funType = intent.getIntExtra("funType", 0);
        switch (funType) {
            case MEMO:
                break;
            case NOTICEMAN:
                relativeLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case SUGGEST:
                relativeLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_day:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int mon=month+1;
                        tv_day.setText(year + "/" + mon + "/" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tv_add_time:
                Calendar calendar1 = Calendar.getInstance();
                new TimePickerDialog(this,
                        // 绑定监听器
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                tv_time.setText(hourOfDay + " : " + minute);
                            }
                        }
                        // 设置初始时间
                        , calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), true).show();
                break;
            case R.id.btn_add_back:
                finish();
                break;
            case R.id.btn_add_cancel:
                finish();
                break;
            case R.id.btn_add_save:
                save();
                break;
        }
    }

    private void save() {
        switch (funType) {
            case MEMO:
                Memo memo=new Memo();
                memo.setDay(tv_day.getText().toString());
                memo.setTime(tv_time.getText().toString());
                memo.setTitle(et_title.getText().toString());
                memo.setContent(et_add.getText().toString());
                memo.setUser(userName);
                memo.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(context,"保存成功！" , Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(context,"保存失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case NOTICEMAN:
                Notice notice=new Notice();
                notice.setTitle(et_title.getText().toString());
                notice.setContent(et_add.getText().toString());
                if (type==1){
                    notice.setType("all");
                }else {
                    notice.setType(et_select.getText().toString());
                }
                notice.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(context,"保存成功！" , Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(context,"保存失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case SUGGEST:
                Suggest suggest=new Suggest();
                suggest.setTitle(et_title.getText().toString());
                suggest.setContent(et_add.getText().toString());
                suggest.setUser(userName);
                suggest.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(context,"保存成功！" , Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(context,"保存失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==R.id.rb_all){
            type=1;
        }else {
            type=2;
        }
    }
}
