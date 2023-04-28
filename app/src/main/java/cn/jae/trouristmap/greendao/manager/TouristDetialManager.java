package cn.jae.trouristmap.greendao.manager;

import android.util.Log;

import com.blankj.utilcode.util.TimeUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.jae.trouristmap.base.App;
import cn.jae.trouristmap.greendao.dao.TrouristDetailTableDao;
import cn.jae.trouristmap.greendao.table.TrouristDetailTable;

/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 15:35
 * @Description :
 */
public class TouristDetialManager {
    private TouristDetialManager() {
    }

    private static class TouristDetialManagerHolder {
        private static final TouristDetialManager INSTANCE = new TouristDetialManager();
    }

    public static TouristDetialManager getInstance() {
        return TouristDetialManagerHolder.INSTANCE;
    }

    public TrouristDetailTableDao getMessageDao() {
        return App.getDaoSession().getTrouristDetailTableDao();
    }

    public List<TrouristDetailTable> getToruristDetailListByPlanName(String planName) {
        Log.d("TouristDetialManager", "getToruristDetailListByPlanName(TouristDetialManager.java:34)" + planName);
        List<TrouristDetailTable> list = getMessageDao().queryBuilder().where(TrouristDetailTableDao.Properties.PlanName.eq(planName)).list();
        Collections.sort(list, new Comparator<TrouristDetailTable>() {
            @Override
            public int compare(TrouristDetailTable o1, TrouristDetailTable o2) {
                if (TimeUtils.string2Millis(o2.getLastModifiedDate()) > TimeUtils.string2Millis(o1.getLastModifiedDate())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return list;
    }

    public void updateTouristDetail(TrouristDetailTable trouristDetailTable) {
        getMessageDao().update(trouristDetailTable);
    }

    public void deleteTouristDetail(TrouristDetailTable trouristDetailTable) {
        getMessageDao().delete(trouristDetailTable);
    }

    public void insertTouristDetail(TrouristDetailTable trouristDetailTable) {
        getMessageDao().insertOrReplace(trouristDetailTable);
    }

}
