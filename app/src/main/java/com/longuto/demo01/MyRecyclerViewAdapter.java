package com.longuto.demo01;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int TYPE_NORMAL = 0x10001;   // 普通视图类型
    public static final int TYPE_FOOTER = 0x10002;   // 底部视图类型

    //是否为Adapter设置点击监听
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }


    /** 获取当前条目的数据 */
    public String getItemData(int position) {
        return mData.get(position);
    }

    /** 获取当前适配器的数据 */
    public List<String> getData() {
        return mData;
    }

    /**
     * 替换RecyclerView数据
     */
    public void replaceList(List<String> list) {
        if (list != null){
            this.mData = list;
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    List<String> mData;
    Context mContext;

    public MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mData = data;
        this.mContext = context;
    }

    View mFooterView;   // 底部视图

    public View getmFooterView() {
        return mFooterView;
    }

    public void setmFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if(null == mFooterView) {
            return TYPE_NORMAL;
        }

        if(position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }

        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(null != mFooterView && TYPE_FOOTER == viewType) {    // 如果是底部视图
            return new MyFooterHolder(mFooterView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_recy, parent, false);
        view.setOnClickListener(this);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(TYPE_FOOTER == getItemViewType(position)) {  // 底部
            return;
        }
        if(viewHolder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) viewHolder;
            int pos = myHolder.getLayoutPosition();
            myHolder.setData(pos);
            myHolder.itemView.setTag(pos);
        }
    }

    @Override
    public int getItemCount() {
        int size =  null == mData ? 0 : mData.size();
        if(null == mFooterView) {
            return size;
        } else {
            return size + 1;
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_recy);
        }

        public void setData(int position) {
            String temp = mData.get(position);
            tv.setText(temp);
        }
    }

    private class MyFooterHolder extends MyHolder {
        public MyFooterHolder(View mFooterView) {
            super(mFooterView);
        }
    }
}
