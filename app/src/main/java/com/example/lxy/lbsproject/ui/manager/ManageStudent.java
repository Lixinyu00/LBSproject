package com.example.lxy.lbsproject.ui.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.ui.common.MateViewActivity;

public class ManageStudent extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private EditText editText;
    private Button btn_back;
    private Button btn_query;

    private int queryType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        init();
        setlistener();
    }

    private void init() {
        radioGroup=findViewById(R.id.rg_manage);
        editText=findViewById(R.id.et_manage);
        btn_back=findViewById(R.id.btn_manage_back);
        btn_query=findViewById(R.id.btn_manage_query);
    }

    private void setlistener() {
        radioGroup.setOnCheckedChangeListener(this);
        btn_back.setOnClickListener(this);
        btn_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_manage_back:finish();break;
            case R.id.btn_manage_query:
                if (queryType != 0) {
                    if (editText.getText().toString().equals("")){
                        Toast.makeText(this,"请输入查找内容！" , Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent=new Intent(this, MateViewActivity.class);
                        intent.putExtra("param",editText.getText().toString());
                        intent.putExtra("queryType",queryType);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(this,"请选择查找方式！" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_name:queryType=1;break;
                case R.id.rb_classes:queryType=2;break;
                case R.id.rb_num:queryType=3;break;
            }
    }
}
