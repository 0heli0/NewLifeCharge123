-- 站内信标题字段长度从50扩展至100位
alter table tb_notice  modify column title VARCHAR(100) NOT NULL DEFAULT ''  COMMENT '标题';


-- 新建微信退款记录表
DROP TABLE IF EXISTS `tb_weixin_refund_log`;
CREATE TABLE `tb_weixin_refund_log` (
  id                   INT AUTO_INCREMENT  COMMENT '主键-微信支付记录ID',
  appid                VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '开发者ID',
  mch_appid            VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '申请商户号的appid或商户号绑定的appid',
  mch_id               VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '商户号ID',
  device_info          VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '设备号',
  nonce_str            VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '随机字符串',
  sign                 VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '签名',
  sign_type            VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '签名类型',
  result_code          VARCHAR(16)    NOT NULL DEFAULT ''  COMMENT '业务结果',
  err_code             VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '错误代码',
  err_code_des         VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '错误代码描述',
  openid               VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '微信用户标识openid',
  payment_no           VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '企业付款成功，返回的微信付款单号',
  partner_trade_no     VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '商户订单号(本系统内订单号)',
  attach               VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '商家数据包',
  payment_time         VARCHAR(14)    NOT NULL DEFAULT ''  COMMENT '企业付款成功时间',
  return_code          VARCHAR(16)    NOT NULL DEFAULT ''  COMMENT '返回状态码',
  return_msg           VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '返回信息',
  create_time          DATETIME       NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='微信退款记录表 v1.0';