package com.example.lxy.lbsproject.ui.manager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.CheckIn;

import java.util.List;

public class CheckInfAdapter extends RecyclerView.Adapter<CheckInfAdapter.CheckInfviewholder> {

    private List<CheckIn> list;


    public void setData(List<CheckIn> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CheckInfviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycle_checkinf, viewGroup, false);
        CheckInfviewholder holder = new CheckInfviewholder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInfviewholder checkInfviewholder, int i) {
        Log.e("1111", "onBindViewHolder: " + i);
        checkInfviewholder.tv_day.setText(list.get(i).getDay());
        checkInfviewholder.tv_in.setText(list.get(i).getTimeIn());
        checkInfviewholder.tv_out.setText(list.get(i).getTimeOut());
    }

    @Override
    public int getItemCount() {
        Log.e("1111", "getItemCount: "+list.size() );
        return list.size();
    }

    public class CheckInfviewholder extends RecyclerView.ViewHolder {
        TextView tv_day;
        TextView tv_in;
        TextView tv_out;

        public CheckInfviewholder(@NonNull View itemView) {
            super(itemView);
            tv_day = itemView.findViewById(R.id.tv_checkinf_day);
            tv_in = itemView.findViewById(R.id.tv_checkinf_in);
            tv_out = itemView.findViewById(R.id.tv_checkinf_out);
        }
    }
}
