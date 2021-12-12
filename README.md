# flutter电影APP

开发者：吴怨吴悔

=============================界面预览（如果无法预览，请查看项目根目录png文件）==========================
![app首页](./%E9%A6%96%E9%A1%B5%E9%A2%84%E8%A7%881.png)
![app首页](./%E9%A6%96%E9%A1%B5%E9%A2%84%E8%A7%882.png)
![app首页](./%E9%A6%96%E9%A1%B5%E9%A2%84%E8%A7%883.png)
![app首页](./%E9%A6%96%E9%A1%B5%E9%A2%84%E8%A7%884.png)
=============================界面预览（如果无法预览，请查看项目根目录png文件）==========================

运行命令：flutter run --enable-software-rendering

## 使用的插件

轮播：flutter_swiper: ^1.0.6

网络请求：dio: ^3.0.10

屏幕相关：flutter_screenutil: ^0.5.1

状态管理器: provider: ^3.1.0+1	

下拉加载：flutter_easyrefresh: ^2.1.5

提示组件fluttertoast: ^3.0.3

缓存：shared_preferences: ^0.4.1

webview：webview_flutter: ^0.3.10+4

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
