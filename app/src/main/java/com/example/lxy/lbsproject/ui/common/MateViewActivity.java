package com.example.lxy.lbsproject.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MateViewActivity extends Activity implements View.OnClickListener {
    private final int NAME=1;
    private final int CLASS=2;
    private final int NUM=3;
    private final int MATE=4;

    private RecyclerView recyclerView;
    private MateAdapter mateAdapter;
    private Button btn_back;


    private String param;
    private int type;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mateview);
        init();
        setdata();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setdata();
    }

    private void init() {
        recyclerView=findViewById(R.id.rv_mate);
        btn_back=findViewById(R.id.btn_mateview_back);
        btn_back.setOnClickListener(this);
    }


    private void setdata() {
        Intent intent=getIntent();
        param=intent.getStringExtra("param");
        type=intent.getIntExtra("queryType",0);
        mateAdapter=new MateAdapter();
        switch (type){
            case NAME:
                BmobQuery<MyUser> query1=new BmobQuery<>();//查询数据
                query1.addWhereEqualTo("name",param);
                query1.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(final List<MyUser> list, BmobException e) {
                        if (e==null){
                            mateAdapter.setDate(list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(mateAdapter);
                            mateAdapter.setOnItemClickListener(new MateAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent intent1=new Intent(context,MyInfActivity.class);
                                    intent1.putExtra("userName",list.get(position).getUsername());
                                    intent1.putExtra("modType",1);
                                    intent1.putExtra("delType",1);
                                    context.startActivity(intent1);
                                }
                            });
                        }else {
                            Toast.makeText(context,"查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case CLASS:
                BmobQuery<MyUser> query2=new BmobQuery<>();//查询数据
                query2.addWhereEqualTo("classes",param);
                query2.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(final List<MyUser> list, BmobException e) {
                        if (e==null){
                            mateAdapter.setDate(list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(mateAdapter);
                            mateAdapter.setOnItemClickListener(new MateAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent intent1=new Intent(context,MyInfActivity.class);
                                    intent1.putExtra("userName",list.get(position).getUsername());
                                    intent1.putExtra("modType",1);
                                    intent1.putExtra("delType",1);
                                    context.startActivity(intent1);
                                }
                            });
                        }else {
                            Toast.makeText(context,"查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case NUM:
                BmobQuery<MyUser> query3=new BmobQuery<>();//查询数据
                query3.addWhereEqualTo("username",param);
                query3.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(final List<MyUser> list, BmobException e) {
                        if (e==null){
                            mateAdapter.setDate(list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(mateAdapter);
                            mateAdapter.setOnItemClickListener(new MateAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent intent1=new Intent(context,MyInfActivity.class);
                                    intent1.putExtra("userName",list.get(position).getUsername());
                                    intent1.putExtra("modType",1);
                                    intent1.putExtra("delType",1);
                                    context.startActivity(intent1);
                                }
                            });
                        }else {
                            Toast.makeText(context,"查询失败！" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case MATE:
                BmobQuery<MyUser> query4=new BmobQuery<>();//查询数据
                query4.addWhereEqualTo("classes",param);
                query4.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(final List<MyUser> list, BmobException e) {
                        if (e==null){
                            mateAdapter.setDate(list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(mateAdapter);
                            mateAdapter.setOnItemClickListener(new MateAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent intent1=new Intent(context,MyInfActivity.class);
                                    intent1.putExtra("userName",list.get(position).getUsername()+"");
                                    intent1.putExtra("modType",0);
                                    intent1.putExtra("delType",0);
                                    context.startActivity(intent1);
                                }
                            });
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
        finish();
    }
}
