package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xf on 2016/3/29 0029.
 */
public class Fragment1 extends Fragment {
    private View mView;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment1, container, false);
        initeData();
        return mView;
    }

    private void initeData() {
        list = new ArrayList<String>();
        for (int i = 0; i <= 30; i++) {
            list.add("第" + i);
        }

        ListView mListView = (ListView) mView.findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity(new Intent(getActivity(),
                        ThreeActivity.class));
            }
        });
    }

}
