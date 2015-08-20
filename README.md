# JEE RESTful Web Services

RESTful Web 服务的简单实现，目前实现了注册、认证、用户管理等简单功能。

# 截图

![登录界面](http://fh-1.qiniudn.com/jee-restful-web-login.png "登录界面")
![用户管理界面](http://fh-1.qiniudn.com/jee-restful-web.png "用户管理界面")
![行编辑](http://fh-1.qiniudn.com/jee-restful-web-edit-inline.png "行编辑")

# 技术

1. 后端

    使用Maven构建，主要使用的框架、第三方库

    * Spring Framework 4.1
    * Apache Shiro 1.2
    * Spring MVC 4.1
    * MyBatis 3.3
    * Alibaba Druid 1.0
    * Ehcache 2.6
    * SLF4J 1.7、Log4j 2.3
    * Spring HATEOAS 0.17

2. [前端](https://github.com/howiefh/restful-web-app)

    使用Gulp构建，Bower进行包管理，主要使用的框架、第三方库

    * AngularJS
    * Bootstrap
    * UI Bootstrap
    * Font Awesome
    * Restangular
    * ocLazyLoad
    * AngularUI Router
    * Angular-sanitize
    * AngularJS ui-select
    * xtform
    * Angular Loading Bar
    * Angular Chart
    * ngTable
    * angular-jwt
    * angular-local-storage

3. 数据库

    * mysql
    * flyway - 数据库版本管理

# 安装及运行

```
git clone https://github.com/howiefh/jee-restful-web.git
cd jee-restful-web
```

运行前先导入db目录下的数据库

之后执行`mvn tomcat7:run`即可

安装[前端](https://github.com/howiefh/restful-web-app)即可体验目前实现的简单功能

# [API]

## 认证
认证使用 Json Web Token，用户登录时提交用户名和密码，认证成功后会返回一个 *access_token* 以及必要的用户信息，服务器端并不保存状态，客户端保存状态并且之后的每次请求都应该在头部 *Authorization* 携带 *access_token*。

## 缓存
服务器返回资源时会设置`Etag`头部，客户端应该在请求资源时携带`If-None-Match`头部。

## Media Types
使用 [HAL+JSON](https://github.com/mikekelly/hal_specification/blob/master/hal_specification.md) media-type 来表现状态。

请求使用最基本的JSON格式。

## 错误状态码
如果发生错误，可能返回以下状态码：

+ Response 400 请求错误
+ Response 401 未认证
+ Response 404 未找到页面
+ Response 500 服务器错误

## 注册 [/signup]
通过提交用户名、密码、邮箱可以注册一个账号

### 注册一个账号 [POST]
通过提交包含 *username* *password* *email* 字段的json数据可以注册一个账号

+ Request (application/json)

        {
            "username":"test",
            "password":"123456",
            "email":"test@demo.com"
        }

+ Response 201

## 登录 [/login]
通过提交用户名、密码可以登录系统

### 登录一个账号 [POST]
通过提交包含 *username* *password* 字段的json数据可以登录一个账号

+ Request (application/json)

        {
            "username":"test",
            "password":"123456"
        }

+ Response 200

    + Body

            {
                "access_token": "json web token",
                "user": {
                    "id": 8,
                    "username": "test",
                    "roles": ["admin"],
                    "perms": ["user:view","user:update","user:delete","user:create"]
                }
            }

## 用户列表 [/users{?page,size,sort}]
获取所有用户列表

可以有以下参数：

+ page
+ size
+ sort

### 获取用户列表 [GET]
获取所有用户列表

+ Parameters
    + page - 页码，从0开始计数
    + size - 每页显示项数
    + sort - 排序

+ Request

    + Headers

            Authorization: Bearer jwt

+ Response 200

    + Headers

            Etag: "0c78d24e41fec4bfdfa4e34193ca35bc8"

    + Body

            {
                "_links":
                {
                    "self":
                    {
                        "href": "http://localhost:8080/jee-restful-web/users?page=0&size=10&sort=id,asc"
                    },
                    "next":
                    {
                        "href": "http://localhost:8080/jee-restful-web/users?page=1&size=10&sort=id,asc"
                    }
                },
                "_embedded":
                {
                    "api:users":
                    [
                        {
                            "id": 1,
                            "username": "test",
                            "email": "test@demo.com",
                            "mobile": "13100000000",
                            "locked": false,
                            "roles":
                            [
                                {
                                    "id": 1,
                                    "name": "admin",
                                    "cnname": "管理员",
                                    "available": true,
                                    "menus":
                                    [
                                    ]
                                }
                            ],
                            "organizations":
                            [
                                {
                                    "id": 1,
                                    "name": "总部",
                                    "parentId": 0,
                                    "parentIds": "0",
                                    "sort": 0,
                                    "isShow": true
                                }
                            ],
                            "_links":
                            {
                                "self":
                                {
                                    "href": "http://localhost:8080/jee-restful-web/users/1"
                                },
                                "curies":
                                [
                                    {
                                        "href": "http://localhost/rels/{rel}",
                                        "name": "api",
                                        "templated": true
                                    }
                                ]
                            }
                        }
                    ]
                },
                "page":
                {
                    "size": 10,
                    "totalElements": 1,
                    "totalPages": 1,
                    "number": 0
                }
            }


### 删除多个用户 [DELETE]
请求内容为将要删除的用户id数组

+ Request

    + Headers

            Authorization: Bearer jwt

    + Body

            [1,2]

+ Response 204

### 创建用户 [POST]
创建一个新的用户

+ Request (application/json)

    + Headers

            Authorization: Bearer jwt

    + Body

            {
                "username":"test",
                "email":"us@demo.com",
                "mobile":"13245678901",
                "locked":false,
                "roles":[
                    {
                        "id":2,
                        "name":"normal_user"
                    }
                ],
                "organizations":[
                    {
                        "id":1,
                        "name":"总部"
                    }
                ]
            }

+ Response 201

        {
            "id": 1,
            "username": "test",
            "email": "us@demo.com",
            "mobile": "13245678901",
            "locked": false,
            "roles":
            [
                {
                    "id": 2,
                    "name": "normal_user",
                    "cnname": "普通用户",
                    "available": true,
                    "menus":
                    [
                    ]
                }
            ],
            "organizations":
            [
                {
                    "id": 1,
                    "name": "总部",
                    "parentId": 0,
                    "parentIds": "0",
                    "sort": 0,
                    "isShow": true
                }
            ],
            "_links":
            {
                "self":
                {
                    "href": "http://localhost:8080/jee-restful-web/users/1"
                },
                "curies":
                [
                    {
                        "href": "http://localhost/rels/{rel}",
                        "name": "api",
                        "templated": true
                    }
                ]
            }
        }

## 单个用户 [/users/{id}]
获取单个用户

### 获取单个用户 [GET]

+ Request

    + Headers

            Authorization: Bearer jwt

+ Response 200

        {
            "id": 1,
            "username": "test",
            "email": "test@demo.com",
            "mobile": "13100000000",
            "locked": false,
            "roles":
            [
                {
                    "id": 1,
                    "name": "admin",
                    "cnname": "管理员",
                    "available": true,
                    "menus":
                    [
                        {
                            "id": 5,
                            "permission": "user:delete"
                        },
                        {
                            "id": 4,
                            "permission": "user:update"
                        },
                        {
                            "id": 3,
                            "permission": "user:create"
                        },
                        {
                            "id": 2,
                            "permission": "user:view"
                        },
                        {
                            "id": 1,
                            "permission": "user"
                        }
                    ]
                }
            ],
            "organizations":
            [
                {
                    "id": 1,
                    "name": "总部",
                    "parentId": 0,
                    "parentIds": "0",
                    "sort": 0,
                    "isShow": true
                }
            ],
            "_links":
            {
                "self":
                {
                    "href": "http://localhost:8080/jee-restful-web/users/1"
                },
                "curies":
                [
                    {
                        "href": "http://localhost/rels/{rel}",
                        "name": "api",
                        "templated": true
                    }
                ]
            }
        }

### 更新用户 [PUT]
更新一个用户，可以只提交部分数据

+ Request (application/json)

    + Headers

            Authorization: Bearer jwt

    + Body

            {
                "id":1,
                "username":"test",
                "email":"us@demo.com",
                "mobile":"13245678901",
                "locked":false,
                "roles":[
                    {
                        "id":2,
                        "name":"normal_user"
                    }
                ],
                "organizations":[
                    {
                        "id":1,
                        "name":"总部"
                    }
                ]
            }

+ Response 200

        {
            "id": 1,
            "username": "test",
            "email": "us@demo.com",
            "mobile": "13245678901",
            "locked": false,
            "roles":
            [
                {
                    "id": 2,
                    "name": "normal_user",
                    "cnname": "普通用户",
                    "available": true,
                    "menus":
                    [
                    ]
                }
            ],
            "organizations":
            [
                {
                    "id": 1,
                    "name": "总部",
                    "parentId": 0,
                    "parentIds": "0",
                    "sort": 0,
                    "isShow": true
                }
            ],
            "_links":
            {
                "self":
                {
                    "href": "http://localhost:8080/jee-restful-web/users/1"
                },
                "curies":
                [
                    {
                        "href": "http://localhost/rels/{rel}",
                        "name": "api",
                        "templated": true
                    }
                ]
            }
        }

### 删除用户 [DELETE]
+ Response 204

[API]: http://docs.jeerestfulweb.apiary.io
