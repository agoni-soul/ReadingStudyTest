package com.readingstudytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readingstudytest.MainActivity;
import com.readingstudytest.R;
import com.readingstudytest.bean.GankBodyContentBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GankBodyAdapter extends RecyclerView.Adapter<GankBodyAdapter.ViewHolder> {
    private List<GankBodyContentBean.GankBodyDetailBean> mListBodyContent;
    private View localView;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout itemContent;

        TextView itemAuthor;
        TextView itemTitle;
        TextView itemTags;
        TextView itemPublishTime;

        public ViewHolder(View view){
            super(view);
            itemContent = (LinearLayout) view.findViewById(R.id.ll_adapter_gank_body);

            itemAuthor = (TextView) view.findViewById(R.id.tv_gank_body_item_author);
            itemTitle = (TextView) view.findViewById(R.id.tv_gank_body_item_title);
            itemTags = (TextView) view.findViewById(R.id.tv_gank_body_item_tags);
            itemPublishTime = (TextView) view.findViewById(R.id.tv_gank_body_item_time);
        }
    }

    public GankBodyAdapter(List<GankBodyContentBean.GankBodyDetailBean> listBodyContent){
        mListBodyContent = listBodyContent;
    }
    @NonNull
    @Override
    public GankBodyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        localView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gank_body_item, parent, false);
        GankBodyAdapter.ViewHolder localHolder = new GankBodyAdapter.ViewHolder(localView);
        setListener(localHolder);
        return localHolder;
    }

    private void setListener(ViewHolder holder){
        holder.itemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.jumpContentShowActivity(MainActivity.TAG_REQUESTURL,
                        mListBodyContent.get(holder.getAdapterPosition()).getUrl());
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull GankBodyAdapter.ViewHolder holder, int position) {
        GankBodyContentBean.GankBodyDetailBean itemBodyContent = mListBodyContent.get(position);

        holder.itemAuthor.setText(itemBodyContent.getWho());
        holder.itemTitle.setText(itemBodyContent.getDesc());
        holder.itemPublishTime.setText(itemBodyContent.getPublishedAt().toString().substring(0, 10));
        holder.itemTags.setText(itemBodyContent.getType() + "/" + itemBodyContent.getSource());
    }

    @Override
    public int getItemCount() {
        return mListBodyContent.size();
    }
}
