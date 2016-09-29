package com.vision.yao.test.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 基础控件基本Adapter
 * Created by owner on 2016/3/31.
 *
 * @param <T> 数据类型
 * @param <H> ViewHolder类型
 */
public abstract class BaseAdapter<T, H> extends android.widget.BaseAdapter {

    //Tag不可为正数，防止和ID冲突
    private final int TAG_POSITION = -10001;

    private int layoutID;
    private int clickID;

    /**
     * 构造函数
     *
     * @param layoutID 布局ID
     */
    public BaseAdapter(int layoutID) {
        this(layoutID, 0);
    }

    /**
     * 构造函数
     *
     * @param layoutID 布局ID
     * @param clickID  点击ViewID，0整行，-1不设置
     */
    public BaseAdapter(int layoutID, int clickID) {
        this.layoutID = layoutID;
        this.clickID = clickID;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
            //设置点击
            if (clickID == 0)
                holder.clickView = convertView;
            else
                holder.clickView = holder.findView(clickID);
            if (holder.clickView != null)
                holder.clickView.setOnClickListener(mClickListener);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }
        if (holder.clickView != null)
            holder.itemView.setTag(TAG_POSITION, position);
        if (position == size() - 1) //最后一条数据显示了
            onFooterShow();
        bindView((H) holder, mObjects.get(position), position);
        return convertView;
    }

    //点击监听
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(TAG_POSITION);
            onItemClick(view, mObjects.get(position), position);
        }
    };

    /**
     * Footer显示
     */
    protected void onFooterShow() {
    }

    /**
     * 点击监听
     *
     * @param view     点击的View
     * @param obj      数据对象
     * @param position 数据位置
     */
    public void onItemClick(View view, T obj, int position) {
    }

    //====================

    /**
     * 获取ViewHolder
     *
     * @param root Holder绑定的View对象
     * @return ItemHolder
     */
    protected abstract BaseViewHolder getViewHolder(View root);

    /**
     * 绑定数据
     *
     * @param holder   ItemHolder
     * @param obj      数据对象
     * @param position 数据位置
     */
    protected abstract void bindView(H holder, T obj, int position);

    //==============================

    //数据列表
    private List<T> mObjects = new ArrayList<>();

    /**
     * 列表
     *
     * @return 列表
     */
    public List<T> list() {
        return mObjects;
    }

    /**
     * 删除数据
     *
     * @param obj 数据对象
     */
    public void remove(T obj) {
        mObjects.remove(obj);
    }

    /**
     * 添加数据
     *
     * @param obj 数据对象
     */
    public void add(T obj) {
        mObjects.add(obj);
    }

    /**
     * 获取数据
     *
     * @param index 数据下标
     * @return 数据对象
     */
    public T get(int index) {
        return mObjects.get(index);
    }

    /**
     * 添加所有数据
     *
     * @param list 数据列表
     */
    public void addAll(List<T> list) {
        mObjects.addAll(list);
    }

    /**
     * 清空所有数据
     */
    public void clear() {
        mObjects.clear();
    }

    /**
     * 数据量
     *
     * @return 数据量
     */
    public int size() {
        return mObjects.size();
    }

    //===============================

    /**
     * 基础ViewHolder
     */
    public static class BaseViewHolder {

        public View itemView;
        //点击的View缓存
        private View clickView;

        public BaseViewHolder(View root) {
            this.itemView = root;
            ButterKnife.bind(this, root);
        }

        /**
         * FindView
         *
         * @param id view id
         * @return View
         */
        public <V> V findView(int id) {
            return (V) itemView.findViewById(id);
        }
    }
}
