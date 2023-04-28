package cn.jae.trouristmap.base;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import cn.jae.trouristmap.greendao.dao.DaoMaster;
import cn.jae.trouristmap.greendao.dao.DaoSession;


/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 13:51
 * @Description :
 */
public class App extends Application {
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase();
    }

    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "tourist.db",
                null);
        //获取可写数据库
        Database db = helper.getWritableDb();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
