package com.example.lxy.lbsproject.ui.manager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.CheckIn;

import java.util.List;

public class QueryCheckAdapter extends RecyclerView.Adapter<QueryCheckAdapter.CheckInviewholder> {

    private List<CheckIn> list;
    private int time;
    private int timeIn;
    private int timeOut;

    public void setData(List<CheckIn> list, int time) {
        this.list = list;
        this.time = time;
    }

    @NonNull
    @Override
    public CheckInviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_check, viewGroup, false);
        CheckInviewholder holder = new CheckInviewholder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInviewholder checkInviewholder, final int i) {
        checkInviewholder.tv_check_classes.setText(list.get(i).getClasses());
        checkInviewholder.tv_check_name.setText(list.get(i).getName());
        timeIn = getTime(list.get(i).getTimeIn());
        if (list.get(i).getTimeOut().equals("")) {
            checkInviewholder.tv_check_inf.setText("缺勤");
        } else {
            timeOut = getTime(list.get(i).getTimeOut());
            if (time >= timeIn && time + 60 <= timeOut) {
                checkInviewholder.tv_check_inf.setText("到课");
            } else {
                checkInviewholder.tv_check_inf.setText("缺勤");
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CheckInviewholder extends RecyclerView.ViewHolder {
        TextView tv_check_classes;
        TextView tv_check_name;
        TextView tv_check_inf;

        public CheckInviewholder(View itemView) {
            super(itemView);
            tv_check_classes = itemView.findViewById(R.id.tv_check_classes);
            tv_check_name = itemView.findViewById(R.id.tv_check_name);
            tv_check_inf = itemView.findViewById(R.id.tv_check_inf);
        }
    }

    private int getTime(String time) {
        int hour;
        int min;
        int mins;
        hour = Integer.valueOf(time.substring(0, 2));
        min = Integer.valueOf(time.substring(3, 5));
        mins = hour * 60 + min;
        return mins;
    }
}
