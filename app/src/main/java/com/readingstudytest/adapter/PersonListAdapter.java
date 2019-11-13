package com.readingstudytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.readingstudytest.R;
import com.readingstudytest.bean.ListContentPersonBean;

import java.util.List;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private List<ListContentPersonBean> mListContentPerson;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemIcon;
        TextView itemName;

        public ViewHolder(View view){
            super(view);
            itemIcon = (ImageView) view.findViewById(R.id.item_icon);
            itemName = (TextView) view.findViewById(R.id.item_name);
        }
    }

    public PersonListAdapter(List<ListContentPersonBean> listContentPersonBeans){
        mListContentPerson = listContentPersonBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListContentPersonBean listContentPersonBean = mListContentPerson.get(position);
        holder.itemIcon.setImageResource(listContentPersonBean.getItemImageSrc());
        holder.itemName.setText(listContentPersonBean.getItemName());
    }

    @Override
    public int getItemCount() {
        return mListContentPerson.size();
    }
}
