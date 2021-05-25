package com.tl.androidnativedemo.navigation21.requestpremission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.vo.ContactVo;

import java.util.List;


/**
 * Created by tianlin on 2017/3/15.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{

    List<ContactVo> contactVos;
    Context context;

    public MyAdapter(List<ContactVo> contactVos, Context context)
    {
        this.contactVos = contactVos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        ContactVo contactVo = contactVos.get(position);

        holder.tv.setText("name:" + contactVo.getName() + "\tnumber:" + contactVo.getNumber() + "\temail " + contactVo.getEmail());
    }
    @Override
    public int getItemCount()
    {
        return contactVos == null ? 0 : contactVos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
