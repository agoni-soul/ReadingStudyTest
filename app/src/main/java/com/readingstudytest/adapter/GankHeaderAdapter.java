package com.readingstudytest.adapter;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.readingstudytest.GankFragment;
import com.readingstudytest.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GankHeaderAdapter extends RecyclerView.Adapter<GankHeaderAdapter.ViewHolder> {
    private List<String> mGankItemList;
    private View localView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView gankItemHeader;

        public ViewHolder(View view){
            super(view);
            gankItemHeader = (TextView) view.findViewById(R.id.tv_gank_header_item);
        }
    }

    public GankHeaderAdapter(List<String> gankItemList){
        mGankItemList = gankItemList;
    }

    @Override
    public GankHeaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        localView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gank_header_item, parent, false);
        GankHeaderAdapter.ViewHolder localHolder = new GankHeaderAdapter.ViewHolder(localView);
        localHolder.gankItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = localHolder.getAdapterPosition();
                String itemHeader = mGankItemList.get(position);
                Toast.makeText(localView.getContext(), "you clicked view " + itemHeader,
                        Toast.LENGTH_SHORT).show();

                //使用handler机制进行消息传输
                Message message = new Message();
                message.obj = mGankItemList.get(position);
                GankFragment.handlerGank.sendMessage(message);
            }
        });
        return localHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull GankHeaderAdapter.ViewHolder holder, int position){
        holder.gankItemHeader.setText(mGankItemList.get(position));
    }

    @Override
    public int getItemCount(){
        return mGankItemList.size();
    }

}
