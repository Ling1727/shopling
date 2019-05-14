package com.example.shopling.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.example.shopling.tool.StatusStyle;


/**
 * Created by hasee on 2019/5/10.
 */

public class BaseAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusStyle.setStatusStyle(this,true);
    }
}
