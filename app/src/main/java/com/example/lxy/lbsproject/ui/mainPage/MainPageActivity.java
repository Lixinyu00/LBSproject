package com.example.lxy.lbsproject.ui.mainPage;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;

import java.util.Timer;
import java.util.TimerTask;


public class MainPageActivity extends Activity implements MainPageContract.View, View.OnClickListener {

    private MainPageContract.Presenter presenter;

    private Button btn_main_1;
    private Button btn_main_2;
    private Button btn_main_3;
    private Button btn_main_4;

    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();
        new MainPagePresenter(this);
    }

    @Override
    public void init() {
        btn_main_1 = findViewById(R.id.btn_main_1);
        btn_main_2 = findViewById(R.id.btn_main_2);
        btn_main_3 = findViewById(R.id.btn_main_3);
        btn_main_4 = findViewById(R.id.btn_main_4);
    }

    @Override
    public void setPresenter(MainPageContract.Presenter presenter) {
        this.presenter = presenter;
        btn_main_1.setOnClickListener(this);
        btn_main_2.setOnClickListener(this);
        btn_main_3.setOnClickListener(this);
        btn_main_4.setOnClickListener(this);
    }

    @Override
    public Intent getMyIntent() {
        return getIntent();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void setText(String t1, String t2, String t3, String t4) {
        btn_main_1.setText(t1);
        btn_main_2.setText(t2);
        btn_main_3.setText(t3);
        btn_main_4.setText(t4);
    }


    @Override
    public FragmentManager getMyFragmentManager() {
        return getFragmentManager();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_1:
                presenter.fun1();
                break;
            case R.id.btn_main_2:
                presenter.fun2();
                break;
            case R.id.btn_main_3:
                presenter.fun3();
                break;
            case R.id.btn_main_4:
                presenter.fun4();
                break;
        }
    }

    @Override
    /**
     * 双击返回键退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
