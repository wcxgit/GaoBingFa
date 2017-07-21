package org.seckill.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.MessageFormat;

/**
 * Created by walter on 2017/7/21.
 * 缓存数据库
 */
public class RedisDao {

    private final JedisPool jedisPool;
    private final RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    //创建protostuff
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    /**
     * 从缓存数据库中获取指定id的seckill对象
     * 如果没有则从数据库中查找出指定数据，并将查找的数据存储到缓存数据库中
     *
     * @param SeckillId
     * @return
     */
    public Seckill getRedis(Long SeckillId) {
        String key = "seckill:" + SeckillId;

        try {
            Jedis jedis = jedisPool.getResource();
            try {
                //从缓存数据库中查询指定id的数据
                byte[] bytes = jedis.get(key.getBytes());
                //如果查询到数据
                if (bytes != null) {
                    //将数据反序列化成所需对象
                    Seckill seckill = schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象存储到缓存数据库中
     *
     * @param seckill
     * @return 储存成功：ok；储存出错：错误信息F
     */
    public String putRedis(Seckill seckill) {
        String key = "seckill:" + seckill.getSeckillId();
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                //将对象序列化
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //将字节数组存储到缓存数据库
                //超时缓存
                int timeOut = 60 * 60;//一小时
                String result = jedis.setex(key.getBytes(), timeOut, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
