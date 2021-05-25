package com.tl.androidnativedemo.navigation71.pullrefresh;

import android.content.Context;
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
 * Created by tianlin on 2018/5/14.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class RefreshAdapter extends RecyclerView.Adapter
{

    public static final int VIEW_TYPE_ITEM = 1;

    Context context;
    List<RefreshVo> refreshVoList;

    public RefreshAdapter(Context context, List<RefreshVo> refreshVoList)
    {
        this.context = context;
        this.refreshVoList = refreshVoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        if(viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_just_one_text, parent, false);
            return new MyViewHolder(view);
        }

        return null;
    }

    public void addList(List<RefreshVo> refreshVos)
    {
        int preSize = this.refreshVoList.size();
        this.refreshVoList.addAll(preSize, refreshVos);

        notifyItemRangeInserted(preSize, refreshVos.size());
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
            ToastUtils.show(context, refreshVoList.get(getAdapterPosition()).content);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);

        if(viewType == VIEW_TYPE_ITEM) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.itemText.setText(this.refreshVoList.get(position).content);
        }

    }

    @Override
    public int getItemViewType(int position)
    {
        return refreshVoList.get(position).viewType;
    }

    @Override
    public int getItemCount()
    {
        return refreshVoList.size();
    }
}
