package cn.hanzhuang42.mynews.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hanzhuang42.mynews.R;
import cn.hanzhuang42.mynews.adapter.RecyclerAdapter;
import cn.hanzhuang42.mynews.model.News;
import cn.hanzhuang42.mynews.util.DbUtility;

public class TabFragment extends Fragment {

    private List<News> newsList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private int type;

    public static Fragment newInstance(int pos) {
        TabFragment fragment = new TabFragment();
        fragment.type = pos;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerAdapter = new RecyclerAdapter(newsList);
        recyclerView.setAdapter(recyclerAdapter);
        refreshData();
    }

    public void refreshData() {
        refreshList(type);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void refreshList(int type){
        newsList.clear();
        newsList.addAll(DbUtility.query(type));
    }
}