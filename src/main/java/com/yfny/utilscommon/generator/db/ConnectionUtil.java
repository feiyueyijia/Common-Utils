package com.yfny.utilscommon.generator.db;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.entity.RelationMaterials;
import com.yfny.utilscommon.generator.invoker.RelationInvoker;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.StringUtil;
import com.yfny.utilscommon.util.CommonUtils;
import com.yfny.utilscommon.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库连接工具
 * Created by jisongZhou on 2019/3/5.
 **/
public class ConnectionUtil {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * 初始化数据库连接
     *
     * @return
     */
    public boolean initConnection() {
        try {
            if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getDb().getUrl())) {
                Class.forName(DriverFactory.getDriver(ConfigUtil.getConfiguration().getDb().getUrl()));
                String url = ConfigUtil.getConfiguration().getDb().getUrl();
                String username = ConfigUtil.getConfiguration().getDb().getUsername();
                String password = ConfigUtil.getConfiguration().getDb().getPassword();
                connection = DriverManager.getConnection(url, username, password);
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取表结构数据
     *
     * @param tableName 表名
     * @return 包含表结构数据的列表
     */
    public List<ColumnInfo> getMetaData(String tableName) throws SQLException {
        ResultSet tempResultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
        String primaryKey = null;
        if (tempResultSet.next()) {
            primaryKey = tempResultSet.getObject(4).toString();
        }
        List<ColumnInfo> columnInfos = new ArrayList<>();
        statement = connection.createStatement();
        String sql = "SELECT * FROM " + tableName + " WHERE 1 != 1";
        resultSet = statement.executeQuery(sql);
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            ColumnInfo info;
            if (metaData.getColumnName(i).equals(primaryKey)) {
                info = new ColumnInfo(metaData.getColumnName(i), metaData.getColumnType(i), true, metaData.getColumnDisplaySize(i), metaData.isNullable(i));
            } else {
                info = new ColumnInfo(metaData.getColumnName(i), metaData.getColumnType(i), false, metaData.getColumnDisplaySize(i), metaData.isNullable(i));
            }
            columnInfos.add(info);
        }
        statement.close();
        resultSet.close();
        return columnInfos;
    }

    /**
     * 获取当前数据库的所有表
     *
     * @return
     */
    public List<BCodeMaterials> getTablesData() throws SQLException {
        List<BCodeMaterials> materials = new ArrayList<>();
        String url = ConfigUtil.getConfiguration().getDb().getUrl();
        int a = url.indexOf("jdbc:mysql://") + 13;
        int b = url.indexOf("/", a);
        int c = url.indexOf("?");
        String databaseName = url.substring(b + 1, c);
        this.initConnection();
        statement = connection.createStatement();
        String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + databaseName + "';";
        resultSet = statement.executeQuery(sql);
        Map<String, String> tableMap = new HashMap<>();
        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            String description = resultSet.getString("TABLE_COMMENT");
            String className = StringUtils.toCapitalizeCamelCase(tableName);
            if (!"t_database_relation".equals(tableName)) {
                ResultSet tempResultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
                String primaryKey = null;
                if (tempResultSet.next()) {
                    primaryKey = tempResultSet.getObject(4).toString();
                }
                BCodeMaterials material = new BCodeMaterials();
                material.setTableName(tableName);
                material.setDescription(description);
                material.setClassName(className);
                material.setPrimaryKey(primaryKey);
                material.setPkProperty(StringUtil.columnName2PropertyName(primaryKey));
                materials.add(material);
            }
            tableMap.put(tableName, description);
        }
        if (tableMap.containsKey("t_database_relation")) {
            for (BCodeMaterials material : materials) {
                setRelation(material);
            }
        }
        statement.close();
        resultSet.close();
        this.close();
        return materials;
    }

    private void setRelation(BCodeMaterials material) throws SQLException {
        List<RelationMaterials> relationMaterials = new ArrayList<>();
        List<String> fkList = new ArrayList<>();
        statement = connection.createStatement();
        String sql = "SELECT id, primary_table, foreign_table, primary_key, foreign_key, relation_type FROM t_database_relation;";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String primaryTable = resultSet.getString("primary_table");
            String foreignTable = resultSet.getString("foreign_table");
            String primaryKey = resultSet.getString("primary_key");
            String foreignKey = resultSet.getString("foreign_key");
            String relationType = resultSet.getString("relation_type");
            if (material.getTableName().equals(primaryTable)) {
                RelationMaterials primaryMaterial = new RelationMaterials();
                primaryMaterial.setId(id);
                primaryMaterial.setPkTableName(primaryTable);
                primaryMaterial.setPkClassName(material.getClassName());
                primaryMaterial.setPrimaryKey(primaryKey);
                primaryMaterial.setPkProperty(StringUtil.columnName2PropertyName(primaryKey));
                primaryMaterial.setFkTableName(foreignTable);
                primaryMaterial.setFkClassName(StringUtils.toCapitalizeCamelCase(foreignTable));
                primaryMaterial.setForeignKey(foreignKey);
                primaryMaterial.setFkProperty(StringUtil.columnName2PropertyName(foreignKey));
                primaryMaterial.setRelation(relationType);
                relationMaterials.add(primaryMaterial);
            } else if (material.getTableName().equals(foreignTable)) {
                if (relationType.equals(RelationInvoker.Builder.ONE_TO_MANY) || relationType.equals(RelationInvoker.Builder.ONE_TO_ONE)) {
                    fkList.add(foreignKey);
                }
            }
        }
        fkList = CommonUtils.removeDuplicate(fkList);
        material.setRelationList(relationMaterials);
        material.setFkList(fkList);
    }

    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
