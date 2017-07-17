package org.seckill.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * Created by walter on 2017/7/16.
 */
@Getter
@Setter
@ToString(exclude = "sckill")
public class SuccessKilled {

    private long seckillId;

    private long userPhone;

    private int state;

    private Date createTime;

    private Seckill sckill;


}
