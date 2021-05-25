package com.tl.androidnativedemo.navigation71.check;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/7/10.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class CheckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context context;

    private List<CheckItemTaskVo> taskVoList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_list, parent, false);
        return new CheckItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        CheckItemTaskVo checkItemTaskVo = taskVoList.get(position);

        CheckItemViewHolder viewHolder = (CheckItemViewHolder) holder;

        viewHolder.tvItemCheckText.setText(checkItemTaskVo.text);

        if (checkItemTaskVo.isCheckOver)
        {
            viewHolder.pbCheckProgress.setVisibility(View.GONE);

            if(checkItemTaskVo.isPass)
            {
                viewHolder.ivItemCheckIcon.setImageResource(R.drawable.android);
            }
            else
            {
                viewHolder.ivItemCheckIcon.setImageResource(R.drawable.apple);
            }
        }
        else
        {
            viewHolder.pbCheckProgress.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount()
    {
        return taskVoList.size();
    }

    class CheckItemViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_item_check_text)
        TextView tvItemCheckText;
        @BindView(R.id.iv_item_check_icon)
        ImageView ivItemCheckIcon;
        @BindView(R.id.pb_check_progress)
        ProgressBar pbCheckProgress;

        public CheckItemViewHolder(View itemView)
        {
            super(itemView);

            ButterKnife.bind(this, itemView);

//            View view = LayoutInflater.from(context).inflate(R.layout.item_check_list, parent, false);
        }
    }

    public CheckAdapter(Context context, List<CheckItemTaskVo> taskVoList)
    {
        this.context = context;
        this.taskVoList = taskVoList;
    }

    public List<CheckItemTaskVo> getTaskVoList()
    {
        return taskVoList;
    }

    public void setTaskVoList(List<CheckItemTaskVo> taskVoList)
    {
        this.taskVoList = taskVoList;
    }

}
