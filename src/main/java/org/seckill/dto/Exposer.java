package org.seckill.dto;

import lombok.*;

/**
 * 暴露秒杀地址dto
 * Created by walter on 2017/7/17.
 */
@Data
public class Exposer {
    private boolean exposed;//是否开始秒杀
    private String md5;
    private long seckillId;
    private long now;//系统当前时间(毫秒值)
    private long start;//秒杀开始时间
    private long end;//秒杀结束时间

    public Exposer() {
    }

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
