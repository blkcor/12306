package com.github.blkcor.util;

import cn.hutool.core.util.StrUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBUtil {
    public static String url = "";
    public static String username = "";
    public static String password = "";

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = DBUtil.url;
            String username = DBUtil.username;
            String password = DBUtil.password;
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取表注释
     *
     * @param tableName 表名
     * @return 表注释
     * @throws SQLException 异常
     */
    public static String getTableComment(String tableName) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select table_comment from information_schema.tables where table_name = '" + tableName + "'");
        String tableNameCH = "";
        if (rs != null) {
            while (rs.next()) {
                tableNameCH = rs.getString("table_comment");
                break;
            }
        }
        assert rs != null;
        rs.close();
        statement.close();
        connection.close();
        System.out.println("表名：" + tableNameCH);
        return tableNameCH;
    }

    public static List<Field> getColumnsByTableName(String tableName) throws SQLException {
        List<Field> fieldList = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("show full columns from " + tableName);
        if (rs != null) {
            while (rs.next()) {
                String columnName = rs.getString("Field");
                String type = rs.getString("Type");
                String comment = rs.getString("Comment");
                String nullAble = rs.getString("Null");
                Field field = new Field();
                field.setName(columnName);
                field.setNameHump(lineToHump(columnName));
                field.setNameBigHump(lineToBigHump(columnName));
                field.setJavaType(sqlTypeToJavaType(type));
                field.setType(type);
                field.setComment(comment);
                if (comment.contains("|")) {
                    field.setNameCn(comment.substring(0, comment.indexOf("|")));
                } else {
                    field.setNameCn(comment);
                }
                field.setNullable("YES".equals(nullAble));
                if (type.toLowerCase().contains("varchar")) {
                    String length = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                    field.setLength(Integer.parseInt(length));
                } else {
                    field.setLength(0);
                }
                if (comment.contains("枚举")) {
                    field.setEnums(true);
                    int start = comment.indexOf("[");
                    int end = comment.indexOf("]");
                    String enumsName = comment.substring(start + 1, end);
                    String enumsConst = StrUtil.toUnderlineCase(enumsName).toUpperCase().replace("_ENUM", "");
                    field.setEnumsConst(enumsConst);
                }else{
                    field.setEnums(false);
                }
                fieldList.add(field);
            }
        }
        System.out.println("字段：" + fieldList);
        return fieldList;
    }

    /**
     * 下划线转小驼峰
     */
    public static String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转大驼峰
     */
    public static String lineToBigHump(String str) {
        String s = lineToHump(str);
        return StrUtil.upperFirst(s);
    }

    /**
     * sql类型转java类型
     */
    public static String sqlTypeToJavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar") || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("timestamp")) {
            return "Date";
        } else {
            return "String";
        }
    }
}
