package org.seckill.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExcption;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by walter on 2017/7/17.
 */
@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);
    @Resource
    private SeckillDao seckillDao;
    @Resource
    private SuccessKilledDao successKilledDao;

    private final String slat = "ewerjlFJAL34534#%￥%&&*//-*/+;'./";

    public PageInfo getAllSeckill(Integer pageNnm, Integer pageSize) {
        PageHelper.startPage(pageNnm, pageSize);
        List<Seckill> seckillList = seckillDao.queryAll();
        return new PageInfo(seckillList);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        long startTime = seckill.getStartTime().getTime();
        long endTime = seckill.getEndTime().getTime();
        long nowTime = new Date().getTime();
        //如果秒杀活动未开始或者已结束
        if (startTime > nowTime || endTime < nowTime) {
            return new Exposer(false, seckillId, nowTime, startTime, endTime);
        }

        String md5 = this.getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     *  1：开发团队达成一致约定，明确标注事务方法的变成风格
     *  2：保证事务方法的执行时间尽可能短，不要穿插其他的网络操作，如RPC/HTTP请求，或者将这些请求剥离到事务方法之外
     *  3：不是所有的方法都需要事务，如只有一条修改操作或者只读操作不需要事务
     */
    public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillExcption, RepeatKillException, SeckillCloseException {
        System.out.println(this.getMd5(seckillId).equals(md5));
        System.out.println("this.getMd5(seckillId)"+this.getMd5(seckillId));
        System.out.println("md5"+md5);
        if (this.getMd5(seckillId).equals(md5)) {
            try {
                int rowCount = seckillDao.reduceNumber(seckillId, new Date());
                if (rowCount == 0) {
                    throw new SeckillCloseException(SeckillStateEnum.END.getStateInfo());
                } else {
                    rowCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                    if (rowCount == 0) {
                        throw new RepeatKillException(SeckillStateEnum.REPEAT_SKILL.getStateInfo());
                    } else {
                        SuccessKilled successKilled = successKilledDao.queryByIdWithSckill(seckillId, userPhone);
                        return new SeckillExcution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                    }
                }
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage(), e);
                throw e;
            } catch (RepeatKillException e) {
                logger.error(e.getMessage(), e);
                throw e;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SeckillExcption("秒杀异常:" + e.getMessage());
            }
        } else {
            throw new RuntimeException(SeckillStateEnum.DATE_REWRITE.getStateInfo());
        }
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "./" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
