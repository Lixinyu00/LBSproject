package com.example.lxy.lbsproject.ui.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.MyUser;
import com.example.lxy.lbsproject.ui.common.AddActivity;
import com.example.lxy.lbsproject.ui.common.MateViewActivity;
import com.example.lxy.lbsproject.ui.common.MyInfActivity;
import com.example.lxy.lbsproject.ui.login.LogInActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl_my_inf;
    private RelativeLayout rl_my_mate;
    private RelativeLayout rl_my_suggest;
    private RelativeLayout rl_my_exit;

    private TextView tv_name;
    private TextView tv_classes;
    private Button btn_back;

    private String userName;

    private Context context = this;

    private MyUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        init();
        setlistener();
        setdata();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setdata();
    }

    private void init() {
        rl_my_inf = findViewById(R.id.rl_my_inf);
        rl_my_mate = findViewById(R.id.rl_my_mate);
        rl_my_suggest = findViewById(R.id.rl_my_suggest);
        rl_my_exit = findViewById(R.id.rl_my_exit);
        tv_name = findViewById(R.id.tv_my_name);
        tv_classes = findViewById(R.id.tv_my_classes);
        btn_back = findViewById(R.id.btn_my_back);
    }

    private void setlistener() {
        rl_my_inf.setOnClickListener(this);
        rl_my_mate.setOnClickListener(this);
        rl_my_suggest.setOnClickListener(this);
        rl_my_exit.setOnClickListener(this);
        btn_back.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_my_back:
                finish();
                break;
            case R.id.rl_my_inf:
                Intent intent1 = new Intent(this, MyInfActivity.class);
                intent1.putExtra("userName", userName);
                intent1.putExtra("modType", 1);
                intent1.putExtra("delType",0);
                this.startActivity(intent1);
                break;
            case R.id.rl_my_mate:
                Intent intent2 = new Intent(this, MateViewActivity.class);
                intent2.putExtra("param", myUser.getClasses());
                intent2.putExtra("queryType", 4);
                this.startActivity(intent2);
                break;
            case R.id.rl_my_suggest:
                Intent intent3 = new Intent(this, AddActivity.class);
                intent3.putExtra("userName", userName);
                intent3.putExtra("funType", 5);
                this.startActivity(intent3);
                break;
            case R.id.rl_my_exit:
                Intent intent4 = new Intent(this, LogInActivity.class);
                this.startActivity(intent4);
                finish();
                break;
        }
    }
}
