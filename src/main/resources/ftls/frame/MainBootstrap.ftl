#@nacos.addr@、@nacos.namespace@配置请在pom文件中切换，便于环境动态配置

#指定配置服务中心的网址，官方测试地址为：http://console.nacos.io:80  账号：nacos  密码：nacos
spring.cloud.nacos.config.server-addr=${r'@nacos.addr@'}
spring.cloud.nacos.discovery.server-addr=${r'@nacos.addr@'}

#指定配置服务中心的命名空间，默认空间为 public(一般用于区分环境)
spring.cloud.nacos.config.namespace=${r'@nacos.namespace@'}
spring.cloud.nacos.discovery.namespace=${r'@nacos.namespace@'}

#指定配置服务中心的分组，默认分组为 DEFAULT_GROUP（一般用于区分业务）
#spring.cloud.nacos.config.group=DEFAULT_GROUP

#指定服务名称
spring.application.name=${ProjectArtifactId}

#支持加载多配制文件，默认加载的配置文件为${r'${spring.application.name}}'}.yaml，如需加载其他配置文件需要在这里定义
#spring.cloud.nacos.config.shared-dataids=example1.properties,example2.properties
#spring.cloud.nacos.config.refreshable-dataids=example1.properties,example2.properties

#指定配置的后缀，支持 properties、yaml、yml，默认为 properties
spring.cloud.nacos.config.file-extension=yaml
