package cn.hanzhuang42.mynews.util;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.hanzhuang42.mynews.model.News;

public class DbUtility {
    public static List<News> query(int type){
        String[] mTabTitles  = {"显示器","音箱","移动业务","音箱","音箱"};
        List<News> newsList = DataSupport.where("category = ?",mTabTitles[type])
                .order("id desc")
                .find(News.class);
        return newsList;
    }
}
