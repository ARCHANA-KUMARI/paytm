package com.robosoft.archana.paytm.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robosoft.archana.paytm.HomePageActivity;
import com.robosoft.archana.paytm.R;
import com.robosoft.archana.paytm.Util.HomePageLayout;
import com.robosoft.archana.paytm.Util.Items;
import com.robosoft.archana.paytm.widget.HeightWrapContentViewPager;

import java.util.List;

/**
 * Created by archana on 6/8/16.
 */
public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private List<HomePageLayout> mHolePageLytList;
    private View mOneRow;
    private HeightWrapContentViewPager mViewPager;
    private RecyclerView mProductRecyclerView;
    private List<String> mImgUrlList;
    private List<Items> mItemsList;
    private TextView mTxtRowLytName, mTxtCarouselLytName;
    private final int ROW_LYT = 1;
    private final int CAROUSEL_LAYT = 2;
    private int inflateLytType;
    private CustomPagerAdapter mCustomAdapter;
    ProductRecycleViewAdapter mProductRecycleViewAdapter;

    public HomePageAdapter(List<HomePageLayout> mHolePageLytList, HomePageActivity mContext, List<String> mImgUrlList, List<Items> mItemsList) {
        this.mHolePageLytList = mHolePageLytList;
        this.mContext = mContext;
        this.mImgUrlList = mImgUrlList;
        this.mItemsList = mItemsList;
        mProductRecycleViewAdapter = new ProductRecycleViewAdapter(mContext, mItemsList);
        mCustomAdapter = new CustomPagerAdapter(mContext, mImgUrlList);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == CAROUSEL_LAYT) {
            mOneRow = LayoutInflater.from(mContext).inflate(R.layout.pager_row, parent, false);
            viewHolder = new PagerViewHolder(mOneRow);
        } else if (viewType == ROW_LYT) {
            mOneRow = LayoutInflater.from(mContext).inflate(R.layout.horizental_list, parent, false);
            viewHolder = new HorizentalListViewHolder(mOneRow);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CAROUSEL_LAYT:
                setHeader(mTxtCarouselLytName, position);
                mCustomAdapter.notifyDataSetChanged();
                break;
            case ROW_LYT:
                setHeader(mTxtRowLytName, position);
                mProductRecycleViewAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void setHeader(TextView mTxtName, int position) {
        if (!TextUtils.isEmpty(mHolePageLytList.get(position).name))
            mTxtName.setText(mHolePageLytList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mHolePageLytList.size();
    }


    private void setViewPager() {
        if (mViewPager != null) {
            mViewPager.setAdapter(mCustomAdapter);
            mViewPager.addOnPageChangeListener(this);

        }
    }

    private void setProductRecyclerViewAdapter() {
        if (mProductRecyclerView != null) {
            mProductRecyclerView.setLayoutManager(com.robosoft.archana.paytm.Util.ViewUtils.setLinearLayoutManager(LinearLayoutManager.HORIZONTAL, mContext));
            mProductRecyclerView.setAdapter(mProductRecycleViewAdapter);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        mViewPager.reMeasureCurrentPage();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class PagerViewHolder extends RecyclerView.ViewHolder {

        public PagerViewHolder(View itemView) {
            super(itemView);
            mTxtCarouselLytName = (TextView) itemView.findViewById(R.id.lyt_name);
            mViewPager = (HeightWrapContentViewPager) itemView.findViewById(R.id.viewpager);
            mViewPager.setClipToPadding(false);
            mViewPager.setPageMargin(40);
            setViewPager();

        }
    }

    class HorizentalListViewHolder extends RecyclerView.ViewHolder {
        public HorizentalListViewHolder(View itemView) {
            super(itemView);
            mTxtRowLytName = (TextView) itemView.findViewById(R.id.lyt_name);
            mProductRecyclerView = (RecyclerView) itemView.findViewById(R.id.product_recycler);
            setProductRecyclerViewAdapter();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHolePageLytList.get(position).layout.equals("carousel-1")) {
            inflateLytType = CAROUSEL_LAYT;
        } else if (mHolePageLytList.get(position).layout.equals("row")) {
            inflateLytType = ROW_LYT;
        }
        return inflateLytType;
    }
}