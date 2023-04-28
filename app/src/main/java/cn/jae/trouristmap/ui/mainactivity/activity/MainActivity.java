package cn.jae.trouristmap.ui.mainactivity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fondesa.recyclerviewdivider.DividerBuilder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jae.trouristmap.base.BaseActivity;
import cn.jae.trouristmap.greendao.manager.TouristListDaoManager;
import cn.jae.trouristmap.greendao.table.TrouristListTable;
import cn.jae.trouristmap.ui.mainactivity.adapter.TrouristListAdapter;
import cn.jae.trouristmap.R;

/**
 * @Author : Jae
 * @Date : 2023/4/28 10:36
 * @Description :
 * 主界面：
 * 1.显示旅游分组：可用来做历史记录的查看
 * 2.添加旅游分组
 * 3.删除旅游分组：这个删除要把数据库内容也删除了
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.iv_add_trourist)
    ImageView ivAddTrourist;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_trourist)
    RecyclerView rvTrourist;


    private TrouristListAdapter mTrouristListAdapter;
    private List<TrouristListTable> mTrouristListItemBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    private void initData() {
        mTrouristListItemBeanList = new ArrayList<>();
        // TODO: 2023/4/28 根据数据库进行获取
        List<TrouristListTable> allTrouristList = TouristListDaoManager.getInstance().getAllTrouristListSortByTime();
        mTrouristListItemBeanList.addAll(allTrouristList);
    }

    private void initView() {
        tvTitle.setText("计划列表");

        mTrouristListAdapter = new TrouristListAdapter(mTrouristListItemBeanList, this);
        mTrouristListAdapter.setOnTrouristListItemClickListener(new TrouristListAdapter.OnTrouristListItemClickListener() {
            @Override
            public void onTrouristListItemClick(int position) {
                goTrouristDetialActivity(mTrouristListItemBeanList.get(position).getTrouristName());
            }

            @Override
            public void onTrouristListItemLongClick(int position) {
                // TODO: 2023/4/28 添加确认弹窗
                TouristListDaoManager.getInstance().delete(mTrouristListItemBeanList.get(position));
                resetTouristList();
            }
        });
        rvTrourist.setAdapter(mTrouristListAdapter);
        rvTrourist.setLayoutManager(new LinearLayoutManager(this));
        new DividerBuilder(this)
                .color(Color.TRANSPARENT)
                .size(5, TypedValue.COMPLEX_UNIT_DIP)
                .build()
                .addTo(rvTrourist);
        ivAddTrourist.setOnClickListener(v -> {
            new XPopup.Builder(this).asInputConfirm("开始计划", "请输入计划名称",
                            new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {
                                    TrouristListTable trouristListTable = new TrouristListTable();
                                    trouristListTable.setTrouristName(text);
                                    trouristListTable.setTrouristCreateTime(TimeUtils.getNowString());
                                    trouristListTable.setTrouristModifyTime(TimeUtils.getNowString());
                                    TouristListDaoManager.getInstance().insertTrourist(trouristListTable);
                                    resetTouristList();
                                }
                            })
                    .show();
        });
    }

    private void resetTouristList() {
        mTrouristListItemBeanList.clear();
        mTrouristListItemBeanList.addAll(TouristListDaoManager.getInstance().getAllTrouristListSortByTime());
        if (mTrouristListAdapter != null) {
            mTrouristListAdapter.notifyDataSetChanged();
        }
    }

    private void goTrouristDetialActivity(String trouristName) {
        // TODO: 2023/4/28 前往详情界面
        ToastUtils.showShort(trouristName);
        Intent intent = new Intent(MainActivity.this, TouristDetailActivity.class);
        intent.putExtra(TouristDetailActivity.PLAN_NAME, trouristName);
        startActivity(intent);

    }
}