package com.hand.sxy.design.flyweight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 再学习学习
 *
 * 享元模式
 * 享元模式的主要目的是实现对象的共享，即共享池，当系统中对象多的时候可以减少内存的开销，通常与工厂模式一起使用。
 * 适用于作共享的一些个对象，他们有一些共有的属性，就拿数据库连接池来说，url、driverClassName、username、password及dbname，
 * 这些属性对于每个连接来说都是一样的，所以就适合用享元模式来处理，建一个工厂类，将上述类似属性作为内部数据，其它的作为外部数据，
 * 在方法调用时，当做参数传进来，这样就节省了空间，减少了实例的数量。
 *
 */
public class ConnectionPool {
    private Vector<Connection> pool;

    private static String url = "xxx";
    private static String username = "xxx";
    private static String password = "xxx";
    private static String driverClass = "xxx";

    private int poolSize = 100;

    private static ConnectionPool instance = null;
    Connection conn = null;

    private ConnectionPool(){
        pool = new Vector<>(poolSize);
        for(int i = 0; i < poolSize; i++){
            try{
                Class.forName(driverClass);
                conn = DriverManager.getConnection(url);
                pool.add(conn);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    //返回连接到连接池
    public synchronized void release(){
        pool.add(conn);
    }

    //返回连接池中的一个连接
    public synchronized Connection getConnection(){
        if(pool.size() <= 0)    return null;

        Connection connection = pool.get(0);
        pool.remove(connection);
        return connection;
    }

}
