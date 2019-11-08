package com.readingstudytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.readingstudytest.R;

import java.util.List;

public class InfoHeaderAdapter extends RecyclerView.Adapter<InfoHeaderAdapter.ViewHolder> {

    private List<String> mInfoItemList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView infoItemHeader;

        public ViewHolder(View view){
            super(view);
            infoItemHeader = (TextView) view.findViewById(R.id.info_header_item);
        }
    }

    public InfoHeaderAdapter(List<String> infoItemList){
        mInfoItemList = infoItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewTyep){
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_info_header_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.infoItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String itemHeader = mInfoItemList.get(position);
                Toast.makeText(view.getContext(), "you clicked view " + itemHeader,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String itemName = mInfoItemList.get(position);
        holder.infoItemHeader.setText(itemName);
    }

    @Override
    public int getItemCount(){
        return mInfoItemList.size();
    }
}
