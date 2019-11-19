package com.readingstudytest.adapter;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.readingstudytest.R;
import com.readingstudytest.bean.ProjectInfoBean;
import com.readingstudytest.home.InfoFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InfoHeaderAdapter extends RecyclerView.Adapter<InfoHeaderAdapter.ViewHolder>{

    private List<ProjectInfoBean> mInfoItemList;
    private View localView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView infoItemHeader;

        public ViewHolder(View view){
            super(view);
            infoItemHeader = (TextView) view.findViewById(R.id.info_header_item);
        }
    }

    public InfoHeaderAdapter(List<ProjectInfoBean> infoItemList){
        mInfoItemList = infoItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        localView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_info_header_item, parent, false);
        ViewHolder localHolder = new ViewHolder(localView);
        localHolder.infoItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = localHolder.getAdapterPosition();
                String itemHeader = mInfoItemList.get(position).getName();
                Toast.makeText(localView.getContext(), "you clicked view " + itemHeader,
                        Toast.LENGTH_SHORT).show();

                //使用handler机制进行消息传输
                Message message = new Message();
                message.arg1 = mInfoItemList.get(position).getId();
                InfoFragment.handlerInfo.sendMessage(message);
            }
        });
        return localHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        String itemName = mInfoItemList.get(position).getName();
        holder.infoItemHeader.setText(itemName);
    }

    @Override
    public int getItemCount(){
        return mInfoItemList.size();
    }
}
