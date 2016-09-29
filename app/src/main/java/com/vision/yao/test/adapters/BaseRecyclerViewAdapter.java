package com.vision.yao.test.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 基础Adapter
 * Created by Hao on 2016/1/1.
 *
 * @param <T> 数据类型
 * @param <H> ViewHolder类型
 */
public abstract class BaseRecyclerViewAdapter<T, H> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {

    private int[] layoutIDs;
    private int[] clickViewIDs;

    /**
     * 单样式构造函数,整行点击
     *
     * @param layoutID 布局ID
     */
    public BaseRecyclerViewAdapter(int layoutID) {
        this(layoutID, 0);
    }

    /**
     * 单样式构造函数
     *
     * @param layoutID    布局ID
     * @param clickViewID 点击ViewID，0整行，-1不设置
     */
    public BaseRecyclerViewAdapter(int layoutID, int clickViewID) {
        this(new int[]{layoutID}, new int[]{clickViewID});
    }

    /**
     * 多样式构造函数
     *
     * @param layoutIDs    布局IDs
     * @param clickViewIDs 点击ViewIDs，0整行，-1不设置，其它对应的View，和布局对应
     */
    public BaseRecyclerViewAdapter(int[] layoutIDs, int[] clickViewIDs) {
        this.layoutIDs = layoutIDs;
        this.clickViewIDs = clickViewIDs;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewIndex) {
        if (viewIndex == VIEW_TYPE_FOOTER) { //是Footer
            return new BaseViewHolder(mFooter);
        } else if (viewIndex == VIEW_TYPE_HEADER) { //是Header
            return new BaseViewHolder(mHeader);
        }
        if (viewIndex < 0 || viewIndex >= layoutIDs.length) { //不合法ViewIndex
            viewIndex = 0;
        }
        //必须传入parent来inflate布局，不然item的match_parent无效
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(layoutIDs[viewIndex], viewGroup, false);
        BaseViewHolder holder = getViewHolder(root, viewIndex);
        int clickViewID = clickViewIDs[viewIndex];
        if (clickViewID == 0) {
            holder.clickView = root;
        } else {
            holder.clickView = holder.findView(clickViewID);
        }
        if (holder.clickView != null) {
            holder.clickView.setOnClickListener(mClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isFooter(position)) { //Footer不绑定数据
            onFooterShow();
            return;
        } else if (isHeader(position)) { //Header不绑定数据
            return;
        }
        int dataPosition = position - (mHeader == null ? 0 : 1); //如果存在头部，数据位置应该减去1
        if (holder.clickView != null) {
            holder.clickView.setTag(dataPosition);
        }
        bindView((H) holder, mObjects.get(dataPosition), dataPosition, getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return mObjects.size() + (mFooter == null ? 0 : 1) + (mHeader == null ? 0 : 1);
    }

    /**
     * 判断应该显示的布局,无法判断请调用super方法来自动判断
     *
     * @param position 数据的位置
     * @return 当前数据对应的布局下标，和传入的布局列表对应
     */
    @Override
    public int getItemViewType(int position) {
        if (isFooter(position)) { //Footer的位置
            return VIEW_TYPE_FOOTER;
        } else if (isHeader(position)) { //Header的位置
            return VIEW_TYPE_HEADER;
        }
        return 0;
    }

    //点击监听
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            onItemClick(view, mObjects.get(position), position);
        }
    };

    /**
     * 点击监听
     *
     * @param view     点击的View
     * @param obj      数据对象
     * @param position 数据位置
     */
    protected void onItemClick(View view, T obj, int position) {
    }

    //=====================

    /**
     * Footer的ViewType类型
     */
    private final int VIEW_TYPE_FOOTER = -100;
    /**
     * Header的ViewType类型
     */
    private final int VIEW_TYPE_HEADER = -200;
    private View mFooter;
    private View mHeader;

    /**
     * 设置FooterView,需要{@link #notifyDataSetChanged()},目前只支持单Footer主要是用于显示分页
     *
     * @param footer footer
     */
    public void setFooter(View footer) {
        mFooter = footer;
    }

    /**
     * 设置HeaderView,需要{@link #notifyDataSetChanged()},目前只支持单Header
     *
     * @param header header
     */
    public void setHeader(View header) {
        mHeader = header;
    }

    /**
     * 是否是Footer
     *
     * @param position Footer的位置
     */
    public boolean isFooter(int position) {
        return mFooter != null && position == getItemCount() - 1; //Footer存在并且是最后的数据了
    }

    /**
     * 是否是Header
     *
     * @param position Header的位置
     */
    private boolean isHeader(int position) {
        return mHeader != null && position == 0; //Header存在并且是第一个数据
    }

    /**
     * Footer显示
     */
    protected void onFooterShow() {
    }

    //====================

    /**
     * 获取ViewHolder
     *
     * @param root  Holder绑定的View对象
     * @param index 布局的Index
     * @return ItemHolder
     */
    protected abstract BaseViewHolder getViewHolder(View root, int index);

    /**
     * 绑定数据
     *
     * @param holder   ItemHolder
     * @param obj      数据对象
     * @param position 数据位置
     * @param index    布局的Index
     */
    protected abstract void bindView(H holder, T obj, int position, int index);

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

    //==============================

    /**
     * 还原到初始显示状态
     */
    public void resetUI() {
        clear();
        setHeader(null);
        setFooter(null);
        notifyDataSetChanged();
    }

    //===============================

    /**
     * 基础ViewHolder
     */
    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        //点击的View缓存
        private View clickView;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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