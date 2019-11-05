<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>${ProjectGroupId}</groupId>
    <artifactId>${ProjectArtifactId}</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>${ProjectName}</name>
    <description>SpringCloud 自动生成项目</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>
    </parent>

    <properties>
        <spring-boot.version>2.0.4.RELEASE</spring-boot.version>
        <spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
        <spring-tx.version>5.0.8.RELEASE</spring-tx.version>
        <nacos-spring.version>0.9.0.RELEASE</nacos-spring.version>
        <cucumber.version>1.2.5</cucumber.version>
        <mysql-connector-java.version>5.1.47</mysql-connector-java.version>
        <druid.version>1.1.12</druid.version>
        <mybatis-spring-boot.version>2.0.0</mybatis-spring-boot.version>
        <mapper-spring-boot.version>2.1.3</mapper-spring-boot.version>
        <pagehelper-spring-boot.version>1.2.3</pagehelper-spring-boot.version>
        <mybatis-generator-maven-plugin.version>1.3.7</mybatis-generator-maven-plugin.version>
        <fastjson.version>1.2.55</fastjson.version>
    </properties>

    <dependencies>
        <!-- 业务依赖 -->
        <dependency>
            <groupId>${ProjectGroupId}</groupId>
            <artifactId>${ProjectArtifactId}-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <!-- web项目依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${r'${spring-boot.version}'}</version>
        </dependency>
        <!-- 热部署工具依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <version>${r'${spring-boot.version}'}</version>
            <scope>runtime</scope>
        </dependency>
        <!-- 服务管理依赖 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>${r'${nacos-spring.version}'}</version>
        </dependency>
        <!-- 服务配置依赖 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>${r'${nacos-spring.version}'}</version>
        </dependency>
        <!-- mysql数据库依赖开始 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${r'${mysql-connector-java.version}'}</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>${r'${druid.version}'}</version>
        </dependency>
        <!-- mysql数据库依赖结束 -->
        <!-- mybatis依赖开始 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${r'${mybatis-spring-boot.version}'}</version>
        </dependency>
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>${r'${mapper-spring-boot.version}'}</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>${r'${pagehelper-spring-boot.version}'}</version>
        </dependency>
        <!-- mybatis依赖结束 -->
        <!-- 单元测试依赖开始 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>${r'${spring-boot.version}'}</version>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>com.vaadin.external.google</groupId>
                    <artifactId>android-json</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${r'${spring-tx.version}'}</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${r'${fastjson.version}'}</version>
        </dependency>
        <!-- 单元测试依赖结束 -->
        <!-- Cucumber 测试依赖开始 -->
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${r'${cucumber.version}'}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-spring</artifactId>
            <version>${r'${cucumber.version}'}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>${r'${cucumber.version}'}</version>
            <scope>test</scope>
        </dependency>
        <!-- Cucumber 测试依赖结束 -->
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${r'${spring-boot.version}'}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${r'${spring-cloud.version}'}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <finalName>${r'${project.artifactId}'}</finalName>
        <filters>
            <filter>src/main/resources/bootstrap.properties</filter>
        </filters>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <plugins>
            <!-- spring maven插件 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${r'${spring-boot.version}'}</version>
                <configuration>
                    <classifier>exec</classifier>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <!-- 具体值请根据实际环境进行修改 -->
        <profile>
            <!-- 本地开发环境 -->
            <id>local</id>
            <activation>
                <!-- 设置默认激活这个配置 -->
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <!-- 运行环境名称描述 -->
                <environment>local</environment>
                <!-- Nacos地址 -->
                <nacos.addr>127.0.0.1:8848</nacos.addr>
                <!-- Nacos空间标识 -->
                <nacos.namespace>576f8824-5af7-45ff-97c4-c90080208e1e</nacos.namespace>
            </properties>
        </profile>
        <profile>
            <!-- 开发测试环境 -->
            <id>dev</id>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
            <properties>
                <environment>dev</environment>
                <nacos.addr>127.0.0.1:8848</nacos.addr>
                <nacos.namespace>58911547-2fd5-4253-9b48-bf7fef4a2b87</nacos.namespace>
            </properties>
        </profile>
        <profile>
            <!-- 正式测试环境 -->
            <id>test</id>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
            <properties>
                <environment>test</environment>
                <nacos.addr>127.0.0.1:8848</nacos.addr>
                <nacos.namespace>dc25f0db-9f1d-4ba4-86c7-2e592897c725</nacos.namespace>
            </properties>
        </profile>
        <profile>
            <!-- 生产发布环境 -->
            <id>release</id>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
            <properties>
                <environment>release</environment>
                <nacos.addr>127.0.0.1:8848</nacos.addr>
                <nacos.namespace>4823b8b8-69c5-41ae-918d-80ec4f2ac971</nacos.namespace>
            </properties>
        </profile>
    </profiles>

</project>
