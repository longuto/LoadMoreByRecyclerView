package com.longuto.demo01;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView; // 列表
    MyRecyclerViewAdapter mAdapter;
    List<String> datas;

    private void refreshDatas(String index) {
        datas = new ArrayList<>();
        datas.add(index + "11111");
        datas.add(index + "22222");
        datas.add(index + "33333");
        datas.add(index + "44444");
        datas.add(index + "55555");
        datas.add(index + "66666");
        datas.add(index + "77777");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recy);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        refreshDatas("下拉刷新第0页 + ");
        mAdapter = new MyRecyclerViewAdapter(this, datas);
        TextView footView = new TextView(this);
        footView.setText("正在加载中,请稍后...");
        mAdapter.setmFooterView(footView);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String itemData = mAdapter.getItemData(position);
                showToast("当前数据为：" + itemData);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MyDecoration(this));
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore(final int currentPage) {
                showToast("当前页为"  + currentPage);
                if(currentPage >= 4) {  // 当前加载到第一页之后停止加载，包括初始化的第一页
                    ((TextView)mAdapter.getmFooterView()).setText("我是有底线的~");
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            SystemClock.sleep(3000);    // 休眠3秒
                            Message msg = Message.obtain();
                            msg.what = 0x1000001;
                            msg.obj = currentPage;
                            mHandler.sendMessage(msg);
                        }
                    } .start();
                }
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x1000001:
                    int currentPage = (int) msg.obj;
                    refreshDatas("上拉加载第" + currentPage + "页 + ");
                    mAdapter.getData().addAll(datas);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 显示吐司
     * @param content
     */
    private void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
