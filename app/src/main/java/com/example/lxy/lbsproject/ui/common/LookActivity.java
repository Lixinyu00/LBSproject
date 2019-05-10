package com.example.lxy.lbsproject.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class LookActivity extends Activity implements View.OnClickListener {
    public static final int MEMO=1;  //备忘录标识
    public static final int NOTICE=2;  //公告
    public static final int NOTICEMAN=3; //公告管理
    public static final int SUGGESTMAN=4;   //意见反馈
    public static final int SUGGEST=5;  //意见建议

    private RecyclerView recyclerView;
    private Button btn_back;
    private Button btn_add;
    private TextView tv_title;

    private LookAdapter lookAdapter;

    private String userName;
    private int funType;
    private Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        init();
        setlistener();
        setdata();
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_main);
        btn_back = findViewById(R.id.btn_back);
        btn_add = findViewById(R.id.btn_add);
        tv_title = findViewById(R.id.tv_title);
    }

    private void setlistener() {
        btn_back.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    private void setdata() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        funType = intent.getIntExtra("funType",0);
        lookAdapter=new LookAdapter();
        switch (funType){
            case MEMO:
                tv_title.setText("备忘录");
                BmobQuery<Memo> query1=new BmobQuery<>();//查询数据
                query1.addWhereEqualTo("user",userName);
                query1.findObjects(new FindListener<Memo>() {
                    @Override
                    public void done(List<Memo> list, BmobException e) {
                        if (e==null){
                            lookAdapter.setMemo(list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(lookAdapter);
                        }else {
                            Toast.makeText(context,"查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case NOTICE:
                tv_title.setText("公告");
                btn_add.setVisibility(View.GONE);
                BmobQuery<Notice> query2=new BmobQuery<>();//查询数据
                query2.addWhereEqualTo("user","2");
                query2.findObjects(new FindListener<Notice>() {
                    @Override
                    public void done(List<Notice> list, BmobException e) {
                        if (e==null){
                            lookAdapter.setNotice(list);
                            Log.e("a11", "onBindViewHolder: "+list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(lookAdapter);
                        }else {
                            Toast.makeText(context,"查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case NOTICEMAN:
                tv_title.setText("公告管理");
                BmobQuery<Notice> query3=new BmobQuery<>();//查询数据
                query3.addWhereEqualTo("user","2");
                query3.findObjects(new FindListener<Notice>() {
                    @Override
                    public void done(List<Notice> list, BmobException e) {
                        if (e==null){
                            lookAdapter.setNotice(list);
                            Log.e("a11", "onBindViewHolder: "+list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(lookAdapter);
                        }else {
                            Toast.makeText(context,"查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case SUGGESTMAN:
                tv_title.setText("建议反馈");
                btn_add.setVisibility(View.GONE);
                BmobQuery<Suggest> query4=new BmobQuery<>();//查询数据
                query4.addWhereNotEqualTo("user","0");
                query4.findObjects(new FindListener<Suggest>() {
                    @Override
                    public void done(List<Suggest> list, BmobException e) {
                        if (e==null){
                            lookAdapter.setSuggest(list);
                            Log.e("a11", "onBindViewHolder: "+list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(lookAdapter);
                        }else {
                            Toast.makeText(context,"查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_add:
                Intent intent=new Intent(this,AddActivity.class);
                intent.putExtra("userName",userName);
                if (funType==MEMO){
                    intent.putExtra("funType",MEMO);
                }else {
                    intent.putExtra("funType",NOTICEMAN);
                }
                this.startActivity(intent);
                break;
        }
    }
}
