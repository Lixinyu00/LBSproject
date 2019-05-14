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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.Memo;
import com.example.lxy.lbsproject.data.model.Notice;

import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyActivity extends Activity implements View.OnClickListener {
    private static final int MEMO = 1;  //备忘录标识
    private static final int NOTICEMAN = 3; //公告管理

    private TextView tv_day;
    private TextView tv_time;
    private Button btn_back;
    private Button btn_cancel;
    private Button btn_save;
    private EditText et_add;
    private EditText et_title;
    private RelativeLayout relativeLayout;

    private int funType;
    private String obId;
    private Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        init();
        setlistener();
        setdata();
    }

    private void init() {
        tv_day = findViewById(R.id.tv_modify_day);
        tv_time = findViewById(R.id.tv_modify_time);
        btn_back = findViewById(R.id.btn_modify_back);
        btn_cancel = findViewById(R.id.btn_modify_cancel);
        btn_save = findViewById(R.id.btn_modify_save);
        et_add = findViewById(R.id.et_modify);
        et_title = findViewById(R.id.et_modify_title);
        relativeLayout = findViewById(R.id.rl_modify);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        tv_day.setOnClickListener(this);
        tv_time.setOnClickListener(this);
    }

    private void setdata() {
        Intent intent = getIntent();
        funType = intent.getIntExtra("funType", 0);
        obId=intent.getStringExtra("obId");
        switch (funType){
            case MEMO:
                BmobQuery<Memo> query1 = new BmobQuery<>();//查询数据
                query1.addWhereEqualTo("objectId", obId);
                query1.findObjects(new FindListener<Memo>() {
                    @Override
                    public void done(final List<Memo> list, BmobException e) {
                        if (e == null) {
                            tv_day.setText(list.get(0).getDay());
                            tv_time.setText(list.get(0).getTime());
                            et_title.setText(list.get(0).getTitle());
                            et_add.setText(list.get(0).getContent());
                        } else {
                            Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case NOTICEMAN:
                relativeLayout.setVisibility(View.GONE);
                BmobQuery<Notice> query3 = new BmobQuery<>();//查询数据
                query3.addWhereEqualTo("objectId", obId);
                query3.findObjects(new FindListener<Notice>() {
                    @Override
                    public void done(final List<Notice> list, BmobException e) {
                        if (e == null) {
                            et_title.setText(list.get(0).getTitle());
                            et_add.setText(list.get(0).getContent());
                        } else {
                            Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_modify_day:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_day.setText(year + "/" + month + "/" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.tv_modify_time:
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
            case R.id.btn_modify_back:
                finish();
                break;
            case R.id.btn_modify_cancel:
                finish();
                break;
            case R.id.btn_modify_save:
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
                memo.update(obId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
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
                notice.update(obId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
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
}
