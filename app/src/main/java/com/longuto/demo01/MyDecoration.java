package com.longuto.demo01;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yltang3 on 2018/5/6.
 */

public class MyDecoration extends RecyclerView.ItemDecoration {

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS  = new int[]{
            android.R.attr.listDivider
    };

    Context mContext;
    Drawable mDivider;

    public MyDecoration(Context context) {
        this.mContext = context;
        final TypedArray ta = context.obtainStyledAttributes(ATRRS);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(childAt);
            if(2 == position || 5 == position) {    // 如果adapter的位置为2或5
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                final int top = childAt.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if(2 == position || 5 == position) {    // 如果adapter的位置为2或5则下移
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }

    MyDecorationListener mListener; // 监听

    /**
     * 设置当前的位置
     */
    public void setOnMyDecorationListener(MyDecorationListener listener) {
        this.mListener = listener;
    }

    public interface MyDecorationListener {
        int getCurrentPosition();   // 获取当前的位置
    }
}