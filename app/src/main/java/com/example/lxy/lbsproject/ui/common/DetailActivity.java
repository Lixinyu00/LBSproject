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
import com.example.lxy.lbsproject.data.model.Memo;
import com.example.lxy.lbsproject.data.model.Notice;
import com.example.lxy.lbsproject.data.model.Suggest;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class DetailActivity extends Activity implements View.OnClickListener {
    private static final int MEMO = 1;  //备忘录标识
    private static final int NOTICE = 2;  //公告
    private static final int NOTICEMAN = 3; //公告管理
    private static final int SUGGESTMAN = 4;   //意见反馈
    private static final int SUGGEST = 5;  //意见建议

    private int funType;
    private String obId;

    private Button btn_back;
    private Button btn_del;
    private Button btn_modify;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_content;


    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_detail);
        init();
        setlistener();
        setdata();
    }

    private void init() {
        btn_back = findViewById(R.id.btn_detail_back);
        btn_del = findViewById(R.id.btn_detail_del);
        btn_modify = findViewById(R.id.btn_detail_modify);
        tv_title = findViewById(R.id.tv_detail_title);
        tv_time = findViewById(R.id.tv_detail_time);
        tv_content = findViewById(R.id.tv_detail_content);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
    }

    private void setdata() {
        Intent intent = getIntent();
        funType = intent.getIntExtra("funType", 0);
        obId = intent.getStringExtra("obId");
        switch (funType) {
            case MEMO:
                BmobQuery<Memo> query1 = new BmobQuery<>();//查询数据
                query1.addWhereEqualTo("objectId", obId);
                query1.findObjects(new FindListener<Memo>() {
                    @Override
                    public void done(final List<Memo> list, BmobException e) {
                        if (e == null) {
                            tv_title.setText("标题:  " + list.get(0).getTitle());
                            tv_time.setText("提醒时间:  " + list.get(0).getDay() + " " + list.get(0).getTime());
                            tv_content.setText("内容:  " + list.get(0).getContent());
                        } else {
                            Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case NOTICE:
                btn_del.setVisibility(View.GONE);
                btn_modify.setVisibility(View.GONE);
                BmobQuery<Notice> query2 = new BmobQuery<>();//查询数据
                query2.addWhereEqualTo("objectId", obId);
                query2.findObjects(new FindListener<Notice>() {
                    @Override
                    public void done(final List<Notice> list, BmobException e) {
                        if (e == null) {
                            tv_title.setText("标题:  " + list.get(0).getTitle());
                            tv_time.setText("时间:  " + list.get(0).getUpdatedAt());
                            tv_content.setText("内容:  " + list.get(0).getContent());
                        } else {
                            Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case NOTICEMAN:
                BmobQuery<Notice> query3 = new BmobQuery<>();//查询数据
                query3.addWhereEqualTo("objectId", obId);
                query3.findObjects(new FindListener<Notice>() {
                    @Override
                    public void done(final List<Notice> list, BmobException e) {
                        if (e == null) {
                            tv_title.setText("标题:  " + list.get(0).getTitle());
                            tv_time.setText("时间:  " + list.get(0).getUpdatedAt());
                            tv_content.setText("内容:  " + list.get(0).getContent());
                        } else {
                            Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case SUGGESTMAN:
                btn_del.setVisibility(View.GONE);
                btn_modify.setVisibility(View.GONE);
                BmobQuery<Suggest> query4 = new BmobQuery<>();//查询数据
                query4.addWhereEqualTo("objectId", obId);
                query4.findObjects(new FindListener<Suggest>() {
                    @Override
                    public void done(final List<Suggest> list, BmobException e) {
                        if (e == null) {
                            tv_title.setText("标题:  " + list.get(0).getTitle());
                            tv_time.setText("时间:  " + list.get(0).getUpdatedAt());
                            tv_content.setText("内容:  " + list.get(0).getContent());
                        } else {
                            Toast.makeText(context, "查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detail_back:
                this.finish();
                break;
            case R.id.btn_detail_del:
                if (funType == MEMO) {
                    Memo memo = new Memo();
                    memo.delete(obId, new UpdateListener() {
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
                } else if (funType == NOTICEMAN) {
                    Notice notice = new Notice();
                    notice.delete(obId, new UpdateListener() {
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
                }
                break;
            case R.id.btn_detail_modify:
                Intent intent = new Intent(this, ModifyActivity.class);
                intent.putExtra("obId", obId);
                intent.putExtra("funType", funType);
                this.startActivity(intent);
                finish();
                break;
        }
    }
}
