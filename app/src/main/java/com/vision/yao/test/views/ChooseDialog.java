package com.vision.yao.test.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.vision.yao.test.R;
import com.vision.yao.test.adapters.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择框Dialog
 * Created by xiezihao on 16/8/10.
 */
public class ChooseDialog extends Dialog {

    @BindView(R.id.rv)
    RecyclerView rv;

    private OnChooseListener l;

    public ChooseDialog(Context context, List<Item> items, OnChooseListener l) {
        super(context, R.style.BottomToTopDialogTheme);
        this.l = l;

        //在底部显示
        getWindow().setGravity(Gravity.BOTTOM);

        //设置内容
        setContentView(R.layout.dl_choose);
        ButterKnife.bind(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(context));

        //添加数据显示
        mAdapter.addAll(items);
        rv.setAdapter(mAdapter);
    }

    private BaseRecyclerViewAdapter<Item, ViewHolder> mAdapter
            = new BaseRecyclerViewAdapter<Item, ViewHolder>(R.layout.item_dl_choose) {
        @Override
        protected BaseViewHolder getViewHolder(View root, int index) {
            return new ViewHolder(root);
        }

        @Override
        protected void bindView(ViewHolder holder, Item obj, int position, int index) {
            holder.tv.setText(obj.text);
            holder.tv.setTextColor(Color.parseColor(obj.isCancel ? "#999999" : "#333333"));
            //分割线
            if (position == 0) { //第一个隐藏两条线
                holder.v_divider_l.setVisibility(View.GONE);
                holder.v_divider_s.setVisibility(View.GONE);
            } else { //其它位置
                holder.v_divider_s.setVisibility(obj.isCancel ? View.GONE : View.VISIBLE);
                holder.v_divider_l.setVisibility(obj.isCancel ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        protected void onItemClick(View view, Item obj, int position) {
            l.onChoose(obj);
            //关闭提示框
            dismiss();
        }
    };

    class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {

        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.v_divider_l)
        View v_divider_l;
        @BindView(R.id.v_divider_s)
        View v_divider_s;

        public ViewHolder(View view) {
            super(view);
        }
    }

    /**
     * 选项
     */
    public static class Item {
        public int text;
        boolean isCancel;

        public Item(int text, boolean isCancel) {
            this.text = text;
            this.isCancel = isCancel;
        }
    }

    /**
     * 选中监听
     */
    public interface OnChooseListener {
        void onChoose(Item item);
    }
}
