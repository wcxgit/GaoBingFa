package org.seckill.exception;

/**
 * 重复秒杀异常
 * Created by walter on 2017/7/17.
 */
public class RepeatKillException extends SeckillExcption {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
