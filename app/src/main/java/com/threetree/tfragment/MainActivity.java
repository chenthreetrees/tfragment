package com.threetree.tfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.threetree.recycler.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private APagingViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView()
    {
        TextView menuA = (TextView)findViewById(R.id.menu_a);
        TextView menuB = (TextView)findViewById(R.id.menu_b);
        TextView menuC = (TextView)findViewById(R.id.menu_c);
        TextView menuD = (TextView)findViewById(R.id.menu_d);
        menuA.setOnClickListener(this);
        menuB.setOnClickListener(this);
        menuC.setOnClickListener(this);
        menuD.setOnClickListener(this);

        mViewPager = (APagingViewPager) findViewById(R.id.viewpager);
        mViewPager.setPagingEnable(true);
        final List<BaseFragment> lists = new ArrayList<BaseFragment>();
        lists.add(new MainAFragment());
        lists.add(new MainBFragment());
        lists.add(new MainCFragment());
        lists.add(new MainDFragment());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return lists.get(position);
            }

            @Override
            public int getCount()
            {
                return lists.size();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_a:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.menu_b:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.menu_c:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.menu_d:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
}
