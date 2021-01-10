-- 新建充电桩充电历史数据（即硬件充电账单）表
DROP TABLE IF EXISTS `tb_client_history_data`;
CREATE TABLE `tb_client_history_data` (
  id                  INT                  AUTO_INCREMENT  COMMENT '主键-新建充电桩历史数据ID',
  station_client_code VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '充电桩编号',
  gun_code            VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '充电枪编号',
  charge_way          TINYINT     NOT NULL DEFAULT 0   COMMENT '充电方式(0:立即充电 1：预约充电)',
  charge_mode         TINYINT     NOT NULL DEFAULT 0   COMMENT '充电模式(0.自动充电 1.金额模式 2.时间模式 3.电量模式)',
  charge_card_type    TINYINT     NOT NULL DEFAULT 113 COMMENT '充电卡类型(1.用户卡 81.员工卡 97.充值卡 112.在线卡 113.APP用户 120.VIN码)',
  charge_card_no      VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电卡号(用户手机号)',
  car_vin             VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '车辆VIN码',
  babe                VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电前余额/元，两位小数',
  charge_voltage      VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电电压，一位小数',
  charge_electricity  VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电电流，两位小数',
  charge_time         VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电时间/秒',
  charge_amount       VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电金额/元，两位小数',
  charge_energy       VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电电能,两位小数',
  charge_energy_start VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电开始电能,两位小数',
  charge_energy_end   VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电结束电能,两位小数',
  left_time           VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '剩余时间,只限直流',
  current_soc         VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '当前SOC(充电百分比/%),只限直流',
  upload_master       TINYINT     NOT NULL DEFAULT 0   COMMENT '是否上传主站(0.没有上传 1.上传)',
  pay                 TINYINT     NOT NULL DEFAULT 0   COMMENT '是否付费( 0.没有正常付费 1.已经付费)',
  charge_end_reason   TINYINT     NOT NULL DEFAULT 0   COMMENT '充电终止原因(0.正常接收 1.异常结束)',
  charge_time_start   DATETIME    NOT NULL DEFAULT now()   COMMENT '充电开始时间',
  charge_time_end     DATETIME    NOT NULL DEFAULT now()  COMMENT '充电结束时间',
  record_no           INT         NOT NULL DEFAULT 0  COMMENT '记录流水号--启动充电时下发的交易号/订单ID（transactionNo）--TODO:暂定',
  record_storage_no   INT         NOT NULL DEFAULT 0  COMMENT '记录存储序号',
  create_time         DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='充电桩充电历史数据（即硬件充电账单）表 v1.0';


-- 新建十个时段充电电量和充电金额表
DROP TABLE IF EXISTS `tb_stage_data`;
CREATE TABLE `tb_stage_data` (
  id                  INT                  AUTO_INCREMENT  COMMENT '主键-新建充电桩历史数据ID',
  client_history_id   INT         NOT NULL DEFAULT 0   COMMENT '充电桩充电历史数据主键ID',
  time                INT         NOT NULL DEFAULT 1   COMMENT '时段序号',
  balance             VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电金额/元，两位小数',
  power               VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '充电电能，两位小数',
  create_time         DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='十个时段充电电量和充电金额表 v1.0';








