package com.readingstudytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.readingstudytest.R;
import com.readingstudytest.bean.ArticleBean;

import java.util.List;

public class HomeBodyAdapter extends RecyclerView.Adapter<HomeBodyAdapter.ViewHolder> {

    private List<ArticleBean.ArticleDetailBean> mListContentPerson;
    private HomeBodyAdapter.ViewHolder localHolder;
    private View localView;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemAuthor;
        TextView itemChapter;

        TextView itemTitle;
        ImageView itemImage;

        LinearLayout itemTags;
        ImageView itemCollected;
        ImageView itemCollectNormal;
        TextView itemTime;

        public ViewHolder(View view){
            super(view);
            itemAuthor = (TextView) view.findViewById(R.id.tv_home_android_author);
            itemChapter = (TextView) view.findViewById(R.id.tv_home_android_chapter);

            itemTitle = (TextView) view.findViewById(R.id.tv_home_android_title);
            itemImage = (ImageView) view.findViewById(R.id.iv_home_android_pic);

            itemTags = (LinearLayout) view.findViewById(R.id.ll_home_android_tags);
            itemCollected = (ImageView) view.findViewById(R.id.iv_home_android_collected);
            itemCollectNormal = (ImageView) view.findViewById(R.id.iv_home_android_collect_normal);
            itemTime = (TextView) view.findViewById(R.id.tv_home_android_time);
        }
    }

    public HomeBodyAdapter(List<ArticleBean.ArticleDetailBean> listContentPersonBeans){
        mListContentPerson = listContentPersonBeans;
    }

    @Override
    public HomeBodyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        localView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_android_item, parent, false);
        localHolder = new HomeBodyAdapter.ViewHolder(localView);
        return localHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBodyAdapter.ViewHolder holder, int position) {
        ArticleBean.ArticleDetailBean listContentPersonBean = mListContentPerson.get(position);

        holder.itemAuthor.setText(listContentPersonBean.getAuthor());
        String chapterContent = listContentPersonBean.getSuperChapterName() + " / " +
                listContentPersonBean.getChapterName();
        holder.itemChapter.setText(chapterContent);

        holder.itemTitle.setText(listContentPersonBean.getTitle());
        if(listContentPersonBean.getEnvelopePic() == null ||
                listContentPersonBean.getEnvelopePic().equals("")){
            holder.itemImage.setVisibility(View.GONE);
        }else{
            holder.itemImage.setVisibility(View.VISIBLE);
            Glide.with(localView.getContext())
                    .load(listContentPersonBean.getEnvelopePic())
                    .preload(70, 70);
        }


        if(listContentPersonBean.getTags() == null){
            holder.itemTags.setVisibility(View.GONE);
        }else{
            holder.itemTags.setVisibility(View.VISIBLE);
            //后续更新该部分布局
        }
        if(listContentPersonBean.getCollect()){
            holder.itemCollected.setVisibility(View.VISIBLE);
            holder.itemCollectNormal.setVisibility(View.GONE);
        }else{
            holder.itemCollected.setVisibility(View.GONE);
            holder.itemCollectNormal.setVisibility(View.VISIBLE);
        }
        holder.itemTime.setText(listContentPersonBean.getNiceDate());
    }

    @Override
    public int getItemCount() {
        return mListContentPerson.size();
    }
}
