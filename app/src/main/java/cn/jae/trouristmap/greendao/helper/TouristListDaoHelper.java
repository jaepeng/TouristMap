package cn.jae.trouristmap.greendao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.jae.trouristmap.greendao.dao.DaoMaster;

/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 13:59
 * @Description :
 */
public class TouristListDaoHelper extends DaoMaster.DevOpenHelper {
    public TouristListDaoHelper(Context context, String name) {
        super(context, name);
    }

    public TouristListDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DaoMaster.dropAllTables(wrap(db), true);
    }
}
