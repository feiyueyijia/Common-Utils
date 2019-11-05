package com.yfny.utilscommon.generator.utils;

import java.sql.Types;

/**
 * 代码生成器数据类型工具
 * Created by jisongZhou on 2019/3/5.
 **/
public class TypeUtil {

    /**
     * 将数据库数据类型转换为Java数据类型
     *
     * @param sqlType
     * @return
     */
    public static String parseTypeFormSqlType(int sqlType) {
        StringBuilder sb = new StringBuilder();
        switch (sqlType) {
            case Types.BIT:
            case Types.BOOLEAN:
                sb.append("boolean");
                break;
            case Types.TINYINT:
                sb.append("byte");
                break;
            case Types.SMALLINT:
                sb.append("short");
                break;
            case Types.INTEGER:
                sb.append("int");
                break;
            case Types.BIGINT:
                sb.append("long");
                break;
            case Types.REAL:
                sb.append("float");
                break;
            case Types.FLOAT:
            case Types.DOUBLE:
                sb.append("double");
                break;
            case Types.DECIMAL:
            case Types.NUMERIC:
                sb.append("BigDecimal");
                break;
            case Types.VARCHAR:
            case Types.CHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.LONGVARCHAR:
            case Types.LONGNVARCHAR:
                sb.append("String");
                break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                sb.append("Date");
                break;
            case Types.NCLOB:
            case Types.CLOB:
            case Types.BLOB:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                sb.append("byte[]");
                break;
            case Types.NULL:
            case Types.OTHER:
            case Types.JAVA_OBJECT:
                sb.append("Object");
                break;
            default:
                sb.append("Object");

        }
        return sb.toString();
    }

}
