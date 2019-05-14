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
 * Created by hasee on 2019/4/24.
 */

public class FindFragmentPage1 extends Fragment {
    private View view;
    private TextView tvFind1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_find_page_1,container,false);
        initView();
        return view;
    }

    private void initView() {
        tvFind1=view.findViewById(R.id.tvFind1);
        tvFind1.setText(getArguments().getString("key"));
    }
}
