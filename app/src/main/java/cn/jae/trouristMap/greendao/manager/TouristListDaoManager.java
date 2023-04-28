package cn.jae.trouristMap.greendao.manager;

import com.blankj.utilcode.util.TimeUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.jae.trouristMap.base.App;
import cn.jae.trouristMap.greendao.dao.TrouristListTableDao;
import cn.jae.trouristMap.greendao.table.TrouristListTable;

/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 14:07
 * @Description :
 */
public class TouristListDaoManager {
    private TouristListDaoManager() {
    }

    private static class TouristListDaoManagerHolder {
        private static final TouristListDaoManager INSTANCE = new TouristListDaoManager();
    }

    public static TouristListDaoManager getInstance() {
        return TouristListDaoManagerHolder.INSTANCE;
    }

    public TrouristListTableDao getMessageDao() {
        return App.getDaoSession().getTrouristListTableDao();
    }

    /**
     * 按时间倒序排
     *
     * @return
     */
    public List<TrouristListTable> getAllTrouristListSortByTime() {
        List<TrouristListTable> trouristListTables = getMessageDao().loadAll();
        Collections.sort(trouristListTables, new Comparator<TrouristListTable>() {
            @Override
            public int compare(TrouristListTable o1, TrouristListTable o2) {
                if (TimeUtils.string2Millis(o2.getTrouristModifyTime()) > TimeUtils.string2Millis(o1.getTrouristModifyTime())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return trouristListTables;
    }


    public void updateTrouristPosition(String name, int position) {
        TrouristListTable trouristBeanByName = getTrouristBeanByName(name);
        if (trouristBeanByName != null) {
            trouristBeanByName.setPosition(position);
            getMessageDao().update(trouristBeanByName);
        }
    }

    public long insertTrourist(TrouristListTable trouristListTable) {
        return getMessageDao().insertOrReplace(trouristListTable);
    }

    public void delete(TrouristListTable trouristListTable) {
        getMessageDao().delete(trouristListTable);
    }

    public void deleteByName(String name) {
        TrouristListTable trouristBeanByName = getTrouristBeanByName(name);
        getMessageDao().delete(trouristBeanByName);
    }

    /**
     * 名字作为唯一标识，不应当进行修改
     *
     * @param olderName
     * @param newName
     * @deprecated
     */
    public void updateTrouristName(String olderName, String newName) {
        TrouristListTable trouristBeanByName = getTrouristBeanByName(olderName);
        if (trouristBeanByName != null) {
            trouristBeanByName.setTrouristName(newName);
            getMessageDao().update(trouristBeanByName);
        }
    }

    public TrouristListTable getTrouristBeanByName(String name) {
        TrouristListTable trouristListTable = getMessageDao().queryBuilder().where(TrouristListTableDao.Properties.TrouristName.eq(name)).unique();
        return trouristListTable;
    }
}
