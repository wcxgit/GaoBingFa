package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized;
import org.seckill.entity.SuccessKilled;

/**
 * Created by walter on 2017/7/16.
 */
public interface SuccessKilledDao {

    /**
     * 插入成功秒杀明细
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询秒杀明细并含有被秒杀的商品对象
     *
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
