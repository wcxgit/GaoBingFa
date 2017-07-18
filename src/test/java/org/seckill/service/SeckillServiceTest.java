package org.seckill.service;

import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExcption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


import static org.junit.Assert.*;

/**
 * Created by walter on 2017/7/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml", "classpath:spring/spring-dao.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    @Test
    public void getAllSeckill() throws Exception {
        PageInfo pageInfo = seckillService.getAllSeckill(1, 10);
        logger.info("list={}", pageInfo.getList());
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long userPhone = 13111111111L;
            try {
                SeckillExcution excution = seckillService.excuteSeckill(seckillId, userPhone, exposer.getMd5());
                logger.info("excution=" + excution);
            } catch (RepeatKillException e) {
                e.printStackTrace();
            } catch (SeckillCloseException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("exposer={}", exposer);
        }
    }
}