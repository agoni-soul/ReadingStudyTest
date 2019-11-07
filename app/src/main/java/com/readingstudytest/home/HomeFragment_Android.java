package com.readingstudytest.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.readingstudytest.R;

public class HomeFragment_Android extends Fragment implements View.OnClickListener{

    private TextView dic_android_textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dic_layout_android, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dic_android_textView = (TextView) getActivity().findViewById(R.id.dic_android_textView);
        Button button=(Button)getActivity().findViewById(R.id.dic_android_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.dic_android_button:{
                dic_android_textView.setText("更新android");
                Toast.makeText(getActivity(), "android", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
}
