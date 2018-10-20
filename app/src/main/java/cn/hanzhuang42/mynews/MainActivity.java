package cn.hanzhuang42.mynews;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import cn.hanzhuang42.mynews.fragments.TabFragment;
import cn.hanzhuang42.mynews.model.News;
import cn.hanzhuang42.mynews.model.NewsResult;
import cn.hanzhuang42.mynews.view.VerticalSwipeRefreshLayout;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final static int CategoryNum = 5;
    private String[] mTabTitles  = {"显示器","音箱","移动业务","又是音箱","还是音箱"};
    private Fragment[] mFragmentArrays = new Fragment[CategoryNum];
    private VerticalSwipeRefreshLayout swipeRefreshLayout;

    TabLayout tabLayout;
    ViewPager viewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        int t = R.id.swipe_refresh;
        swipeRefreshLayout = findViewById(t);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });
        initData();
        initView();
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://mingke.veervr.tv:1920/test")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseDate = response.body().string();
                    parseJSON(responseDate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSON(String jsonData){
        NewsResult newsResult = new Gson().fromJson(jsonData,NewsResult.class);
        List<News> newsList = newsResult.getData();
        for(int i = 0;i<5;i++){
            for(News news : newsList){
                news.save();
            }
        }
    }


    private void refreshNews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initView() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0;i<CategoryNum;i++) {
            mFragmentArrays[i] = TabFragment.newInstance(i);
        }
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
