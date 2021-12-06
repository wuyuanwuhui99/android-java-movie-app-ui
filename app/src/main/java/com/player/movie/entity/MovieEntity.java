package com.player.movie.entity;

import java.util.Date;

public class MovieEntity {
    private Long id;//主键
    private Long movieId;
    private String director;//导演
    private String star;//主演
    private String type;//类型
    private String countryLanguage;//国家/语言
    private String viewingState;//观看状态
    private String releaseTime;//上映时间
    private String plot;//剧情
    private Date updateTime;//更新时间
    private String movieName;//电影名称
    private String isRecommend;//是否推荐，0:不推荐，1:推荐
    private String img;//电影海报
    private String classify;//分类 电影,电视剧,动漫,综艺,新片库,福利,午夜,恐怖,其他
    private String sourceName;//来源名称，本地，骑士影院，爱奇艺
    private String sourceUrl;//来源地址
    private Date createTime;//创建时间
    private String localImg;//本地图片
    private String label;//播放集数
    private String originalHref;//源地址
    private String description;//简单描述
    private String targetHref;//链接地址
    private String useStatus;//0代表未使用，1表示正在使用，是banner和carousel图的才有
    private Double score;//评分
    private String category;//类目，值为banner首屏，carousel：滚动轮播
    private String ranks;//排名
    private String userId;//用户名，这这个表不需要，为了跟记录叫和收藏表的结构一致',
    private String doubanUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountryLanguage() {
        return countryLanguage;
    }

    public void setCountryLanguage(String countryLanguage) {
        this.countryLanguage = countryLanguage;
    }

    public String getViewingState() {
        return viewingState;
    }

    public void setViewingState(String viewingState) {
        this.viewingState = viewingState;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
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

    public String getLocalImg() {
        return localImg;
    }

    public void setLocalImg(String localImg) {
        this.localImg = localImg;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOriginalHref() {
        return originalHref;
    }

    public void setOriginalHref(String originalHref) {
        this.originalHref = originalHref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetHref() {
        return targetHref;
    }

    public void setTargetHref(String targetHref) {
        this.targetHref = targetHref;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRanks() {
        return ranks;
    }

    public void setRanks(String ranks) {
        this.ranks = ranks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoubanUrl() {
        return doubanUrl;
    }

    public void setDoubanUrl(String doubanUrl) {
        this.doubanUrl = doubanUrl;
    }
}
