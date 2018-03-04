package com.uic.snaram2.mymusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nikhi on 2/27/2018.
 */

class ListViewAdapter extends BaseAdapter {
    Context context;
   // int[] images;
    ArrayList songtitles;
    ArrayList songartists;
    ArrayList weblinks;
    LayoutInflater layoutInflater;


    public ListViewAdapter(Context context,ArrayList songtitles,ArrayList songartists,ArrayList weblinks)
    {
        this.context = context;
       // this.images = images;
        this.songtitles =songtitles;
        this.songartists = songartists;
        this.weblinks = weblinks;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return songartists.size();
    }

    @Override
    public Object getItem(int position) {
        return songartists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.list_view,null);
       // ImageView image = (ImageView)view.findViewById(R.id.images);
        TextView title = (TextView)view.findViewById(R.id.titles);
        TextView artist = (TextView)view.findViewById(R.id.artists);
      //  image.setImageResource(images[position]);
        title.setText(songtitles.get(position).toString());
        artist.setText(songartists.get(position).toString());
        return view;
    }
}

