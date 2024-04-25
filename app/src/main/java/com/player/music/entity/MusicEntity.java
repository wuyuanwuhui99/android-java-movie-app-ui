package com.player.music.entity;

import java.util.Date;

public class MusicEntity {
    private Long id;//主键
    private Long albumId;// 专辑id
    private String songName;// 歌曲名称
    private String authorName;// 歌手名称
    private Long authorId;// 歌手id
    private String albumName;// 专辑
    private String version;// 版本
    private String language;// 语言
    private Date publishDate;// 发布时间
    private Long wideAudioId;// 未使用字段
    private Long isPublish;// 是否发布
    private Long bigPackId;// 未使用字段
    private Long finalId;// 未使用字段
    private Long audioId;// 音频id
    private Long similarAudioId;// 未使用字段
    private int isHot;// 是否热门
    private Long albumAudioId;// 音频专辑id
    private Long audioGroupId;// 歌曲组id
    private String cover;// 歌曲图片
    private String playUrl;// 网络播放地址
    private String localPlayUrl;// 本地播放地址
    private String sourceName;// 歌曲来源
    private String sourceUrl;// 来源地址
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String label;// 标签
    private String lyrics;// 歌词
    private int permission;// 播放权限
    private int isFavorite;// 是否是喜欢，0表示不在喜欢的列表中，1表示在喜欢的列表中
    private int times;// 听过的次数，在获取播放记录的时候才有

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Long getWideAudioId() {
        return wideAudioId;
    }

    public void setWideAudioId(Long wideAudioId) {
        this.wideAudioId = wideAudioId;
    }

    public Long getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Long isPublish) {
        this.isPublish = isPublish;
    }

    public Long getBigPackId() {
        return bigPackId;
    }

    public void setBigPackId(Long bigPackId) {
        this.bigPackId = bigPackId;
    }

    public Long getFinalId() {
        return finalId;
    }

    public void setFinalId(Long finalId) {
        this.finalId = finalId;
    }

    public Long getAudioId() {
        return audioId;
    }

    public void setAudioId(Long audioId) {
        this.audioId = audioId;
    }

    public Long getSimilarAudioId() {
        return similarAudioId;
    }

    public void setSimilarAudioId(Long similarAudioId) {
        this.similarAudioId = similarAudioId;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public Long getAlbumAudioId() {
        return albumAudioId;
    }

    public void setAlbumAudioId(Long albumAudioId) {
        this.albumAudioId = albumAudioId;
    }

    public Long getAudioGroupId() {
        return audioGroupId;
    }

    public void setAudioGroupId(Long audioGroupId) {
        this.audioGroupId = audioGroupId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getLocalPlayUrl() {
        return localPlayUrl;
    }

    public void setLocalPlayUrl(String localPlayUrl) {
        this.localPlayUrl = localPlayUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
