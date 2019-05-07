package com.example.lxy.lbsproject.ui.register;

import android.content.Context;
import android.os.Bundle;

import com.example.lxy.lbsproject.ui.BasePresenter;
import com.example.lxy.lbsproject.ui.BaseView;


/**
 * Created by LXY on 2018/5/30.
 */

public class RegisterContract {

    interface View extends BaseView<Presenter> {
        Context getContext();
        void finishActivity();
    }

    interface Presenter extends BasePresenter {
        void register(Bundle bundle);
    }
}

