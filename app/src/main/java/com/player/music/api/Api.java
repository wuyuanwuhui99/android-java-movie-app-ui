package com.player.music.api;

public class Api {
    public static final String GETKEYWORDMUSIC = "/service/myMusic/getKeywordMusic";//获取搜索关键词
    public static final String GETMUSICCLASSIFY = "/service/myMusic/getMusicClassify";//获取分类歌曲
    public static final String GETMUSICLISTBYCLASSIFYID = "/service/myMusic/getMusicListByClassifyId";//获取推荐音乐列表
    public static final String GETSINGERLIST= "/service/myMusic/getSingerList";// 获取歌手列表
    public static final String GETCIRCLELISTBYTYPE= "/service/circle/getCircleListByType";// 获取歌手列表
    public static final String GETMUSICPLAYMENU= "/service/myMusic-getway/getMusicPlayMenu";// 获取我的歌单
    public static final String GETMYSINGER= "/service/myMusic-getway/getMySinger";// 获取我关注的歌手
    public static final String GETMUSICRECORD= "/service/myMusic-getway/getMusicRecord";// 获取播放记录
    public static final String INSERTMUSICRECORD= "/service/myMusic-getway/insertLog";// 记录播放日志
    public static final String INSERTMUSICFAVORITE= "/service/myMusic-getway/insertMusicFavorite";// 插入收藏
    public static final String DELETEMUSICFAVORITE= "/service/myMusic-getway/deleteMusicFavorite/";// 删除收藏
    public static final String QUERYMUSICFAVORITE= "/service/myMusic-getway/queryMusicFavorite";// 查询收藏
    public static final String SEARCHMUSIC= "/service/myMusic/searchMusic";// 音乐搜索
    public static final String GETSINGERCATEGORY="/service/myMusic/getSingerCategory";// 获取歌手分类
    public static final String SAVELIKE="/service/social-getway/saveLike";// 添加点赞
    public static final String DELETELIKE="/service/social-getway/deleteLike";// 删除点赞
    public static final String INSERTCOMMENT="/service/social-getway/insertComment";// 新增评论
}
