package com.tonystark.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tonystark.android.xpanel.AbsXPanelAdapter;
import com.tonystark.android.xpanel.XPanelDefaultHeaderView;
import com.tonystark.android.xpanel.XPanelDragMotionDetection;
import com.tonystark.android.xpanel.XPanelView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemBean> mDataList = new ArrayList<>();

    private XPanelView xPanelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        xPanelView = findViewById(R.id.xpanelview);
        for (int i = 0; i < 15; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.text = "item name : " + i + "";
            mDataList.add(itemBean);
        }

        AbsXPanelAdapter absXPanelAdapter = new XPanelAdapter();
        xPanelView.setAdapter(absXPanelAdapter);
        absXPanelAdapter.notifyDataSetChanged();

        XPanelDefaultHeaderView headerView = new XPanelDefaultHeaderView(this);
        headerView.setCanDrag(true);

        xPanelView.setHeaderLayout(headerView);
        xPanelView.setMeasureAll(true);
        xPanelView.setChuttyMode(false);
        xPanelView.setCanFling(true);
        xPanelView.setExposedPercent(0.25f);
        xPanelView.setKickBackPercent(0.65f);
        xPanelView.setOnXPanelMotionListener(new XPanelDragMotionDetection.OnXPanelMotionListener() {
            @Override
            public void OnDrag(int dragMotion, int offset) {
                String motion = "";
                switch (dragMotion) {
                    case XPanelDragMotionDetection.DragMotion.DRAG_DOWN: {
                        motion = "DRAG_DOWN";
                        break;
                    }
                    case XPanelDragMotionDetection.DragMotion.DRAG_UP: {
                        motion = "DRAG_UP";
                        break;
                    }
                    case XPanelDragMotionDetection.DragMotion.DRAG_FLING: {
                        motion = "DRAG_FLING";
                        break;
                    }
                    case XPanelDragMotionDetection.DragMotion.DRAG_STOP: {
                        motion = "DRAG_STOP";
                        break;
                    }
                }
                Log.i("OnXPanelMotionListener", "OnDrag dragMotion:" + motion + " offset:" + offset);
            }

            @Override
            public void OnCeiling(boolean isCeiling) {
                Log.i("OnXPanelMotionListener", "OnCeiling isCeiling:" + isCeiling);
            }

        });
    }

    private class ItemBean {
        public int time;
        public String text;
    }

    private class XPanelAdapter extends AbsXPanelAdapter<XPanelAdapter.MyViewHolder> {
        private Toast mToast;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final ItemBean bean = mDataList.get(position);
            holder.mItem.setText(bean.text);
            holder.mTime.setText(bean.time + "");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bean.time++;
                    xPanelView.getAdapter().notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView mItem;

            private TextView mTime;

            public MyViewHolder(View itemView) {
                super(itemView);
                mItem = itemView.findViewById(R.id.item);
                mTime = itemView.findViewById(R.id.time);
            }
        }
    }
}
