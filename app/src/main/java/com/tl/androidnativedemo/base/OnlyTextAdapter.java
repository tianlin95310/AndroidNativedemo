package com.tl.androidnativedemo.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by tianlin on 2017/6/29.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */


public class OnlyTextAdapter extends RecyclerView.Adapter<OnlyTextAdapter.MyViewHolder>
{
    Context context;
    List<String> strings;

    public OnlyTextAdapter(Context context, List<String> strings)
    {
        this.context = context;
        this.strings = strings;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.item_text)
        TextView itemText;

        public MyViewHolder(View itemView)
        {
            super(itemView);
//            View view = LayoutInflater.from(context).inflate(R.layout.item_just_one_text, parent, false);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_text)
        public void onViewClicked()
        {
            ToastUtils.show(context, strings.get(getAdapterPosition()));
            Log.d("my", strings.get(getAdapterPosition()));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_just_one_text, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.itemText.setText(strings.get(position));
    }

    public void addList(List<String> list)
    {
        int preSize = this.strings.size();
        this.strings.addAll(list);
        int currentSize = this.strings.size();

        notifyItemRangeInserted(preSize, list.size());
    }

    public List<String> getStrings()
    {
        return strings;
    }

    public void setStrings(List<String> strings)
    {
        this.strings = strings;
    }

    @Override
    public int getItemCount()
    {
        return strings.size();
    }
}

