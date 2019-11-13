package com.readingstudytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.readingstudytest.R;
import com.readingstudytest.bean.HomeAndroidDatasBean;
import com.readingstudytest.bean.HomeAndroidDatasTagsBean;
import com.readingstudytest.bean.ListContentPersonBean;

import java.util.List;

public class HomeAndroidContentAdapter extends RecyclerView.Adapter<HomeAndroidContentAdapter.ViewHolder> {

    private List<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>> mListContentPerson;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemAuthor;
        TextView itemChapter;

        TextView itemTitle;
        ImageView itemImage;

        LinearLayout itemTags;
        ImageView itemCollectted;
        ImageView itemCollectNormal;
        TextView itemTime;

        public ViewHolder(View view){
            super(view);
            itemAuthor = (TextView) view.findViewById(R.id.tv_home_android_author);
            itemChapter = (TextView) view.findViewById(R.id.tv_home_android_chapter);

            itemTitle = (TextView) view.findViewById(R.id.tv_home_android_title);
            itemImage = (ImageView) view.findViewById(R.id.iv_home_android_pic);

            itemTags = (LinearLayout) view.findViewById(R.id.ll_home_android_tags);
            itemCollectted = (ImageView) view.findViewById(R.id.iv_home_android_collected);
            itemCollectNormal = (ImageView) view.findViewById(R.id.iv_home_android_collect_normal);
            itemTime = (TextView) view.findViewById(R.id.tv_home_android_time);
        }
    }

    public HomeAndroidContentAdapter(List<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>> listContentPersonBeans){
        mListContentPerson = listContentPersonBeans;
    }

    @Override
    public HomeAndroidContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_android_item, parent, false);
        HomeAndroidContentAdapter.ViewHolder holder = new HomeAndroidContentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeAndroidContentAdapter.ViewHolder holder, int position) {
        HomeAndroidDatasBean listContentPersonBean = mListContentPerson.get(position);

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
            //后续补充下载图片的地址
//            holder.itemImage.setImageResource(listContentPersonBean.getChapterid());
        }


        if(listContentPersonBean.getTags() == null){
            holder.itemTags.setVisibility(View.GONE);
        }else{
            holder.itemTags.setVisibility(View.VISIBLE);
            //后续更新该部分布局
        }
        if(listContentPersonBean.getCollect()){
            holder.itemCollectted.setVisibility(View.VISIBLE);
            holder.itemCollectNormal.setVisibility(View.GONE);
        }else{
            holder.itemCollectted.setVisibility(View.GONE);
            holder.itemCollectNormal.setVisibility(View.VISIBLE);
        }
        holder.itemTime.setText(listContentPersonBean.getNiceDate());
    }

    @Override
    public int getItemCount() {
        return mListContentPerson.size();
    }
}
