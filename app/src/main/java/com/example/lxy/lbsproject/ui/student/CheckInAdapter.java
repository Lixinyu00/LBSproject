package com.example.lxy.lbsproject.ui.student;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lxy.lbsproject.R;

import java.util.List;

public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.CheckInviewholder>{

    private OnItemClickListener listener;

    private List<ScanResult> list;

    public void setData(List<ScanResult> list) {
        this.list = list;
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
        int wifi_level;
        checkInviewholder.tv_wifi_id.setText(list.get(i).SSID+" ");
        wifi_level= WifiManager.calculateSignalLevel(list.get(i).level,5);
        checkInviewholder.tv_level.setText(wifi_level+"");
        checkInviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CheckInviewholder extends RecyclerView.ViewHolder {
        TextView tv_wifi_id;
        TextView tv_level;

        public CheckInviewholder(View itemView) {
            super(itemView);
            tv_wifi_id=itemView.findViewById(R.id.tv_wifi_id);
            tv_level=itemView.findViewById(R.id.tv_level);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onClick(View v, int position);
    }
}
