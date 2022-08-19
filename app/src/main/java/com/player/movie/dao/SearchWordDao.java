package com.player.movie.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.player.movie.entity.SearchWordEntity;

import java.util.List;

@Dao
public interface SearchWordDao {

    @Insert
    void insert(SearchWordEntity...searchWordEntity);

    @Delete
    void delete(SearchWordEntity...searchWordEntity);

    @Update
    int update(SearchWordEntity...searchWordEntity);

    @Query("SELECT * FROM search_word")
    List<SearchWordEntity> query();
}
