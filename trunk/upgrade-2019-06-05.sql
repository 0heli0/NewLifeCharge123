-- 新建充电桩告警记录表
DROP TABLE IF EXISTS `tb_client_alarm_data`;
CREATE TABLE `tb_client_alarm_data` (
  id                  INT                  AUTO_INCREMENT  COMMENT '主键-充电桩告警记录ID',
  station_client_code VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '充电桩编号',
  gun_code            VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '充电枪编号',
  alarm_point         VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '告警点',
  alarm_point_name    VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '告警点中文解释',
  alarm_reason        VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '告警原因',
  alarm_reason_name   VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '告警点中文解释',
  alarm_time_start    DATETIME    NOT NULL DEFAULT now()  COMMENT '告警开始时间',
  alarm_time_end      DATETIME    NOT NULL DEFAULT now()  COMMENT '告警结束时间',
  alarm_time          VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '告警持续时间/秒',
  affect_charge       TINYINT     NOT NULL DEFAULT 0   COMMENT '是否影响充电(0.无影响 1.有影响)',
  upload_master       TINYINT     NOT NULL DEFAULT 0   COMMENT '是否上传主站(0.没有上传 1.上传)',
  record_no           INT         NOT NULL DEFAULT 0  COMMENT '记录流水号--启动充电时下发的交易号/订单ID（transactionNo)',
  record_storage_no   INT         NOT NULL DEFAULT 0  COMMENT '记录存储序号',
  create_time         DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='充电桩告警记录表 v1.0';

