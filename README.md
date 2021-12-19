# flutter电影APP

开发者：吴怨吴悔

=============================界面预览（如果无法预览，请查看项目根目录png文件）==========================

![app首页](./%E9%A2%84%E8%A7%881.png)
![app首页](./%E9%A2%84%E8%A7%882.png)
![app首页](./%E9%A2%84%E8%A7%883.png)
![app首页](./%E9%A2%84%E8%A7%884.png)
![app首页](./%E9%A2%84%E8%A7%885.png)
=============================界面预览（如果无法预览，请查看项目根目录png文件）==========================

所有影片数据来自第三方电影网站，使用python爬虫实时抓取，然后存入到自己的mysql数据库中，所有接口均采用java开发

后端接口项目和sql语句：https://github.com/wuyuanwuhui99/springboot-app-service

flutter版本参见：https://github.com/wuyuanwuhui99/flutter-movie-app-ui

react native版本参见: https://github.com/wuyuanwuhui99/react-native-app-ui

vue在线音乐项目：https://github.com/wuyuanwuhui99/vue-music-app-ui

在线音乐后端项目：https://github.com/wuyuanwuhui99/koa2-music-app-service

vue3+ts明日头条项目：https://github.com/wuyuanwuhui99/vue3-ts-toutiao-app-ui

nginx配置

    server{
        listen       5001;
        location /service/ {
            proxy_pass http://127.0.0.1:5000;
        }
        location /static/ {
            alias E:/static/;
        }
    }
