package com.tl.androidnativedemo.navigation01.liaotian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;

import java.util.List;


/**
 * Created by tianlin on 2017/3/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;

    List<LiaoTianVo> list;

    public MyAdapter(Context context, List<LiaoTianVo> list)
    {
        this.context = context;
        this.list = list;
    }

    public void add(LiaoTianVo liaoTianVo)
    {
        list.add(liaoTianVo);
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder
    {
        TextView item1_liaotian_tv;

        ImageView item1_liaotian_iv;

        public MyViewHolder1(View itemView)
        {
            super(itemView);
            item1_liaotian_tv = (TextView) itemView.findViewById(R.id.item1_liaotian_tv);
            item1_liaotian_iv = (ImageView) itemView.findViewById(R.id.item1_liaotian_iv);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder
    {
        TextView item2_liaotian_tv;

        ImageView item2_liaotian_iv;

        public MyViewHolder2(View itemView)
        {
            super(itemView);
            item2_liaotian_tv = (TextView) itemView.findViewById(R.id.item2_liaotian_tv);
            item2_liaotian_iv = (ImageView) itemView.findViewById(R.id.item2_liaotian_iv);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = null;
        if (viewType == 1)
        {
            view = LayoutInflater.from(context).inflate(R.layout.liao_tian_item1, parent, false);
            return new MyViewHolder1(view);
        }
        else if (viewType == 2)
        {
            view = LayoutInflater.from(context).inflate(R.layout.liao_tian_item2, parent, false);
            return new MyViewHolder2(view);
        }
        else
            return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        int viewType = getItemViewType(position);

        if(viewType == 1)
        {
            MyViewHolder1 myViewHolder1 = (MyViewHolder1) holder;
            myViewHolder1.item1_liaotian_tv.setText(list.get(position).getText());
            myViewHolder1.item1_liaotian_iv.setImageResource(R.drawable.android);
        }
        else if(viewType == 2)
        {
            MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
            myViewHolder2.item2_liaotian_tv.setText(list.get(position).getText());
            myViewHolder2.item2_liaotian_iv.setImageResource(R.drawable.apple);
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        String who = list.get(position).getWho();

        if ("1".equals(who))
            return 1;
        else if ("2".equals(who))
            return 2;
        return -1;
    }
}
