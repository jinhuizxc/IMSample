package com.example.imsample.framework.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.imsample.dao.DaoMaster;
import com.example.imsample.dao.DaoSession;

/**
 * Created by wapchief on 2017/7/14.
 * 数据库初始化的辅助类
 */

public class GreenDaoHelper {

    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;


    public GreenDaoHelper(Context context) {
        this.context = context;
    }

    public DaoSession initDao(){
        helper = new DaoMaster.DevOpenHelper(context, "test", null);
        db= helper.getWritableDatabase();
        daoMaster= new DaoMaster(db);
        daoSession= daoMaster.newSession();
        return daoSession;
    }
}
