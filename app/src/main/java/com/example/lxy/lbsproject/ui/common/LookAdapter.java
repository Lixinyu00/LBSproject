package com.example.lxy.lbsproject.ui.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.Memo;
import com.example.lxy.lbsproject.data.model.Notice;
import com.example.lxy.lbsproject.data.model.Suggest;

import java.util.List;

public class LookAdapter extends RecyclerView.Adapter<LookAdapter.Memoviewholder> {

    private List<Memo> memoList;
    private List<Notice> noticeList;
    private List<Suggest> suggestList;

    private int type;

    public void setMemo(List<Memo> list) {
        type = 1;
        memoList = list;
    }

    public void setNotice(List<Notice> list) {
        type = 2;
        noticeList = list;
    }

    public void setSuggest(List<Suggest> list) {
        type = 3;
        suggestList = list;
    }

    @NonNull
    @Override
    public LookAdapter.Memoviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler, viewGroup, false);
        Memoviewholder holder = new Memoviewholder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LookAdapter.Memoviewholder checkInviewholder, int i) {
        switch (type) {
            case 1:
                checkInviewholder.tv_content.setText(memoList.get(i).getTitle());
                checkInviewholder.tv_time.setText(memoList.get(i).getTime());
                checkInviewholder.tv_day.setText(memoList.get(i).getDay());
                break;
            case 2:
                checkInviewholder.tv_content.setText(noticeList.get(i).getTitle());
                checkInviewholder.tv_time.setText(noticeList.get(i).getUpdatedAt().substring(0,10));
                checkInviewholder.tv_day.setText(noticeList.get(i).getUpdatedAt().substring(11,19));
                Log.e("a11", "onBindViewHolder: "+noticeList);
                break;
            case 3:
                checkInviewholder.tv_content.setText(suggestList.get(i).getTitle());
                checkInviewholder.tv_time.setText(suggestList.get(i).getUpdatedAt().substring(0,10));
                checkInviewholder.tv_day.setText(suggestList.get(i).getUpdatedAt().substring(11,19));
                Log.e("a11", "onBindViewHolder: "+suggestList);
                break;
        }
        checkInviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        switch (type) {
            case 1:
                return memoList.size();
            case 2:
                return noticeList.size();
            case 3:
                return suggestList.size();
            default:
                return 0;
        }
    }

    public class Memoviewholder extends RecyclerView.ViewHolder {
        TextView tv_content;
        TextView tv_time;
        TextView tv_day;

        public Memoviewholder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_item_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_day = itemView.findViewById(R.id.tv_day);
        }
    }
}
