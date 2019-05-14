package com.example.shopling.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.shopling.tool.StatusStyle;

/**
 * Created by hasee on 2019/4/26.
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusStyle.setStatusStyle(this,true);
    }
}
