package com.yfny.utilscommon.generator.entity;

import java.io.Serializable;

/**
 * 代码生成器参数配置类
 * Created by jisongZhou on 2019/3/5.
 **/
public class Configuration implements Serializable {
    private String author;
    private String packageName;
    private String entityPackageName;//新增属性--实体对象包名
    private Path path;
    private Db db;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPackageName() {
        return packageName == null ? "" : packageName + ".";
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEntityPackageName() {
        return entityPackageName == null ? "" : entityPackageName + ".";
    }

    public void setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Db getDb() {
        return db;
    }

    public void setDb(Db db) {
        this.db = db;
    }

    public static class Db {
        private String url;
        private String username;
        private String password;

        public Db() {
        }

        public Db(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Path {
        private String controller;
        private String service;
        private String entity;
        private String mapper;
        private String future;//新增属性--组合式异步线程
        private String composite;//新增属性--组合模式构件
        private String sqlbuilder;//新增属性--sql语句构造器
        private String hystrix;//新增属性--熔断器
        private String basetest;//新增属性--单元测试基类
        private String unittest;//新增属性--单元测试

        public Path() {
        }

        public Path(String controller, String service, String entity, String mapper, String future, String composite, String sqlbuilder, String hystrix, String basetest, String unittest) {
            this.controller = controller;
            this.service = service;
            this.entity = entity;
            this.mapper = mapper;
            this.future = future;
            this.composite = composite;
            this.sqlbuilder = sqlbuilder;
            this.hystrix = hystrix;
            this.basetest = basetest;
            this.unittest = unittest;
        }

        public String getController() {
            return controller == null ? "" : controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }

        public String getService() {
            return service == null ? "" : service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getEntity() {
            return entity == null ? "" : entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getMapper() {
            return mapper == null ? "" : mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }

        public String getFuture() {
            return future;
        }

        public void setFuture(String future) {
            this.future = future;
        }

        public String getComposite() {
            return composite;
        }

        public void setComposite(String composite) {
            this.composite = composite;
        }

        public String getSqlbuilder() {
            return sqlbuilder == null ? "" : sqlbuilder;
        }

        public void setSqlbuilder(String sqlbuilder) {
            this.sqlbuilder = sqlbuilder;
        }

        public String getHystrix() {
            return hystrix;
        }

        public void setHystrix(String hystrix) {
            this.hystrix = hystrix;
        }

        public String getBasetest() {
            return basetest == null ? "" : basetest;
        }

        public void setBasetest(String basetest) {
            this.basetest = basetest;
        }

        public String getUnittest() {
            return unittest == null ? "" : unittest;
        }

        public void setUnittest(String unittest) {
            this.unittest = unittest;
        }
    }

}
