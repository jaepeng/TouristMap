package cn.jae.trouristMap.ui.mainactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jae.trouristMap.R;
import cn.jae.trouristMap.greendao.table.TrouristListTable;

/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 11:10
 * @Description :
 */
public class TrouristListAdapter extends RecyclerView.Adapter<TrouristListAdapter.ViewHolder> {
    List<TrouristListTable> mTrouristListItemBeans;
    Context mContext;
    private OnTrouristListItemClickListener mOnTrouristListItemClickListener;

    public TrouristListAdapter(List<TrouristListTable> trouristListItemBeans, Context context) {
        mTrouristListItemBeans = trouristListItemBeans;
        mContext = context;
    }

    @NonNull
    @Override
    public TrouristListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trourist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrouristListAdapter.ViewHolder holder, int position) {
        TrouristListTable trouristListItemBean = mTrouristListItemBeans.get(position);
        holder.tvTrouristName.setText(trouristListItemBean.getTrouristName());
    }

    @Override
    public int getItemCount() {
        return mTrouristListItemBeans.size();
    }

    public interface OnTrouristListItemClickListener {
        void onTrouristListItemClick(int position);

        void onTrouristListItemLongClick(int position);
    }

    public void setOnTrouristListItemClickListener(OnTrouristListItemClickListener listener) {
        this.mOnTrouristListItemClickListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_trouist_name)
        TextView tvTrouristName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
