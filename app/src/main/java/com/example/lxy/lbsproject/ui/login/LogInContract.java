package com.example.lxy.lbsproject.ui.login;

import android.content.Context;

import com.example.lxy.lbsproject.ui.BasePresenter;
import com.example.lxy.lbsproject.ui.BaseView;



public class LogInContract {

    interface View extends BaseView<Presenter> {
        Context getContext();
        void finishActivity();
    }
    interface Presenter extends BasePresenter {
        void logIn(String account, String psd, int type);
        void register();
    }
}
