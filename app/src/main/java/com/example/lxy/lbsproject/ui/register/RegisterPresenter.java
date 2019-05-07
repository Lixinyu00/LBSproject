package com.example.lxy.lbsproject.ui.register;

import android.os.Bundle;
import android.widget.Toast;

import com.example.lxy.lbsproject.data.model.MyUser;
import com.example.lxy.lbsproject.utils.Md5;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void register(Bundle bundle) {
        String id = bundle.getString("id");
        String psd = bundle.getString("psd");
        String name = bundle.getString("name");
        String phone = bundle.getString("phone");
        String college = bundle.getString("college");
        String major = bundle.getString("major");
        String classes = bundle.getString("classes");
        Integer type = bundle.getInt("type");
        if (id.equals("") || psd.equals("") || name.equals("") || phone.equals("")) {
            Toast.makeText(view.getContext(), "信息不全，请填写完整！", Toast.LENGTH_SHORT).show();

        } else {
            MyUser user = new MyUser();
            user.setUsername(id);
            user.setPassword(Md5.getmd5(psd));
            user.setName(name);
            user.setMobilePhoneNumber(phone);
            user.setCollege(college);
            user.setMajor(major);
            user.setClasses(classes);
            user.setUserType(type);
            user.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if (e == null) {
                        Toast.makeText(view.getContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                        view.finishActivity();
                    } else {
                        switch (e.getErrorCode()) {
                            case 202:
                                Toast.makeText(view.getContext(), "用户名已存在，注册失败！" , Toast.LENGTH_SHORT).show();
                                break;
                            case 209:
                                Toast.makeText(view.getContext(), "该手机已被注册，注册失败！" , Toast.LENGTH_SHORT).show();
                                break;
                            case 301:
                                Toast.makeText(view.getContext(), "请输入正确的手机号，注册失败！" , Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                    Toast.makeText(view.getContext(), "服务器开小差了，注册失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
