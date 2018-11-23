# pesonal
个人主页搭建

## 使用其他WEB服务器
  spring boot 内置Tomcat,同时还支持Jetty、Undertow作为web服务器

  **Undertow :**
  ```
  <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-undertow</artifactId>
  </dependency>
  ```

  **Jetty ：**

  ```
    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-jetty</artifactId>
    </dependency>
  ```

  **同时，还需要在Spring-boot-starter-web中去除Tomcat依赖 ：**

  ```
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
      <exclusion>
        <artifactId>org.springframework.boot</artifactId>
        <groupId>spring-boot-starter-tomcat</groupId>
      </exclusion>
    </exclusions>
  </dependency>
  ```
其中Undertow性能优于tomcat和jetty,推荐使用Undertow作为SpringBoot应用服务器

## 修改控制台的输出信息：

1、可以在Spring Boot项目的resources下新建一个banner.txt文件，也可以为banner.png(gif、jpg)

2、关于banner的配置：

`banner.location=classpath:banner.txt`

**如果使用图片，图片位置可以使用jpg/png**

`banner.image.location=classpath:banner.gif`

**图片宽度，这里指转为字符的个数，越多越清楚**

`banner.image.width=76`

**图片长度**

`banner.image.height=76`

**图片与左边的边距，默认为2个字符**

`banner.image.margin=2`

## 配置浏览器显示的ico

1、更换为自定义的需要在resources下创建static/images目录，然后放入自己的图片

**使用：**

`<link rel="shortcut icon" href="/images/自定义的图片"`

## 日志配置

**指定对应包下面的日志级别**

`logging.level.org=info`

`logging.level.root=info`

`logging.level.com.yourpage=info`

**指定日志输出到文件中，默认未设置**

`logging.file=mylog.log`

**指定日志存放目录，默认该文件存放在spring boot应用运行的当前目录下**

`logging.path=d:/temp/log`

当日志文件达到10MB时，会自动重新生成一个新日志文件


## 多环境部署
 **备注**
先注释了devtools依赖

##  读取配置文件的值

### Placeholder方式 ： ${...}

@Value("${}")

### 在配置中，各参数之间可以使用Placeholder表达式来调用

例如：

```
book.auth=lidar
book.name=sea
book.desc=${book.auth} born in USA
```

### 生成随机数

**随机字符**

`my.value=${random.value}`

**随机int**

`my.number=${random.int}`

**随机long**

`my.bignumber=${random.long}`

**10以内的随机数**

`my.number10=${random.int(10)}`

**10-20的随机数**

`my.number20=${random.int(10,20)}`

## RestTemplate

### getForEntity(URI url,Class responseType);

生成URI:

例：
```
UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://USER_SERVICE/user?name={name}")
                .build()
                .expand("lala")
                .encode();

URI uri = uriComponents.toUri();

restTemplate.getForEntity(uri,User.class);
```


## AOP 表达式

`execution(public * *(..))`

所有的public方法，后面的*号代表类路径和方法名

`execution(* set*(..))`

所有set开头的方法

`execution(public set*(..))`

所有set开头的public方法

`execution(public com.garyhu.service.* set*(..))`

所有set开头的public方法，且位于com.garyhu.service包下

`target(com.garyhu.service.CommonService)`

所有实现了CommonService接口的类的方法

`@target(org.springframework.transaction.annotation.Transaction)`

所有用@Transaction注解的方法

`@within(org.springframework.stereotype.Controller)`

类声明了@Controller的所有方法



## spring boot 2.0

使用配置的datasource报错，现在是自定义的数据，然后通过类来自动加载，url一定要写成jdbc-url

## session + Redis + nginx 设置session共享

### 1、安装nginx

设置代理，如下：

```
upstream backend {
     server 127.0.0.1:8099;
     server 127.0.0.1:8077;
}

...

location / {
     ...
     proxy_pass http://backend;
}
```

### 2、Spring boot：

**依赖**

```
    <!--Redis-->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

    <!--session  通过Redis来实现session共享-->
    <dependency>
      <groupId>org.springframework.session</groupId>
      <artifactId>spring-session-data-redis</artifactId>
    </dependency>
```

**配置**

在application.yml中

```
spring.session.store-type=Redis

spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=xxxxxx
spring.redis.timeout=3000     # 小于等于0的时候Spring boot无法启动
spring.redis.pool.max-active=8 # 0表示无限制
spring.redis.pool.max-idle=8
spring.redis.pool.min-idle=0
spring.redis.pool.max-wait=-1
```

添加配置类RedisSessionConfig

在UserController的`/signin`接口测试登录保存session
`/getsession`测试session是否共享

