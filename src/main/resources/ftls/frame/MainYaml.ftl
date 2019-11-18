#采用Nacos注册中心后，默认空间为public，默认分组为DEFAULT_GROUP，默认对应配置文件为${r'${spring.application.name}}'}.yaml
#Nacos注册中心需要自行安装，官方测试地址为：http://console.nacos.io:80  账号：nacos  密码：nacos
#可提供更改的配置可以写在Nacos上，一般以空间区分运行环境，切换空间即可切换配置

server:
    #端口号
    port: ${(IsLocal)?string("8080","'${r'${server.port}'}'")}
spring:
    application:
        #服务名称，同一注册中心下不能重复
        name: ${ProjectArtifactId}
    cloud:
        nacos:
            discovery:
                #服务注册中心指向，根据pom文件中环境变量切换
                server-addr: ${r'@nacos.addr@'}
    datasource:
        #数据库地址
        url: ${(IsLocal)?string("${DataBaseUrl}","${r'${spring.datasource.url}'}")}
        #数据库用户名
        username: ${(IsLocal)?string("${DataBaseUserName}","${r'${spring.datasource.username}'}")}
        #数据库密码
        password: ${(IsLocal)?string("${DataBasePassword}","${r'${spring.datasource.password}'}")}
        #数据库初始连接数，初始值建议设为1，根据实际情况调整
        initial-size: ${(IsLocal)?string("1","${r'${spring.datasource.initial-size}'}")}
        #数据库最大空闲连接数，超出连接将被释放，设置为0表示没有限制，初始值建议设为5，根据实际情况调整
        maxIdle: ${(IsLocal)?string("5","${r'${spring.datasource.maxIdle}'}")}
        #数据库最大连接数，设为0表示没有限制，初始值建议设为20，根据实际情况调整
        max-active: ${(IsLocal)?string("20","${r'${spring.datasource.max-active}'}")}
        #数据库超时设置，以ms为单位，设为-1表示没有限制，初始值建议设为60000，根据实际情况调整
        max-wait: ${(IsLocal)?string("60000","${r'${spring.datasource.max-wait}'}")}
        driver-class-name: com.mysql.jdbc.Driver
        filters: stat
        pool-prepared-statements: true
        max-open-prepared-statements: 20
        test-on-return: false
        test-on-borrow: false
        test-while-idle: true
        validation-query: select 'x'
        min-evictable-idle-time-millis: 300000
        time-between-eviction-runs-millis: 60000
        #使用druid数据源
        type: com.alibaba.druid.pool.DruidDataSource
    redis:
        #缓存数据库地址
        host: ${(IsLocal)?string("127.0.0.1","${r'${spring.redis.host}'}")}
        #缓存数据库端口
        port: ${(IsLocal)?string("6379","${r'${spring.redis.port}'}")}
        #缓存数据库密码
        password: ${(IsLocal)?string("","${r'${spring.redis.password}'}")}
        #缓存数据库名称
        database: ${(IsLocal)?string("0","${r'${spring.redis.database}'}")}
        #缓存数据库超时设置，初始值建议设为60s
        timeout: ${(IsLocal)?string("60s","${r'${spring.redis.timeout}'}")}
        jedis:
            pool:
                max-idle: 50
                min-idle: 5
                max-wait: -1s
                max-active: -1
    transaction:
        rollback-on-commit-failure: true

feign:
    hystrix:
        enabled: true

#ribbon请求连接的超时时间
ribbon:
    ConnectTimeout: 10000
    #请求处理的超时时间
    ReadTimeout: 10000
    #对所有请求操作都进行重试
    OkToRetryOnAllOperations: true
    #对当前服务的重试次数（第一次分配给9082的时候，如果404，则再重试MaxAutoRetries次，如果还是404，则切换到其他服务MaxAutoRetriesNextServer决定）
    MaxAutoRetries: 0
    #切换服务的次数(比如本次请求分配给9082处理，发现404，则切换分配给9081处理，如果还是404，则返回404给客户端）
    MaxAutoRetriesNextServer: 0

#mybatis中pagehelper分页插件
pagehelper:
    helperDialect: mysql
    reasonable: true
    supportMethodsArguments: true
    params: count=countSql

#mybatis通用mapper
mapper:
    identity: MYSQL
    mappers: tk.mybatis.mapper.common.BaseMapper
    #设置insert和update中，是否判断字符串类型!=''
    not-empty: true
    #枚举按简单类型处理
    enum-as-simple-type: true
