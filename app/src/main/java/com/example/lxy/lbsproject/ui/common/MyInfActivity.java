package com.example.lxy.lbsproject.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.MyUser;
import com.example.lxy.lbsproject.ui.manager.CheckInfActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyInfActivity extends Activity implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_college;
    private TextView tv_major;
    private TextView tv_classes;
    private TextView tv_phone;
    private Button btn_modify;
    private Button btn_back;
    private Button btn_del;
    private Button btn_checkinf;

    private String userName;
    private int modtype;
    private int deltype;

    private Context context=this;

    private MyUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinf);
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
        tv_classes=findViewById(R.id.tv_myInf_classes);
        tv_name=findViewById(R.id.tv_myInf_name);
        tv_college=findViewById(R.id.tv_myInf_college);
        tv_major=findViewById(R.id.tv_myInf_major);
        tv_phone=findViewById(R.id.tv_myInf_phone);
        btn_modify=findViewById(R.id.btn_myinf_mod);
        btn_back=findViewById(R.id.btn_myinf_back);
        btn_del=findViewById(R.id.btn_myinf_del);
        btn_checkinf=findViewById(R.id.btn_myinf_checkinf);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_checkinf.setOnClickListener(this);
    }

    private void setdata() {
        Intent intent=getIntent();
        userName=intent.getStringExtra("userName");
        modtype=intent.getIntExtra("modType",0);
        deltype=intent.getIntExtra("delType",0);
        if (modtype==0){
            btn_modify.setVisibility(View.GONE);
        }
        if (deltype==0){
            btn_del.setVisibility(View.GONE);
            btn_checkinf.setVisibility(View.GONE);
        }
        BmobQuery<MyUser> query = new BmobQuery<>();//查询数据
        query.addWhereEqualTo("username", userName);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(final List<MyUser> list, BmobException e) {
                if (e == null) {
                    myUser=list.get(0);
                    tv_name.setText(list.get(0).getName());
                    tv_classes.setText(list.get(0).getClasses());
                    tv_college.setText(list.get(0).getCollege());
                    tv_major.setText(list.get(0).getMajor());
                    tv_phone.setText(list.get(0).getMobilePhoneNumber());
                } else {
                    Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_myinf_mod:
                Intent intent=new Intent(this,ModifyInfActivity.class);
                intent.putExtra("obId",myUser.getObjectId());
                startActivity(intent);
                break;
            case R.id.btn_myinf_back:finish();break;
            case R.id.btn_myinf_del:
                MyUser user=new MyUser();
                user.delete(myUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
                case R.id.btn_myinf_checkinf:
                        Intent intent1=new Intent(this, CheckInfActivity.class);
                        intent1.putExtra("userName",userName);
                        startActivity(intent1);
                    break;
        }
    }
}
