package com.readingstudytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readingstudytest.HomeFragment;
import com.readingstudytest.MainActivity;
import com.readingstudytest.R;
import com.readingstudytest.TodoFragment;
import com.readingstudytest.bean.ArticleBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoBodyAdapter extends RecyclerView.Adapter<TodoBodyAdapter.ViewHolder> {
    private List<ArticleBean.ArticleDetailBean> mListBodyContent;
    private View localView;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout itemContent;

        TextView itemAuthorLeft;
        TextView itemAuthorRight;

        TextView itemTitle;

        LinearLayout itemTags;

        public ViewHolder(View view){
            super(view);
            itemContent = (LinearLayout) view.findViewById(R.id.ll_adapter_todo_body);

            itemAuthorLeft = (TextView) view.findViewById(R.id.tv_todo_body_item_author_left);
            itemAuthorRight = (TextView) view.findViewById(R.id.tv_todo_body_item_author_right);

            itemTitle = (TextView) view.findViewById(R.id.tv_todo_body_item_title);

            itemTags = (LinearLayout) view.findViewById(R.id.ll_todo_body_item_tags);
        }
    }

    public TodoBodyAdapter(List<ArticleBean.ArticleDetailBean> listBodyContent){
        mListBodyContent = listBodyContent;
    }
    @NonNull
    @Override
    public TodoBodyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        localView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_body_item, parent, false);
        TodoBodyAdapter.ViewHolder localHolder = new TodoBodyAdapter.ViewHolder(localView);
        setListener(localHolder);
        return localHolder;
    }

    private void setListener(ViewHolder holder){
        holder.itemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.jumpContentShowActivity(MainActivity.TAG_REQUESTURL,
                        mListBodyContent.get(holder.getAdapterPosition()).getLink());
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull TodoBodyAdapter.ViewHolder holder, int position) {
        ArticleBean.ArticleDetailBean listContentPersonBean = mListBodyContent.get(position);

        holder.itemAuthorLeft.setText(listContentPersonBean.getAuthor());
        holder.itemAuthorRight.setText(listContentPersonBean.getAuthor());

        holder.itemTitle.setText(listContentPersonBean.getTitle());

        if(listContentPersonBean.getTags() == null || listContentPersonBean.getTags().size() == 0){
            holder.itemTags.setVisibility(View.GONE);
        }else{
            holder.itemTags.setVisibility(View.VISIBLE);
            //后续更新该部分布局
        }
    }

    @Override
    public int getItemCount() {
        return mListBodyContent.size();
    }

}
