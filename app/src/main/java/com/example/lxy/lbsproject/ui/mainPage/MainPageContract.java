package com.example.lxy.lbsproject.ui.mainPage;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;

import com.example.lxy.lbsproject.ui.BasePresenter;
import com.example.lxy.lbsproject.ui.BaseView;


public class MainPageContract {
    interface View extends BaseView<Presenter> {
        Intent getMyIntent();
        Context getContext();
        void finishActivity();
        void setText( String t1, String t2,String t3, String t4);
        FragmentManager getMyFragmentManager();
    }
    interface Presenter extends BasePresenter {
        void fun1();
        void fun2();
        void fun3();
        void fun4();
    }
}
