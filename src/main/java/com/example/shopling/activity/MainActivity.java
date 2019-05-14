package com.example.shopling.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.example.shopling.R;
import com.example.shopling.fragment.ClassifyFragment;
import com.example.shopling.fragment.FindFragment;
import com.example.shopling.fragment.HomeFragment;
import com.example.shopling.fragment.MeFragment;
import com.example.shopling.fragment.ShoppingFragment;
import com.example.shopling.tool.BottomNavigationViewHelper;


public class MainActivity extends BaseAppCompatActivity {
    private HomeFragment homeFragment;
    private ClassifyFragment classifyFragment;
    private FindFragment findFragment;
    private ShoppingFragment shoppingFragment;
    private MeFragment meFragment;
    private Fragment currentFragment=null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(homeFragment);
                    return true;
                case R.id.navigation_classify:
                    showFragment(classifyFragment);
                    return true;
                case R.id.navigation_find:
                    showFragment(findFragment);
                    return true;
                case R.id.navigation_shopping_trolley:
                    showFragment(shoppingFragment);
                    if(shoppingFragment.getTvShopSet().getText().toString().equals("完成")){
                        shoppingFragment.shopSet();
                    }
                    return true;
                case R.id.navigation_me:
                    showFragment(meFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeFragment=new HomeFragment();
        classifyFragment=new ClassifyFragment();
        findFragment=new FindFragment();
        shoppingFragment=new ShoppingFragment();
        meFragment=new MeFragment();
        navigation.setSelectedItemId(R.id.navigation_home);
    }


    public void showFragment(Fragment fragment){
        if(currentFragment!=fragment){
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            if(currentFragment!=null){
                transaction.hide(currentFragment);
            }
            currentFragment=fragment;
            if(fragment.isAdded()){
                transaction.show(fragment).commit();
            }else{
                transaction.add(R.id.rlRaplace,fragment).show(fragment).commitNow();
            }
        }
    }

}
