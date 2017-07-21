package org.seckill.dto;

import lombok.Data;

/**
 * Created by walter on 2017/7/19.
 */
@Data
public class SeckillResult<T> {
    private boolean isSuccess;

    private T data;

    private String msg;

    public SeckillResult(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public SeckillResult(boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

}
