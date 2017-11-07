package com.iplay.iplaychat.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cheergoivan on 2017/7/26.
 */

public abstract class BaseDAO {

    public abstract void createTableIfNotExists();

}
