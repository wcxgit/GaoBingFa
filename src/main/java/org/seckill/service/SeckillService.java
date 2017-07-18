package org.seckill.service;

import com.github.pagehelper.PageInfo;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExcption;

import java.util.List;

/**
 * 秒杀业务接口
 * Created by walter on 2017/7/17.
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    PageInfo getAllSeckill(Integer pageNnm, Integer pageSize);

    /**
     * 根据id查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 暴露秒杀接口
     * 如果秒杀开始返回秒杀地址
     * 否则返回系统时间和秒杀开始时间
     *
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillExcption, RepeatKillException, SeckillCloseException;

}
