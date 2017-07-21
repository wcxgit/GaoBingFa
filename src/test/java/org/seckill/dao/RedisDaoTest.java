package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by walter on 2017/7/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    @Resource
    private RedisDao redisDao;
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testRedis() throws Exception {
        Long id = 1001L;
        //从缓存数据库中获取数据
        Seckill seckill = redisDao.getRedis(id);
        if (seckill == null) {
            //如果未查询到数据
            //从数据库查找
            seckill = seckillDao.queryById(id);
            if (seckill == null) {
                //如果从数据库未查询到数据，则说明无该数据
                System.out.println("无数据");
            } else {
                //如果从数据库中查询出数据
                //将数据存储到缓存数据库中
                String result = redisDao.putRedis(seckill);
                System.out.println(result);
            }
        }

        //从缓存数据库中查寻出对象
        seckill = redisDao.getRedis(id);
        System.out.println(seckill);
    }
}