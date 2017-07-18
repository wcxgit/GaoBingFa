package org.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by walter on 2017/7/17.
 */
public class SeckillCloseException extends SeckillExcption {
    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillCloseException(String message) {

        super(message);
    }
}
