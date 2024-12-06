package com.example.vvvvv;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {
    Context ctx;
    int imagesList[];
    String title[];
    String subtitles[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx,String []title,String[]subtitle,int[] images){
        this.ctx=ctx;
        this.imagesList=images;
        this.title=title;
        this.subtitles=subtitle;
        inflater=LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =inflater.inflate(R.layout.activity_main2,null);
        TextView titleView=view.findViewById(R.id.titleView);
        TextView subtitleView=view.findViewById(R.id.subtitle);
        ImageView image=view.findViewById(R.id.image);
        titleView.setText(title[i]);
        subtitleView.setText(subtitles[i]);
        image.setImageResource(imagesList[i]);
        return view;
    }
}
