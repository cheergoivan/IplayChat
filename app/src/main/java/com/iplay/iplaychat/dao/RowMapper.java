package com.iplay.iplaychat.dao;

import android.database.Cursor;

/**
 * Created by cheergoivan on 2017/7/26.
 */

public interface RowMapper<T> {
    T map(Cursor cursor);
}
