package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends FragmentActivity {
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        list = new ArrayList<Fragment>();
        list.add(new Fragment1());
        list.add(new Fragment2());
        list.add(new Fragment3());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
//        for (int i = 0; i <= 30; i++) {
//            list.add("第" + i);
//        }
//
//        ListView mListView = (ListView) findViewById(R.id.lv);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                SecondActivity.this, android.R.layout.simple_list_item_1, list);
//        mListView.setAdapter(adapter);
//        mListView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                startActivity(new Intent(getApplicationContext(),
//                        ThreeActivity.class));
//            }
//        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
