package com.example.shopling.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopling.R;

/**
 * Created by hasee on 2019/4/8.
 */

public class ClassifyItemFragment extends Fragment {
    private View view;
    private TextView textView;
    private String string;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_item_classify_item_0,container,false);
        initView();
        return view;
    }

    private void initView() {
        textView=view.findViewById(R.id.textView);
        textView.setText(getArguments().getString("string"));
    }
}
