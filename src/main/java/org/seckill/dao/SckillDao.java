package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by walter on 2017/7/16.
 */
public interface SckillDao {

    /**
     * 减少库存
     *
     * @param seckillId  秒杀商品id
     * @param createTime 秒杀时间
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("createTime") Date createTime);

    /**
     * 根据id查询商品对象
     *
     * @param seckillId 秒杀商品id
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 查询秒杀商品列表
     *
     * @return
     */
    List<Seckill> queryAll();
}
