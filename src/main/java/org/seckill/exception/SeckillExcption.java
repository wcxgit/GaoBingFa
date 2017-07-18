package org.seckill.exception;

/**
 * 秒杀业务相关异常
 * Created by walter on 2017/7/17.
 */
public class SeckillExcption extends RuntimeException {

    public SeckillExcption(String message) {
        super(message);
    }

    public SeckillExcption(String message, Throwable cause) {

        super(message, cause);
    }
}
