package com.player.movie.entity;

public class MovieUrlEntity {
    private int id;//主键
    private String movieName;//电影名称
    private Long movieId;//对应的电影的id
    private String href;//源地址
    private String label;//集数
    private String createTime;//创建时间
    private String updateTime;//播放地址
    private String url;//播放地址
    private int playGroup;//播放分组，1, 2

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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPlayGroup() {
        return playGroup;
    }

    public void setPlayGroup(int playGroup) {
        this.playGroup = playGroup;
    }
}
