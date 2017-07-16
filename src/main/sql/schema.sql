-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
USE seckill;

-- 秒杀库存表
CREATE TABLE sckill (
  seckill_id  BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '商品库存id',
  name        VARCHAR(120) NOT NULL
  COMMENT '商品名称',
  number      INT          NOT NULL
  COMMENT '库存数量',
  start_time  TIMESTAMP    NOT NULL
  COMMENT '秒杀开始时间',
  end_time    TIMESTAMP    NOT NULL
  COMMENT '秒杀结束时间',
  create_time TIMESTAMP    NOT NULL /*DEFAULT current_timestamp*/
  COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COMMENT '秒杀库存表';

-- 初始化数据
INSERT INTO sckill (name, number, create_time, start_time, end_time)
VALUES
  ('1000元秒杀iphone6', 100, now(), '2017-7-16 00:00:00', '2017-7-17 00:00:00'),
  ('700元秒杀小米6', 102, now(), '2017-7-16 00:00:00', '2017-7-17 00:00:00'),
  ('800元秒杀小米5', 200, now(), '2017-7-16 00:00:00', '2017-7-17 00:00:00'),
  ('100元秒杀iphone7', 200, now(), '2017-7-16 00:00:00', '2017-7-17 00:00:00'),
  ('4000元秒杀小米电视', 500, now(), '2017-7-16 00:00:00', '2017-7-17 00:00:00'),
  ('900元秒杀华为', 300, now(), '2017-7-16 00:00:00', '2017-7-17 00:00:00'),
  ('700元秒杀魅族', 400, now(), '2017-7-16 00:00:00', '2017-7-17 00:00:00');

-- 秒杀成功明细表
-- 用户登陆认证相关信息
DROP TABLE success_killed;
CREATE TABLE success_killed (
  seckill_id  BIGINT       NOT NULL
  COMMENT '秒杀商品id',
  user_phone  BIGINT       NOT NULL
  COMMENT '用户手机号',
  state       TINYINT   NOT NULL DEFAULT -1
  COMMENT '状态标识,-1:无效;0:成功;1:已付款;2:已发货',
  create_time TIMESTAMP NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
  KEY idx_create_time(create_time)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '秒杀成功明细表';
