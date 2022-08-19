package com.player.movie.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.player.movie.dao.SearchWordDao;
import com.player.movie.entity.SearchWordEntity;
import com.player.movie.utils.Converters;

/**
 * @author: wuwenqiang
 * @description: 生成实体类和表结构
 * @date: 2022-08-10 21:02
 */
@Database(entities = {SearchWordEntity.class},version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SearchWordDatabase extends RoomDatabase {
    // 获取该数据库中某张表的持久化对象
    public abstract SearchWordDao searchWordDao();
    // 单例
    private SearchWordDatabase database;
    public SearchWordDatabase getInstance(Context context){
        if (database == null){
            synchronized (SearchWordDatabase.class){
                if (database == null){
                    database = Room.databaseBuilder(context.getApplicationContext(),SearchWordDatabase.class,"search.db").build();
                }
            }
        }
        return database;
    }
}
