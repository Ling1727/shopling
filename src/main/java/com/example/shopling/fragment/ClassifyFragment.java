package com.example.shopling.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.example.shopling.R;
import com.example.shopling.adapter.LVClassifyAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hasee on 2019/3/28.
 */

public class ClassifyFragment extends Fragment {
    private View view;
    private ListView lvClassify;
    private static String[] strings;
    private LVClassifyAdapter lvClassifyAdapter;
    private RelativeLayout rlIfy;
    private Fragment currentFragment;
    private Map<String,Fragment> fragmentMap;
    private ClassifyItemFragment classifyItemFragment;
    private ClassifyItemRecommendFragment classifyItemRecommendFragment;
    private RelativeLayout toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_classify,container,false);
        initView();
        initData();
        initControl();
        return view;
    }

    static {
        strings=new String[]{"推荐分类","超市","国际名牌","奢侈品","海囤全球",
                "男装","女装","男鞋","女鞋","内衣配饰","箱包手袋","美妆护肤","钟表珠宝","手机数码","电脑办公","家用电器","食品生鲜","玩具乐器","计生情趣",
                "运动户外","家居厨具","生活旅行","图书文娱","汽车生活"};
    }

    private void initView() {
        lvClassify=view.findViewById(R.id.lvClassify);
        rlIfy=view.findViewById(R.id.rlIfy);
        toolbar=view.findViewById(R.id.toolbar);
    }

    private void initData() {
        lvClassifyAdapter=new LVClassifyAdapter(getActivity(),strings);
        fragmentMap=new HashMap<>();
        classifyItemRecommendFragment=new ClassifyItemRecommendFragment();
        fragmentMap.put(0+"",classifyItemRecommendFragment);
    }

    private void initControl() {
        lvClassify.setAdapter(lvClassifyAdapter);
        showFragment(fragmentMap.get(0+""));
        lvClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvClassifyAdapter.changeSelected(position);
                if(!fragmentMap.containsKey(position+"")){
                    ClassifyItemFragment classifyItemFragment=new ClassifyItemFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("string",strings[position]);
                    classifyItemFragment.setArguments(bundle);
                    fragmentMap.put(position+"",classifyItemFragment);
                }
                showFragment(fragmentMap.get(position+""));
            }
        });

    }

    public void showFragment(Fragment fragment){
        if(currentFragment!=fragment){
            FragmentTransaction transaction=getChildFragmentManager().beginTransaction();
            if(currentFragment!=null){
                transaction.hide(currentFragment);
            }
            currentFragment=fragment;
            if(fragment.isAdded()){
                transaction.show(fragment).commit();
            }else{
                transaction.add(R.id.rlIfy,fragment).show(fragment).commit();
            }
        }
    }

}
