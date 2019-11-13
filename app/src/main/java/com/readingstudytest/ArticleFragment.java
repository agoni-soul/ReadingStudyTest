package com.readingstudytest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArticleFragment extends Fragment {
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.gank_layout, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.gank_textView);
        txt_content.setText("第二个Fragment");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        floatingActionButton.hide();

        Button button=(Button)getActivity().findViewById(R.id.gank_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "干货", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView(){
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_android_home_fragment);
    }
}
