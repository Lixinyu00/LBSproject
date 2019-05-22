package com.example.lxy.lbsproject.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyInfActivity extends Activity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_college;
    private EditText et_major;
    private EditText et_classes;
    private EditText et_phone;
    private Button btn_save;
    private Button btn_back;

    private String objectId;

    private Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_inf);
        init();
        setlistener();
        setdata();
    }

    private void init() {
        et_name=findViewById(R.id.tv_modinf_name);
        et_college=findViewById(R.id.tv_modinf_college);
        et_major=findViewById(R.id.tv_modinf_major);
        et_classes=findViewById(R.id.tv_modinf_classes);
        et_phone=findViewById(R.id.et_modinf_phone);
        btn_save=findViewById(R.id.btn_modinf_save);
        btn_back=findViewById(R.id.btn_modinf_back);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    private void setdata() {
        Intent intent=getIntent();
        objectId=intent.getStringExtra("obId");
        BmobQuery<MyUser> query = new BmobQuery<>();//查询数据
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(final List<MyUser> list, BmobException e) {
                if (e == null) {
                    et_name.setText(list.get(0).getName());
                    et_classes.setText(list.get(0).getClasses());
                    et_college.setText(list.get(0).getCollege());
                    et_major.setText(list.get(0).getMajor());
                    et_phone.setText(list.get(0).getMobilePhoneNumber());
                } else {
                    Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_modinf_save:
                MyUser myUser=new MyUser();
                myUser.setName(et_name.getText().toString());
                myUser.setCollege(et_college.getText().toString());
                myUser.setMajor(et_major.getText().toString());
                myUser.setClasses(et_classes.getText().toString());
                myUser.setMobilePhoneNumber(et_phone.getText().toString());
                myUser.update(objectId, new UpdateListener() {
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
            case R.id.btn_modinf_back:finish();break;
        }
    }
}
