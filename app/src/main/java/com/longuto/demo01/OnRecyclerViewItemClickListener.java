package com.longuto.demo01;

import android.view.View;

/**
 * Created by longuto on 2016/11/3.
 * RecyclerView的Item点击接口
 */
public interface OnRecyclerViewItemClickListener {
    void onItemClick(View view, int position);    //需要自定义的方法
}
