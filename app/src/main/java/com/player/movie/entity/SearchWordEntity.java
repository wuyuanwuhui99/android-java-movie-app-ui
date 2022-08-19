package com.player.movie.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "search_word")
public class SearchWordEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_name")
    private String movieName;

    @ColumnInfo(name = "classify")
    private String classify;

    @ColumnInfo(name = "creat_time")
    private Date creatTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public SearchWordEntity(int id, String movieName, String classify, Date creatTime) {
        this.id = id;
        this.movieName = movieName;
        this.classify = classify;
        this.creatTime = creatTime;
    }
}
