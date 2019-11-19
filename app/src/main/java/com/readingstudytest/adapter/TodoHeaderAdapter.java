package com.readingstudytest.adapter;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.readingstudytest.R;
import com.readingstudytest.TodoFragment;
import com.readingstudytest.bean.ItemNameAndIdBean;
import com.readingstudytest.bean.WxArticleBean;
import com.readingstudytest.home.InfoFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoHeaderAdapter extends RecyclerView.Adapter<TodoHeaderAdapter.ViewHolder> {
    private List<ItemNameAndIdBean> mTodoItemList;
    private View localView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView todoItemHeader;

        public ViewHolder(View view){
            super(view);
            todoItemHeader = (TextView) view.findViewById(R.id.tv_todo_header_item);
        }
    }

    public TodoHeaderAdapter(List<ItemNameAndIdBean> todoItemList){
        mTodoItemList = todoItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        localView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_todo_header_item, parent, false);
        ViewHolder localHolder = new ViewHolder(localView);
        localHolder.todoItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = localHolder.getAdapterPosition();
                String itemHeader = mTodoItemList.get(position).getName();
                Toast.makeText(localView.getContext(), "you clicked view " + itemHeader,
                        Toast.LENGTH_SHORT).show();

                //使用handler机制进行消息传输
                Message message = new Message();
                message.arg1 = mTodoItemList.get(position).getId();
                TodoFragment.handlerInfo.sendMessage(message);
            }
        });
        return localHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHeaderAdapter.ViewHolder holder, int position){
        String itemName = mTodoItemList.get(position).getName();
        holder.todoItemHeader.setText(itemName);
    }

    @Override
    public int getItemCount(){
        return mTodoItemList.size();
    }
}
