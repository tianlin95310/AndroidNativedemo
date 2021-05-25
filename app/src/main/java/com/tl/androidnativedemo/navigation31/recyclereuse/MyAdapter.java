package com.tl.androidnativedemo.navigation31.recyclereuse;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/6/12.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    Context context;
    List<MyString> strings;

    public MyAdapter(Context context, List<MyString> strings)
    {
        this.context = context;
        this.strings = strings;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.item_12_et)
        EditText item12Et;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_etittext, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {


        Log.d("my", "strs = " + strings.toString());

        holder.item12Et.setText(strings.get(position).getStr());

        holder.item12Et.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                Log.d("my", "s = " + s.toString());
                Log.d("my", "position = " + holder.getAdapterPosition());
                strings.get(holder.getAdapterPosition()).setStr(s.toString());
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return strings.size();
    }
}
