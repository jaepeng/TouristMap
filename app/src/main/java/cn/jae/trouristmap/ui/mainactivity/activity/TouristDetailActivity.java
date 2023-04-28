package cn.jae.trouristmap.ui.mainactivity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps2d.MapView;
import com.amap.api.services.help.Tip;
import com.blankj.utilcode.util.TimeUtils;
import com.fondesa.recyclerviewdivider.DividerBuilder;
import com.sinfeeloo.openmap.OpenMapUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jae.trouristmap.R;
import cn.jae.trouristmap.base.BaseActivity;
import cn.jae.trouristmap.greendao.manager.TouristDetialManager;
import cn.jae.trouristmap.greendao.table.TrouristDetailTable;
import cn.jae.trouristmap.ui.mainactivity.adapter.TrouristDetailAdapter;

public class TouristDetailActivity extends BaseActivity {
    @BindView(R.id.iv_add_trourist_detail)
    ImageView ivAddTrourist;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_trourist_detial)
    RecyclerView rvTrourist;
    @BindView(R.id.container)
    ConstraintLayout clContainer;
    public static final String PLAN_NAME = "plan_name";
    private String mPlanName;

    public static final int REQUEST_GET_TIPS = 1001;
    private TrouristDetailAdapter mTrouristDetailAdapter;
    private List<TrouristDetailTable> mTrouristDetailTables;

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
        Intent intent = getIntent();
        mPlanName = intent.getStringExtra(PLAN_NAME);
        Log.d("TouristDetailActivity", "onCreate(TouristDetailActivity.java:53)" + mPlanName);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        mTrouristDetailTables = new ArrayList<>();
        List<TrouristDetailTable> toruristDetailListByPlanName = TouristDetialManager.getInstance().getToruristDetailListByPlanName(mPlanName);
        mTrouristDetailTables.addAll(toruristDetailListByPlanName);
    }

    private void initView() {
        tvTitle.setText(mPlanName);
        mTrouristDetailAdapter = new TrouristDetailAdapter(mTrouristDetailTables, this, mBundle);
        mTrouristDetailAdapter.setOnTrouristDetailItemClickListener(new TrouristDetailAdapter.OnTrouristDetailItemClickListener() {
            @Override
            public void onTrouristListItemClick(int position) {
                Log.d("TouristDetailActivity", "onTrouristListItemClick(TouristDetailActivity.java:84)" + position);
                TrouristDetailTable trouristDetailTable = mTrouristDetailTables.get(position);
                OpenMapUtil.openMapPopupWindow(TouristDetailActivity.this, clContainer, trouristDetailTable.getTargetPlaceName(), Double.valueOf(trouristDetailTable.getLatitude()), Double.valueOf(trouristDetailTable.getLongitude()));
            }

            @Override
            public void onTrouristListItemLongClick(int position) {
                TouristDetialManager.getInstance().deleteTouristDetail(mTrouristDetailTables.get(position));
                mTrouristDetailTables.remove(position);
                mTrouristDetailAdapter.notifyDataSetChanged();
                Log.d("TouristDetailActivity", "onTrouristListItemLongClick(TouristDetailActivity.java:91)" + position);
            }
        });
        rvTrourist.setAdapter(mTrouristDetailAdapter);
        rvTrourist.setLayoutManager(new LinearLayoutManager(this));
        new DividerBuilder(this)
                .color(Color.TRANSPARENT)
                .size(5, TypedValue.COMPLEX_UNIT_DIP)
                .build()
                .addTo(rvTrourist);

        List<MapView> trouristList = mTrouristDetailAdapter.getTrouristList();
        for (MapView mapView : trouristList) {

        }

        ivAddTrourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristDetailActivity.this, InputTipsActivity.class);
                startActivityForResult(intent, REQUEST_GET_TIPS);
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_tourist_detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GET_TIPS) {
                if (data != null) {
                    Tip tip = data.getParcelableExtra("EXTRA_TIP");
                    if (tip.getPoiID() != null && !tip.getPoiID().equals("")) {
                        TrouristDetailTable trouristDetailTable = new TrouristDetailTable();
                        trouristDetailTable.setPlanName(mPlanName);
                        trouristDetailTable.setTargetPlaceName(tip.getName());
                        trouristDetailTable.setAddress(tip.getAddress());
                        trouristDetailTable.setLongitude(tip.getPoint().getLongitude() + "");
                        trouristDetailTable.setLatitude(tip.getPoint().getLatitude() + "");
                        trouristDetailTable.setLastModifiedDate(TimeUtils.getNowString());
                        trouristDetailTable.setCreaetDate(TimeUtils.getNowString());
                        TouristDetialManager.getInstance().insertTouristDetail(trouristDetailTable);
                        mTrouristDetailTables.add(0, trouristDetailTable);
                        mTrouristDetailAdapter.notifyDataSetChanged();
                        for (MapView mapView : mTrouristDetailAdapter.getTrouristList()) {
                            mapView.onCreate(mBundle);
                        }
                        for (MapView mapView : mTrouristDetailAdapter.getTrouristList()) {
                            mapView.onResume();
                        }
                    }

                }
            }
        }
    }
}