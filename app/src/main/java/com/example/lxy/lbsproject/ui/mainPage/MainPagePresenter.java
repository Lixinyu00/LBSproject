package com.example.lxy.lbsproject.ui.mainPage;

import android.content.Intent;

import com.example.lxy.lbsproject.ui.manager.ManageStudent;
import com.example.lxy.lbsproject.ui.manager.QueryCheckInf;
import com.example.lxy.lbsproject.ui.manager.QuerySuggest;
import com.example.lxy.lbsproject.ui.manager.ReleaseNotice;
import com.example.lxy.lbsproject.ui.student.CheckIn;
import com.example.lxy.lbsproject.ui.student.Memorandum;
import com.example.lxy.lbsproject.ui.student.QueryNotice;
import com.example.lxy.lbsproject.ui.student.ReleaseSuggest;



public class MainPagePresenter implements MainPageContract.Presenter {

    private MainPageContract.View view;

    private String userName;
    private int userType;

    public MainPagePresenter(MainPageContract.View view) {
        this.view = view;
        view.setPresenter(this);
        checkUserType();
    }

    private void checkUserType() {
        Intent intent = view.getMyIntent();
        userName = intent.getStringExtra("username");
        userType = intent.getIntExtra("userType", 0);
        if (userType == 0) {
            view.setText("考勤", "备忘录","公告","建议意见");
        } else if (userType == 1) {
            view.setText("学生管理","公告管理","查询到课率","意见反馈");
        }

    }

    @Override
    public void fun1() {
        if (userType == 0) {
            Intent intent=new Intent(view.getContext(), CheckIn.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        } else if (userType == 1) {
            Intent intent=new Intent(view.getContext(), ManageStudent.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public void fun2() {
        if (userType == 0) {
            Intent intent=new Intent(view.getContext(), Memorandum.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        } else if (userType == 1) {
            Intent intent=new Intent(view.getContext(), ReleaseNotice.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public void fun3() {
        if (userType == 0) {
            Intent intent=new Intent(view.getContext(), QueryNotice.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        } else if (userType == 1) {
            Intent intent=new Intent(view.getContext(), QueryCheckInf.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public void fun4() {
        if (userType == 0) {
            Intent intent=new Intent(view.getContext(), ReleaseSuggest.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        } else if (userType == 1) {
            Intent intent=new Intent(view.getContext(), QuerySuggest.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        }
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


}
