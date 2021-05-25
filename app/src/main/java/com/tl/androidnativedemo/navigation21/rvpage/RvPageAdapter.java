package com.tl.androidnativedemo.navigation21.rvpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.utils.display.PixsUtils;

/**
 * Created by tianlin on 2018/7/11.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class RvPageAdapter extends PagedListAdapter<DataBean, RvPageAdapter.MyViewHolder> {

    private Context context;

    public RvPageAdapter(DiffUtil.ItemCallback<DataBean> mDiffCallback, Context context) {
        super(mDiffCallback);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, null);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DataBean data = getItem(position);

        if(data.isShow) {
            setVisible(holder, true);
        }
        else {
            setVisible(holder, false);
        }

        holder.text1.setText(String.valueOf(data.content));
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBean thisOne = getItem(holder.getAdapterPosition());

//                Iterator<DataBean> iterator = getCurrentList().iterator();
//                while(iterator.hasNext()) {
//                    DataBean dataBean = iterator.next();
//                    if(dataBean.id == thisOne.id) {
//                        iterator.remove();
//                    }
//                }

                getItem(holder.getAdapterPosition()).isShow = false;
                notifyItemChanged(holder.getAdapterPosition());
                RVPagingActivity activity = (RVPagingActivity) context;
                activity.refresh();
            }
        });
    }

    private void setVisible(RecyclerView.ViewHolder viewHolder, boolean visibility) {
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();

        if(layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(0, 0);
        }
        if (visibility) {
            viewHolder.itemView.setVisibility(View.VISIBLE);
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = PixsUtils.dp2px(context, 100);
        } else {
            viewHolder.itemView.setVisibility(View.GONE);
            layoutParams.width = 0;
            layoutParams.height = 0;
        }
        viewHolder.itemView.setLayoutParams(layoutParams);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;

        public MyViewHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.text);
        }
    }
}
