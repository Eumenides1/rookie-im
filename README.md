# 菜鸟IM 平台
## 接口文档（线上）
- [应用侧接口文档](http://106.54.240.161:8080/rookie/v1/doc.html#/)
- [平台侧接口文档](http://106.54.240.161:8000/rookie/v1/doc.html#/)
## 代码结构
```
.
├── README.md
├── pom.xml
├── rookie-im-common                     项目通用工具方法及常量（规范）
├── rookie-im-platform                   平台侧服务代码（后续考虑微服务拆分）
├── rookie-im-service                    IM 应用侧服务代码
├── rookie-im-tools                      常用工具 starter
│       ├── pom.xml
│       └── rookie-message-push-starter  通用的消息推送 starter，目前实现邮箱推送，提供通用接口，可扩展短信，钉钉等通知
└── scripts
    ├── deploy                           ssh 部署脚本
    ├── hooks                            githook 文件
    ├── init
    └── sql                              渐进开发项目 sql 文件

```
## 开发日志
- 12.28
  - 完成项：
    - 平台侧：主账号授权、主账号权限配置、aksk 生成
    - 应用侧：新建 app
  - 待办：
    - 应用侧：新建 app 配置解析需后续配置确定后解析到特定类
    - 应用侧：新建 APP 选择 ai 应用时 apikey 与 secret 的管理
- 12.29
  - 完成项：  
    - 应用侧：完成好友关系库表结构设计、发起好友申请 api 开发完成、修复用户相关 bug
  - 待办：
    - 用户关系的验证在 IM 系统里的最佳实践