package com.example.lxy.lbsproject.ui.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lxy.lbsproject.R;
import com.example.lxy.lbsproject.data.model.MyUser;

import java.util.List;

public class MateAdapter extends RecyclerView.Adapter<MateAdapter.Mateviewholder> {

    private OnItemClickListener listener;
    private List<MyUser> list;

    public void setDate(List<MyUser> list){
        this.list=list;
    }

    @NonNull
    @Override
    public MateAdapter.Mateviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mate, viewGroup, false);
        Mateviewholder holder = new Mateviewholder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MateAdapter.Mateviewholder mateviewholder, final int i) {
            mateviewholder.tv_name.setText(list.get(i).getName());
            mateviewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Mateviewholder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public Mateviewholder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_mate_name);
        }
    }

    public void setOnItemClickListener(MateAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }
}
