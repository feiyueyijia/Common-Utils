package com.yfny.utilscommon.generator.entity;

import com.yfny.utilscommon.util.StringUtils;

import java.io.Serializable;

/**
 * 代码生成器参数配置类
 * Created by jisongZhou on 2019/3/5.
 **/
public class Configuration implements Serializable {
    private String author;
    private String packageName;
    private String projectName;
    private String filePath;
    private Path path;
    private Db db;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPackageName() {
        return packageName == null ? "" : packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectName() {
        if (StringUtils.isBlank(projectName)) {
            String[] projectNames = StringUtils.split(getPackageName(), "//.");
            projectName = projectNames[projectNames.length - 1];
            projectName = StringUtils.toCapitalizeCamelCase(projectName);
        }
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFilePath() {
        return filePath == null ? "/Auto" : filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Path getPath() {
        return path == null ? new Path() : path;
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

        public Path() {
        }

        public Path(String controller, String service, String entity, String mapper) {
            this.controller = controller;
            this.service = service;
            this.entity = entity;
            this.mapper = mapper;
        }

        public String getController() {
            return StringUtils.isBlank(controller) ? "controller" : controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }

        public String getService() {
            return StringUtils.isBlank(service) ? "service" : service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getEntity() {
            return StringUtils.isBlank(entity) ? "entity" : entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getMapper() {
            return StringUtils.isBlank(mapper) ? "mapper" : mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }

    }

}
