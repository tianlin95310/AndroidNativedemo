package com.tl.androidnativedemo.navigation31.parandchild;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseVo;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/9/29.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ParAndChildAdapter extends RecyclerView.Adapter
{

    public static final int VIEW_TYPE1 = 1;

    public static final int VIEW_TYPE2 = 2;
    Context context;
    List<BaseVo> list;

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public List<BaseVo> getList()
    {
        return list;
    }

    public void setList(List<BaseVo> list)
    {
        this.list = list;
    }

    public ParAndChildAdapter(Context context, List<BaseVo> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;

        if (viewType == 1)
        {
            view = LayoutInflater.from(context).inflate(R.layout.par_and_child_1, parent, false);
            return new MyViewHolder1(view);
        } else if (viewType == 2)
        {
            view = LayoutInflater.from(context).inflate(R.layout.par_and_child_2, parent, false);
            return new MyViewHolder2(view);
        }
        return null;
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder
    {

        @BindView(R.id.tv_not_add_1)
        TextView tvNotAdd1;
        @BindView(R.id.tv_not_add_2)
        TextView tvNotAdd2;
        @BindView(R.id.tv_not_add_3)
        Button tvNotAdd3;
        @BindView(R.id.ll_not_add)
        LinearLayout llNotAdd;

        public MyViewHolder1(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            view = LayoutInflater.from(context).inflate(R.layout.par_and_child_1, parent, false);
        }

        @OnClick({R.id.tv_not_add_2, R.id.ll_not_add})
        public void onViewClicked(View view)
        {
            switch (view.getId())
            {
                case R.id.tv_not_add_2:
                    ToastUtils.show(context, "我是text，被点击了");
                    break;
                case R.id.ll_not_add:
                    ToastUtils.show(context, "整个被点击了");
                    break;
            }
        }

        @OnClick(R.id.tv_not_add_3)
        public void onViewClicked()
        {
            ToastUtils.show(context, "我是button 被点击了");
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder
    {

        @BindView(R.id.tv_add_1)
        TextView tvAdd1;
        @BindView(R.id.tv_add_2)
        TextView tvAdd2;
        @BindView(R.id.tv_add_3)
        Button tvNotAdd3;
        @BindView(R.id.ll_add)
        LinearLayout llAdd;

        public MyViewHolder2(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            view = LayoutInflater.from(context).inflate(R.layout.par_and_child_2, parent, false);
        }

        @OnClick({R.id.tv_add_2, R.id.ll_add})
        public void onViewClicked(View view)
        {
            switch (view.getId())
            {
                case R.id.tv_add_2:
                    ToastUtils.show(context, "我是text，被点击了");
                    break;
                case R.id.ll_add:
                    ToastUtils.show(context, "ll_add被点击了");
                    break;
            }
        }

        @OnClick(R.id.tv_add_3)
        public void onViewClicked()
        {
            ToastUtils.show(context, "我是button，被点击了");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemViewType(int position)
    {
        return list.get(position).viewType;
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
