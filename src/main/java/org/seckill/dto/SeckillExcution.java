package org.seckill.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * 执行秒杀后返回的结果
 * Created by walter on 2017/7/17.
 */
@Data
@NoArgsConstructor
public class SeckillExcution {
    private long seckillId;//秒杀的商品id
    private int state;//秒杀状态
    private String stateInfo;//秒杀状态信息
    private SuccessKilled successKilled;//秒杀成功后的对象

    /**
     * 成功信息
     *
     * @param seckillId
     * @param successKilled
     */
    public SeckillExcution(long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    /**
     * 失败信息
     *
     * @param seckillId
     */
    public SeckillExcution(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
}
