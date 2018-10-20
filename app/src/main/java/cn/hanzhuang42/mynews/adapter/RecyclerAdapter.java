package cn.hanzhuang42.mynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.hanzhuang42.mynews.BrowseActivity;
import cn.hanzhuang42.mynews.R;
import cn.hanzhuang42.mynews.model.News;


/**
 * Created by zq on 2017/1/12.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NewsViewHolder> {

    private List<News> mNewsList;
    private Context mContext = null;

    public RecyclerAdapter(List<News> newsList){
        mNewsList = newsList;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        View newsView;
        ImageView newsImg;
        TextView newsTitle;
        TextView newsContent;

        public NewsViewHolder(View itemView) {
            super(itemView);

            newsView = itemView;
            newsImg = itemView.findViewById(R.id.imageView);
            newsTitle = itemView.findViewById(R.id.tv_title);
            newsContent = itemView.findViewById(R.id.tv_content);

        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.item, parent, false);
        final NewsViewHolder holder = new NewsViewHolder(childView);

        mContext = parent.getContext();
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mNewsList.get(position);
                Intent intent = new Intent(mContext, BrowseActivity.class);
                //由于该地址无法显示，所以加载其他指定网址
                intent.putExtra("url",news.getPage_url());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = mNewsList.get(position);
        String title = news.getTitle();
        String content = title + title;
        String thumb_url = news.getThumb_url();
        Glide.with(mContext)
                .load(thumb_url)
                .into(holder.newsImg);
        holder.newsTitle.setText(title);
        holder.newsContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}