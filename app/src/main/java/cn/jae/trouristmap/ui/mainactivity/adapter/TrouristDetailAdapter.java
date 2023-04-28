package cn.jae.trouristmap.ui.mainactivity.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jae.trouristmap.greendao.table.TrouristDetailTable;
import cn.jae.trouristmap.R;

/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 11:10
 * @Description :
 */
public class TrouristDetailAdapter extends RecyclerView.Adapter<TrouristDetailAdapter.ViewHolder> {
    List<TrouristDetailTable> mTrouristListItemBeans;
    Context mContext;
    List<MapView> holderMapViews = new ArrayList<MapView>();
    private OnTrouristDetailItemClickListener mOnTrouristListItemClickListener;
    private Bundle mBundle;

    public TrouristDetailAdapter(List<TrouristDetailTable> trouristListItemBeans, Context context,Bundle bundle) {
        this.mBundle=bundle;
        mTrouristListItemBeans = trouristListItemBeans;
        mContext = context;
    }

    @NonNull
    @Override
    public TrouristDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trourist_detail, parent, false);
        return new ViewHolder(view);
    }

    public List<MapView> getTrouristList() {
        return holderMapViews;
    }

    @Override
    public void onBindViewHolder(@NonNull TrouristDetailAdapter.ViewHolder holder, int position) {
        TrouristDetailTable trouristListItemBean = mTrouristListItemBeans.get(position);
        String latitude = trouristListItemBean.getLatitude();
        String longitude = trouristListItemBean.getLongitude();
        LatLng place = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        if (!holderMapViews.contains(holder.mMapView)) {
            holderMapViews.add(holder.mMapView);
        }
        holder.mMapView.onCreate(mBundle);
        holder.mMapView.onResume();
        AMap map = holder.mMapView.getMap();
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition(place, 18, 0, 30)));
        MarkerOptions defaultMarker = new MarkerOptions().position(place).title(trouristListItemBean.getTargetPlaceName()).snippet("DefaultMarker");
        map.addMarker(defaultMarker);
        map.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ToastUtils.showShort(marker.getTitle());
                return false;
            }
        });
        holder.etTips.setText(trouristListItemBean.getTips());
    }

    @Override
    public int getItemCount() {
        return mTrouristListItemBeans.size();
    }

    public void setBundle(Bundle bundle) {
        this.mBundle=bundle;
    }

    public interface OnTrouristDetailItemClickListener {
        void onTrouristListItemClick(int position);

        void onTrouristListItemLongClick(int position);
    }

    public void setOnTrouristDetailItemClickListener(OnTrouristDetailItemClickListener listener) {
        this.mOnTrouristListItemClickListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mapview)
        MapView mMapView;
        @BindView(R.id.et_tips)
        EditText etTips;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mMapView.getMap().setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (mOnTrouristListItemClickListener != null) {
                        mOnTrouristListItemClickListener.onTrouristListItemClick(getLayoutPosition());
                    }
                }
            });
            mMapView.getMap().setOnMapLongClickListener(new AMap.OnMapLongClickListener() {

                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (mOnTrouristListItemClickListener != null) {
                        mOnTrouristListItemClickListener.onTrouristListItemLongClick(getLayoutPosition());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnTrouristListItemClickListener != null) {
                        mOnTrouristListItemClickListener.onTrouristListItemClick(getLayoutPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnTrouristListItemClickListener != null) {
                        mOnTrouristListItemClickListener.onTrouristListItemLongClick(getLayoutPosition());
                    }
                    return true;
                }
            });
        }
    }
}
