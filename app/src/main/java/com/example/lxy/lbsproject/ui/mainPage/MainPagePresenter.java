package com.example.lxy.lbsproject.ui.mainPage;

import android.content.Intent;

import com.example.lxy.lbsproject.ui.manager.ManageStudent;
import com.example.lxy.lbsproject.ui.manager.QueryCheckInf;
import com.example.lxy.lbsproject.ui.student.CheckIn;
import com.example.lxy.lbsproject.ui.common.LookActivity;
import com.example.lxy.lbsproject.ui.student.MyInfActivity;


public class MainPagePresenter implements MainPageContract.Presenter {

    public static final int MEMO=1;  //备忘录标识
    public static final int NOTICE=2;  //公告
    public static final int NOTICEMAN=3; //公告管理
    public static final int SUGGESTMAN=4;   //意见反馈
    public static final int SUGGEST=5;  //意见建议

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
            view.setText("考勤", "备忘录","公告","我的");
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
            Intent intent=new Intent(view.getContext(), LookActivity.class);
            intent.putExtra("username",userName);
        if (userType == 0) {
            intent.putExtra("funType",MEMO);
        } else if (userType == 1) {
            intent.putExtra("funType",NOTICEMAN);
        }
            view.getContext().startActivity(intent);
    }

    @Override
    public void fun3() {
        if (userType == 0) {
            Intent intent=new Intent(view.getContext(), LookActivity.class);
            intent.putExtra("username",userName);
            intent.putExtra("funType",NOTICE);
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
            Intent intent=new Intent(view.getContext(), MyInfActivity.class);
            intent.putExtra("userType",userType);
            intent.putExtra("username",userName);
            view.getContext().startActivity(intent);
        } else if (userType == 1) {
            Intent intent=new Intent(view.getContext(), LookActivity.class);
            intent.putExtra("username",userName);
            intent.putExtra("funType",SUGGESTMAN);
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
