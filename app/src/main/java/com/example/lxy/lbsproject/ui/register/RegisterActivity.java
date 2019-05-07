package com.example.lxy.lbsproject.ui.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lxy.lbsproject.R;


public class RegisterActivity extends Activity implements RegisterContract.View, View.OnClickListener {

    private RegisterContract.Presenter presenter;

    private TextView register_id;
    private TextView register_psd;
    private TextView register_name;
    private TextView register_phone;
    private TextView register_college;
    private TextView register_major;
    private TextView register_classes;
    private Button btn_register;

    private int userType = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        new RegisterPresenter(this);
    }

    @Override
    public void init() {
        register_id=findViewById(R.id.register_id);
        register_psd=findViewById(R.id.register_psd);
        register_name=findViewById(R.id.register_name);
        register_phone=findViewById(R.id.register_phone);
        register_college=findViewById(R.id.register_college);
        register_major=findViewById(R.id.register_major);
        register_classes=findViewById(R.id.register_classes);
        btn_register=findViewById(R.id.btn_register);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter=presenter;
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        bundle.putString("id",register_id.getText().toString());
        bundle.putString("psd",register_psd.getText().toString());
        bundle.putString("name",register_name.getText().toString());
        bundle.putString("phone",register_phone.getText().toString());
        bundle.putString("college",register_college.getText().toString());
        bundle.putString("major",register_major.getText().toString());
        bundle.putString("classes",register_classes.getText().toString());
        bundle.putInt("type",userType);
        presenter.register(bundle);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishActivity() {
        this.finish();
    }
}
