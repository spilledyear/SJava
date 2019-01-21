package com.zto.sxy.qf.validate;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.logging.Logger;

public class RedisUtils {
    private static Logger LOG = Logger.getLogger("RedisUtils");

    private static Jedis jedis = null;
    private static String path;
    private static int port;

    private static JedisCluster jedisCluster = null;
    private static String[] paths;
    private static String[] ports;
    private static boolean isClustered = false;

    public static void init(String path, String port) {
        String[] temp = path.split(",");
        int count = temp.length;
        if (count > 1) {
            setPaths(temp);
            setPorts(port.split(","));
            setClustered(true);
        } else {
            setPath(path);
            setPort(Integer.parseInt(port));
            setClustered(false);
        }
    }

    private static Jedis ConntionMaker() {
        if (getPath() != null) {
            jedis = new Jedis(getPath(), getPort());
            LOG.info("----redis connect info:" + getPath() + ":" + getPort());
            return jedis;
        } else {
            return null;
        }
    }

    private static JedisCluster redisClusterConn() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(100);
        config.setMaxWaitMillis(3000);
        Set<HostAndPort> hosts = new HashSet<>();
        String[] paths = getPaths();
        String[] ports = getPorts();
        if (paths != null & ports != null) {
            for (int i = 0; i < ports.length; i++) {
                for (int j = 0; j < paths.length; j++) {
                    hosts.add(new HostAndPort(paths[j], Integer.parseInt(ports[i])));
                }
            }
            LOG.info("----cluster connect to redis");
            return new JedisCluster(hosts, config);
        } else {

            return null;
        }
    }

    private static Jedis jedisFactory() {
        if (jedis == null) {
            jedis = ConntionMaker();
        }
        return jedis;
    }

    private static JedisCluster jedisClusterFactory() {
        if (jedisCluster == null) {
            jedisCluster = redisClusterConn();
        }
        return jedisCluster;
    }

    private static void release() {
        try {
            jedis.close();
        } catch (Exception e1) {
            LOG.info("------redis connection close error");
        }
        try {
            jedisCluster.close();
        } catch (Exception e1) {
            LOG.info("------redis connection close error");
        }
        jedis = null;
        jedisCluster = null;
    }

    public static void delete(String preKey){
        try {
            if (!isClustered()) {
                jedis = jedisFactory();
                Set<String> set = jedis.keys(preKey +"*");
                Iterator<String> iterator = set.iterator();
                while(iterator.hasNext()){
                    jedis.del(iterator.next());
                }
            }
        } catch (Exception e) {
            LOG.info("------set redis key value error");
            release();
        }
    }


    public static void setRedisValue(String key, String value) {
        try {
            if (!isClustered()) {
                jedis = jedisFactory();
                jedis.set(key, value);
            } else {
                jedisCluster = jedisClusterFactory();
                jedisCluster.set(key, value);
            }
        } catch (Exception e) {
            LOG.info("------set redis key value error");
            release();
            if (!isClustered()) {
                jedis = jedisFactory();
                jedis.set(key, value);
            } else {
                jedisCluster = jedisClusterFactory();
                jedisCluster.set(key, value);
            }
        }
    }

    public static String getRedisValue(String key) {
        String value = "";
        try {
            if (!isClustered()) {
                jedis = jedisFactory();
                value = jedis.get(key);
            } else {
                jedisCluster = jedisClusterFactory();
                value = jedisCluster.get(key);
            }
        } catch (Exception e) {
            LOG.info("------get redis key value error");
            release();
            if (!isClustered()) {
                jedis = jedisFactory();
                value = jedis.get(key);
            } else {
                jedisCluster = jedisClusterFactory();
                value = jedisCluster.get(key);
            }
        }
        return value;

    }

    public static void setList(String key, List<String> list){
        try {
            if (!isClustered()) {
                jedis = jedisFactory();
                for(String s: list){
                    jedis.lpush(key, s);
                }
            } else {
                jedisCluster = jedisClusterFactory();
                for(String s: list){
                    jedisCluster.lpush(key, s);
                }
            }
        } catch (Exception e) {
            LOG.info("------set redis key list value error");
            release();
        }
    }

    public static  List<String> getList(String key){
        List<String> list = null;
        try {
            if (!isClustered()) {
                jedis = jedisFactory();
                list = jedis.lrange(key, 0, Integer.MAX_VALUE);
            } else {
                jedisCluster = jedisClusterFactory();
                list = jedisCluster.lrange(key, 0, Integer.MAX_VALUE);
            }
        } catch (Exception e) {
            LOG.info("------set redis key map value error");
            release();
            if (!isClustered()) {
                jedis = jedisFactory();
                list = jedis.lrange(key, 0, Integer.MAX_VALUE);
            } else {
                jedisCluster = jedisClusterFactory();
                list =jedisCluster.lrange(key, 0, Integer.MAX_VALUE);
            }
        }
        return list;
    }


    public static void setHashMap(String key, Map<String, String> value) {
        try {
            if (!isClustered()) {
                jedis = jedisFactory();
                jedis.hmset(key, value);
            } else {
                jedisCluster = jedisClusterFactory();
                jedisCluster.hmset(key, value);
            }
        } catch (Exception e) {
            LOG.info("------set redis key map value error");
            release();
            if (!isClustered()) {
                jedis = jedisFactory();
                jedis.hmset(key, value);
            } else {
                jedisCluster = jedisClusterFactory();
                jedisCluster.hmset(key, value);
            }
        }
    }

    public static Map<String, String> getHashMap(String key) {
        Map<String, String> value = null;
        try {
            if (!isClustered()) {
                jedis = jedisFactory();
                value = jedis.hgetAll(key);
            } else {
                jedisCluster = jedisClusterFactory();
                value = jedisCluster.hgetAll(key);
            }
        } catch (Exception e) {
            LOG.info("------get redis key map value error");
            release();
            if (!isClustered()) {
                jedis = jedisFactory();
                value = jedis.hgetAll(key);
            } else {
                jedisCluster = jedisClusterFactory();
                value = jedisCluster.hgetAll(key);
            }
        }
        return value;
    }

    public static boolean isClustered() {
        return isClustered;
    }

    public static void setClustered(boolean isClustered) {
        RedisUtils.isClustered = isClustered;
    }


    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        RedisUtils.path = path;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        RedisUtils.port = port;
    }

    public static String[] getPaths() {
        return paths;
    }

    public static void setPaths(String[] paths) {
        RedisUtils.paths = paths;
    }

    public static String[] getPorts() {
        return ports;
    }

    public static void setPorts(String[] ports) {
        RedisUtils.ports = ports;
    }
}
