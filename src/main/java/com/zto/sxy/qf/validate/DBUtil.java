package com.zto.sxy.qf.validate;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author spilledyear
 * @date 2017/12/28 22:19
 */
public class DBUtil {

    /**
     * 定义数据库的驱动信息
     */
    private static String DRIVER = "com.mysql.jdbc.Driver";
    /***
     * 表示定义数据库的用户名
     * qfang_dev qfang
     */
    private static String USERNAME = "qfang";
    /**
     * 定义数据库的密码
     * qfang_dev qfang.c0m
     */
    private static String PASSWORD = "qfang_dev";
    /**
     * 定义访问数据库的地址
     * 10.132.241.120:3306/qfang_dev  10.132.241.124:3306/qfang
     */
    private static String URL = "jdbc:mysql://10.132.241.120:3306/qfang_dev";


    private static DBUtil per = null;
    /**
     * 定义数据库的链接
     */
    private Connection con = null;
    /**
     * 定义sql语句的执行对象
     */
    private PreparedStatement pstmt = null;
    /**
     * 定义查询返回的结果集合
     */
    private ResultSet resultSet = null;

    private DBUtil() {

    }

    /**
     * 单例模式，获得工具类的一个对象
     *
     * @return
     */
    public static DBUtil getInstance() {
        if (per == null) {
            per = new DBUtil();
//            per.registeredDriver();
        }
        return per;
    }

    private void registeredDriver() {
        try {
            Class.forName(DRIVER);
            System.out.println("注册驱动成功！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得数据库的连接
     *
     * @return
     */
    public Connection getConnection() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("连接数据库成功!!");
        return con;
    }

    /**
     * 完成对数据库的表的添加删除和修改的操作
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean executeUpdate(String sql, List<Object> params)
            throws SQLException {

        boolean flag = false;

        int result = -1;  // 表示当用户执行添加删除和修改的时候所影响数据库的行数

        pstmt = con.prepareStatement(sql);

        if (params != null && !params.isEmpty()) {
            int index = 1;
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, i);
            }
        }

        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;

        return flag;
    }

    /**
     * 从数据库中查询数据
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> executeQuery(String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 1;
        pstmt = con.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < cols_len; i++) {
//                String cols_name = metaData.getColumnName(i + 1);
                String cols_name = metaData.getColumnLabel(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }

    public String executeQuery(String sql, String columnName) throws SQLException {
        pstmt = con.prepareStatement(sql);
        resultSet = pstmt.executeQuery();
        StringBuilder sb = new StringBuilder("");
        while (resultSet.next()) {
            sb.append(resultSet.getString(columnName)).append(",");
        }
        return sb.toString();
    }


    public List<String> queryList(String sql, String columnName) throws SQLException {
        pstmt = con.prepareStatement(sql);
        resultSet = pstmt.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(columnName));
        }
        return list;
    }


    /**
     * jdbc的封装可以用反射机制来封装,把从数据库中获取的数据封装到一个类的对象里
     *
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> List<T> executeQueryByRef(String sql, List<Object> params,
                                         Class<T> cls) throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        pstmt = con.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            T resultObject = cls.newInstance();  // 通过反射机制创建实例
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); // 打开javabean的访问private权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;

    }

    public void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
