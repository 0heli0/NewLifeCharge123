-- 数据库设计
-- ER/Studio 8.0 SQL Code Generation
-- Company :      www.mseenet.com
-- Project :      新活充电.DM1
--
-- Target DBMS : MySQL 5.x
--
-- 桩站即充电站
-- 用户分为三大类：总后台平台用户，桩站端用户（包含桩站小程序，存在主、子账号情况），车主端小程序用户，一般说的用户都是指车主用户
--


DROP database IF EXISTS new_life_charge;
Create database new_life_charge DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use new_life_charge;


-- ------------------待定表--------------------------------------

-- 
-- TABLE: tb_admin_actions
-- TODO:管理动作表
-- 待定
-- DROP TABLE IF EXISTS `tb_admin_actions`;
-- CREATE TABLE tb_admin_actions(
--     id             INT             AUTO_INCREMENT COMMENT '主键ID',
--     title          VARCHAR(60)     NOT NULL COMMENT '动作标题',
--     action         VARCHAR(100)    NOT NULL COMMENT '动作代码',
--     parent_id      INT             NOT NULL COMMENT '父id',
--     display_order  INT             NOT NULL COMMENT '排序',
--     create_time    DATETIME        NOT NULL COMMENT '创建时间',
--     PRIMARY KEY (id)
-- )ENGINE=INNODB
-- COMMENT ='管理动作表 v1.0' ;



--
-- TABLE: tb_module
-- TODO:模块表
-- TODO 可能不需要模块表
--
-- DROP TABLE IF EXISTS `tb_module`;
-- CREATE TABLE `tb_module` (
--   id            INT AUTO_INCREMENT  COMMENT '模块表主键ID',
--   pId           INT   NOT NULL DEFAULT 0  COMMENT '父Id',
--   name          VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '模块名称',
--   alias         VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '别名',
--   module_type   INT   NOT NULL DEFAULT 1  COMMENT '模块类型(1：无页面 2：列表页)',
--   default_open  INT   NOT NULL DEFAULT 0  COMMENT '默认展开(0：否 1：是)',
--   icon_url      VARCHAR(100) NOT NULL DEFAULT ''  COMMENT '自定义图标',
--   link_url      VARCHAR(255) NOT NULL DEFAULT ''  COMMENT '链接路径',
--   sort          INT   NOT NULL DEFAULT 0  COMMENT '排序',
--   module_level  INT   NOT NULL DEFAULT 0  COMMENT '级别',
--   station_flag  INT   NOT NULL DEFAULT 0  COMMENT '是否是桩站后台模块(0：否 1：是)',
--   create_time   DATETIME NOT NULL DEFAULT now()  COMMENT '创建时间',
--   PRIMARY KEY (id)
-- )
--   ENGINE = INNODB
--   COMMENT ='-- 模块表 v1.0';



-- --------------------
-- TABLE: tb_api_log
-- TODO:接口日志表
-- 待定
-- --------------------
DROP TABLE IF EXISTS `tb_api_log`;
CREATE TABLE tb_api_log (
  id                INT AUTO_INCREMENT  COMMENT '主键-接口日志id',
  server            VARCHAR(50)   NOT NULL DEFAULT ''  COMMENT '服务名',
  host              VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT '主机',
  path_query        TEXT                               COMMENT '请求和参数',
  controller        VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT 'controller',
  action            VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT '动作',
  imei              VARCHAR(50)   NOT NULL DEFAULT ''  COMMENT 'IMEI号',
  device            VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT '设备号',
  user_agent        VARCHAR(5000) NOT NULL DEFAULT ''  COMMENT 'UserAgent',
  hash              VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT 'Hash值',
  token             VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT 'Token',
  validation_result VARCHAR(50)   NOT NULL DEFAULT ''  COMMENT '验证结果',
  ip_address        VARCHAR(50)   NOT NULL DEFAULT ''  COMMENT 'IP地址',
  mac_address       VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT 'MAC地址',
  os_version        VARCHAR(100)  NOT NULL DEFAULT ''  COMMENT '系统版本',
  create_time       DATETIME      NOT NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='接口日志表 v1.0';


-- ----------------------------
-- Table structure for tb_operation_log
-- 操作日志表
--
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log`  (
  `id`                INT NOT NULL AUTO_INCREMENT COMMENT '主键-操作日志id',
  `user_id`           INT NOT NULL DEFAULT 0 COMMENT '用户ID',
  user_type           TINYINT NOT NULL DEFAULT 1   COMMENT '用户类型(1:车主用户,2:桩站系统主账号,3:桩站系统子账号,4:总后台系统用户)',
  `station_id`        INT     NOT NULL DEFAULT 0   COMMENT '桩站id',
  `login_name`        VARCHAR(50)   NOT NULL DEFAULT ''  COMMENT '登录账号(可能与手机号码一致)',
  `operation_type`    TINYINT(4)    NULL DEFAULT NULL    COMMENT '操作类型:操作类型',
  `operation_text`    TEXT                               COMMENT '操作内容',
  `action`            VARCHAR(20)   NOT NULL  DEFAULT '' COMMENT '操作动作',
  `operation_moudle`  VARCHAR(20)   NOT NULL  DEFAULT '' COMMENT '操作模块',
  `request_url`       VARCHAR(255)  NOT NULL  DEFAULT '' COMMENT '请求的网址',
  `ip`                VARCHAR(20)   NOT NULL  DEFAULT ''  COMMENT 'IP地址',
  `create_time`       DATETIME      NOT NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志表 v1.0' ROW_FORMAT = Compact;



--
-- TABLE: tb_parking_log
-- TODO:泊车记录表
--
--  DROP TABLE IF EXISTS `tb_parking_log`;
--  CREATE TABLE `tb_parking_log` (
--    pId          INT   AUTO_INCREMENT  COMMENT '泊车记录表主键ID',
--    staId          INT   NOT NULL DEFAULT 0 COMMENT '所属桩站ID',
--    userId          INT   NOT NULL DEFAULT 0 COMMENT '用户id',
--    numberPlate          VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '车牌号',
--    nickName          VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '昵称',
--    mobile          VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '手机号',
--    parkingStartTime DATETIME       NOT NULL DEFAULT now()  COMMENT '泊车开始时间',
--    parkingEndTime DATETIME         COMMENT '泊车结束时间',
--    status          tinyint NOT NULL DEFAULT 0  COMMENT '状态',
--    remark          VARCHAR(500) NOT NULL DEFAULT ''  COMMENT '备注',
--    create_time DATETIME       NOT NULL DEFAULT now()  COMMENT '创建时间',
--    PRIMARY KEY (pId)
--  )
--    ENGINE = INNODB
--    COMMENT ='-- 泊车记录表 v1.0';



-- ===========================开始=============================================


-- ------------------
-- TABLE: tb_area
-- 区域表
-- ------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area`(
  id                   INT        AUTO_INCREMENT COMMENT '区域表主键ID',
  parent_id            INT         NOT NULL DEFAULT 0   COMMENT '父节点id',
  name                 VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '地区名字',
  short_name           VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '地区简称',
  merger_name          VARCHAR(80) NOT NULL DEFAULT ''  COMMENT '三级地区名',
  merger_short_name    VARCHAR(30) NOT NULL DEFAULT ''  COMMENT '三级地区简称',
  level_type           TINYINT     NOT NULL DEFAULT 0   COMMENT '等级',
  city_code            VARCHAR(5)  NOT NULL DEFAULT ''  COMMENT '城市区号',
  zip_code             VARCHAR(6)  NOT NULL DEFAULT ''  COMMENT '邮编',
  pin_yin              VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '地区拼音',
  jian_pin             VARCHAR(10) NOT NULL DEFAULT ''  COMMENT '地区拼音简拼',
  first_char           VARCHAR(1)  NOT NULL DEFAULT ''  COMMENT '第一个拼音',
  lng                  DECIMAL(12, 9) NOT NULL DEFAULT 0  COMMENT '经度',
  lat                  DECIMAL(12, 9) NOT NULL DEFAULT 0  COMMENT '纬度',
  PRIMARY KEY (id)
)ENGINE=INNODB
  COMMENT='区域表 v1.0' ;


-- --------------------
-- TABLE: tb_regions
-- 行政区域表
-- --------------------
DROP TABLE IF EXISTS `tb_regions`;
CREATE TABLE `tb_regions` (
  id            INT         AUTO_INCREMENT  COMMENT '主键-行政区域id',
  name          VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '名称',
  spell         VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '拼写',
  short_spell   VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '简拼',
  display_order INT         NOT NULL DEFAULT 0   COMMENT '排序',
  parent_id     INT         NOT NULL DEFAULT 0   COMMENT '父id',
  layer         TINYINT     NOT NULL DEFAULT 0   COMMENT '级别',
  distrct_id    INT         NOT NULL DEFAULT 0   COMMENT '省id',
  distrct_name  VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '省名称',
  city_id       INT         NOT NULL DEFAULT 0   COMMENT '市id',
  city_name     VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '市名称',
  province      VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '省份',
  hot           TINYINT     NOT NULL DEFAULT 0   COMMENT '热门城市(0否 1是)',
  status        TINYINT     NOT NULL DEFAULT 0   COMMENT '状态(0隐藏 1显示) ',
  update_time   DATETIME    NOT NULL DEFAULT now()  COMMENT '更新时间',
  create_time   DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='行政区域表 v1.0';



-- -----------------------------------
-- TABLE: tb_generalize_station_log
-- 推广建站记录表
-- -----------------------------------
DROP TABLE IF EXISTS `tb_generalize_station_log`;
CREATE TABLE `tb_generalize_station_log` (
  id                INT AUTO_INCREMENT  COMMENT '主键-推广建站记录id',
  mobile            VARCHAR(50)   NOT NULL DEFAULT ''   COMMENT '推广人电话',
  name              VARCHAR(50)   NOT NULL DEFAULT ''   COMMENT '推广人昵称',
  build_time        DATETIME      NULL DEFAULT NULL     COMMENT '预计建站时间',
  station_address   VARCHAR(100)  NOT NULL DEFAULT ''   COMMENT '电站地址',
  station_descript  VARCHAR(100)  NOT NULL DEFAULT ''   COMMENT '电站描述',
  create_time       DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='推广建站记录表 v1.0';



-- ---------------------
-- TABLE: tb_suggestion
-- 车主用户意见反馈表
-- ---------------------
 DROP TABLE IF EXISTS `tb_suggestion`;
 CREATE TABLE `tb_suggestion` (
 id            INT AUTO_INCREMENT  COMMENT '主键-意见反馈id',
 user_id       INT         NOT NULL DEFAULT 0   COMMENT '用户id', -- 应该只涉及车主用户
 mobile        VARCHAR(11) NOT NULL DEFAULT ''  COMMENT '手机号',
 content       TEXT          COMMENT '反馈建议',
 create_time   DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
 PRIMARY KEY (id)
 )
 ENGINE = INNODB
 COMMENT ='车主用户意见反馈表 v1.0';


-- -------------------
-- TABLE: tb_notice
-- 站内信表
-- -------------------
DROP TABLE IF EXISTS `tb_notice`;
CREATE TABLE `tb_notice` (
  id          INT AUTO_INCREMENT  COMMENT '主键-站内信id',
  title       VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '标题',
  content     TEXT          COMMENT '内容',
  create_time DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='站内信表 v1.0';



-- ----------------------------
-- TABLE: tb_notice_user_ref
-- 站内信与车主用户关联表
-- ----------------------------
DROP TABLE IF EXISTS `tb_notice_user`;
CREATE TABLE `tb_notice_user` (
  id          INT AUTO_INCREMENT  COMMENT '主键-站内信与车主用户关联id',
  notice_id   INT       NOT NULL  DEFAULT 0  COMMENT '站内信id',
  user_id     INT       NOT NULL  DEFAULT 0  COMMENT '用户id',
  status      TINYINT   NOT NULL  DEFAULT 0  COMMENT '阅读状态（0：未读  1：已读）',
  read_time   DATETIME                       COMMENT '阅读时间',
  create_time DATETIME  NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='站内信与车主用户关联表 v1.0';


-- ------------------------
-- TABLE: tb_role_info
-- 角色信息表
-- 分为总后台角色和桩站角色
-- 桩站角色会绑定对应的桩站ID
-- ------------------------
DROP TABLE IF EXISTS `tb_role_info`;
CREATE TABLE `tb_role_info` (
  id              INT AUTO_INCREMENT  COMMENT '主键-角色id',
  station_id      INT           NOT NULL DEFAULT 0    COMMENT '所属桩站ID',
  role_name       VARCHAR(50)   NOT NULL DEFAULT ''   COMMENT '角色名称',
  custom          TINYINT       NOT NULL DEFAULT 1    COMMENT '是否自定义角色(0否 1是)',
  project         TINYINT       NOT NULL DEFAULT 0    COMMENT '所属系统(0:总后台系统,1：桩站系统)',
  remark          VARCHAR(100)  NOT NULL DEFAULT ''   COMMENT '备注',
  create_time     DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='角色信息表（分为总后台角色和桩站角色，桩站角色会绑定对应的桩站ID） v1.0';


-- ---------------------------
-- TABLE: tb_permission_info
-- 权限信息表
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission_info`;
CREATE TABLE `tb_permission_info` (
  id                INT   AUTO_INCREMENT  COMMENT '主键-权限id',
  permission_name   VARCHAR(100) NOT NULL DEFAULT ''  COMMENT '权限名称',
  permission_sname  VARCHAR(60)  NOT NULL DEFAULT ''  COMMENT '权限简称',
  permission_link   VARCHAR(100) NOT NULL DEFAULT ''  COMMENT '权限链接',
  parent_id         INT          NOT NULL DEFAULT 0   COMMENT '父id',
  enable            TINYINT      NOT NULL DEFAULT 0   COMMENT '是否启用(0否1是)',
  sort_no           INT          NOT NULL DEFAULT 0   COMMENT '显示顺序',
  project_type      TINYINT      NOT NULL DEFAULT 0   COMMENT '所属系统(0:总后台系统,1：桩站系统)',
  remark            VARCHAR(50)  NOT NULL DEFAULT ''  COMMENT '备注',
  create_time       DATETIME     NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='权限信息表 v1.0';

-- -------------------------------
-- TABLE: tb_role_permission_ref
-- 角色权限关联表
-- -------------------------------
DROP TABLE IF EXISTS `tb_role_permission_ref`;
CREATE TABLE `tb_role_permission_ref` (
  id            INT AUTO_INCREMENT  COMMENT '主键-角色权限关联id',
  role_id       INT       NOT NULL DEFAULT 0  COMMENT '角色id',
  permission_id INT       NOT NULL DEFAULT 0  COMMENT '权限id',
  create_time   DATETIME  NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='角色权限关联表 v1.0';


-- ----------------------------
-- TABLE: tb_pre_charge
-- 预充值套餐表
-- ----------------------------
DROP TABLE IF EXISTS `tb_pre_charge`;
CREATE TABLE `tb_pre_charge` (
  id              INT             AUTO_INCREMENT   COMMENT '主键-预充值套餐id',
  original_price  DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '原价',
  final_price     DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '最终价格',
  create_time     DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='预充值套餐表 v1.0';


-- -----------------------
-- TABLE: tb_bank_info
-- 银行信息表
-- -----------------------
DROP TABLE IF EXISTS `tb_bank_info`;
CREATE TABLE tb_bank_info(
  id                   INT      AUTO_INCREMENT COMMENT '主键-银行信息id',
  bank_name            VARCHAR(200)    NOT NULL DEFAULT '' COMMENT '银行名称',
  bank_logo_img        VARCHAR(200)    NOT NULL DEFAULT '' COMMENT '银行Logo',
  parent_id            INT             NOT NULL DEFAULT 0  COMMENT '父节点',
  create_time          DATETIME NOT NULL DEFAULT now()     COMMENT '创建时间',
  PRIMARY KEY (id)
)ENGINE=INNODB
  COMMENT='银行信息表 v1.0' ;



-- -----------------------------------
-- TABLE: tb_station_enter
-- 桩站进出客户登记表（业务暂时不涉及）
-- -----------------------------------
DROP TABLE IF EXISTS `tb_station_enter`;
CREATE TABLE `tb_station_enter` (
  id          INT AUTO_INCREMENT  COMMENT '主键ID',
  name        VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '姓名',
  tel         VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '电话',
  city        VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '城市',
  status      INT         NOT NULL DEFAULT 0   COMMENT '状态(0待审 1已审)',
  create_time DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='桩站进出客户登记表 v1.0';


-- -----------------------
-- TABLE: tb_company_info
-- 桩站运营公司信息表
-- -----------------------
DROP TABLE IF EXISTS `tb_company_info`;
CREATE TABLE `tb_company_info` (
  id                        INT AUTO_INCREMENT  COMMENT '主键-公司ID',
  user_id                   INT          NOT NULL DEFAULT 0  COMMENT '用户ID(桩站主账号)',
  company_name              VARCHAR(100) NOT NULL DEFAULT '' COMMENT '公司名称',
  company_code              VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '统一社会信用代码',
  company_img               VARCHAR(500) NOT NULL DEFAULT '' COMMENT '营业执照',
  manager_name              VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '管理员姓名',
  manager_id_card_no        VARCHAR(18)  NOT NULL DEFAULT '' COMMENT '管理员身份证号',
  manager_hand_id_card_img  VARCHAR(500) NOT NULL DEFAULT '' COMMENT '管理员手持身份证照片正面（身份证正面+本人拍照）',
  manager_hand_id_card_img_back  VARCHAR(500) NOT NULL DEFAULT '' COMMENT '管理员手持身份证照片反面（身份证反面+本人拍照）',
  bank_id                   INT          NOT NULL DEFAULT 0  COMMENT '银行ID',
  debit_card_no             VARCHAR(35)  NOT NULL DEFAULT ''  COMMENT '银行卡账号',
  bank_province_id          INT          NOT NULL DEFAULT 0  COMMENT '开户省ID',
  bank_city_id              INT          NOT NULL DEFAULT 0  COMMENT '开户市ID',
  sub_bank_name             VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '开户支行名称',
  audit_status              TINYINT      NOT NULL DEFAULT 0   COMMENT '审核状态：1审核中 2审核通过 3审核不通过',
  audit_time                DATETIME     NOT NULL DEFAULT now()  COMMENT '审核时间',
  remark                    VARCHAR(200) NOT NULL DEFAULT '' COMMENT '备注',
  commission_ration         DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '抽佣比例(小数)',
  create_time               DATETIME     NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='桩站运营公司信息表 v1.0';


-- ----------------------------
-- TABLE: tb_station_info
-- 桩站信息表(桩站资金账户表)
-- ----------------------------
DROP TABLE IF EXISTS `tb_station_info`;
CREATE TABLE `tb_station_info` (
  id                INT AUTO_INCREMENT  COMMENT '主键-桩站ID',
  company_id        INT NOT NULL DEFAULT 0 COMMENT '公司ID',
  use_balance       DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '桩站余额',
  no_check_balance  DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '未结算金额',
  locked_balance    DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '冻结金额（保留-预留给提现用）',
  charge_price      DECIMAL(10,4) NOT NULL DEFAULT 0 COMMENT '充电单价(元/度)',
  service_price     DECIMAL(10,4) NOT NULL DEFAULT 0 COMMENT '服务费(元/度)',
  status            TINYINT       NOT NULL DEFAULT 0 COMMENT '状态(0不可用，1可用)',
  del_flag          TINYINT       NOT NULL DEFAULT 0 COMMENT '是否删除(0否，1是)',
  remark            VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '备注',
  create_time       DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='桩站信息表 v1.0';


-- ---------------------------
-- TABLE: tb_station_detail
-- 桩站详细信息表
-- ---------------------------
DROP TABLE IF EXISTS `tb_station_detail`;
CREATE TABLE `tb_station_detail` (
  id              INT AUTO_INCREMENT  COMMENT '主键-桩站详细信息ID',
  station_id      INT           NOT NULL DEFAULT 0  COMMENT '所属桩站ID',
  name            VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '桩站名称',
  province        INT           NOT NULL DEFAULT 0  COMMENT '桩站所在省份-预留',
  city            INT           NOT NULL DEFAULT 0  COMMENT '桩站所在城市-预留',
  address         VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '桩站所在地址',
  lat             DECIMAL(12,9) NOT NULL DEFAULT 0  COMMENT '桩站所在纬度',
  lng             DECIMAL(12,9) NOT NULL DEFAULT 0  COMMENT '桩站所在经度',
  cover_img       VARCHAR(500)  NOT NULL DEFAULT 0  COMMENT '桩站封面图片',
  managers        VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '管理公司',
  free_parking    TINYINT       NOT NULL DEFAULT 1  COMMENT '是否免费停车(0:否,1:是)-预留',
  parking_fee     VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '停车费描述',
  parking         TINYINT       NOT NULL DEFAULT 1  COMMENT '停车场(1:地上,2:地下)-预留',
  business_hours  VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '营业时间-预留',
  tel             VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '联系方式',
  star            TINYINT       NOT NULL DEFAULT 5  COMMENT '服务星级(1 2 3 4 5星级)-预留',
  type            TINYINT       NOT NULL DEFAULT 1  COMMENT '类型(0:公共,1:专用,2:私人)-默认1',
  remark          VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '备注',
  create_time     DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='桩站详细信息表 v1.0';


-- ------------------------------
-- TABLE: tb_station_time_price
-- 桩站时段电价表
-- ------------------------------
DROP TABLE IF EXISTS `tb_station_time_price`;
CREATE TABLE `tb_station_time_price` (
  id            INT AUTO_INCREMENT           COMMENT '主键-充电站时段电价ID',
  station_id    INT     NOT NULL DEFAULT 0   COMMENT '所属桩站id',
  time_no       INT     NOT NULL DEFAULT 0   COMMENT '时段序号',
  time_begin    TIME    NOT NULL             COMMENT '开始时段',
  time_end      TIME    NOT NULL             COMMENT '结束时段',
  increase      INT     NOT NULL DEFAULT 0   COMMENT '涨幅(%):+涨-减',
  create_time   DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='充电站价格表 v1.0';


-- ------------------------
-- TABLE: tb_station_image
-- 桩站图片表-暂未使用
-- ------------------------
DROP TABLE IF EXISTS `tb_station_image`;
CREATE TABLE `tb_station_image` (
  id             INT AUTO_INCREMENT  COMMENT '桩站图片表主键ID',
  station_id     INT          NOT NULL DEFAULT 0  COMMENT '所属桩站ID',
  title          VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '图标标题',
  img_url        VARCHAR(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  cover_flag     INT          NOT NULL DEFAULT 0  COMMENT '是否封面(0否1是)',
  create_time    DATETIME     NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='桩站图片表-暂未使用 v1.0';


-- ------------------------------
-- TABLE: tb_station_parking_lot
-- 车位表
-- ------------------------------
DROP TABLE IF EXISTS `tb_station_parking_lot`;
CREATE TABLE `tb_station_parking_lot` (
  id                      INT AUTO_INCREMENT  COMMENT '主键-车位ID',
  station_id              INT         NOT NULL DEFAULT 0    COMMENT '所属桩站ID',
  code                    VARCHAR(50) NOT NULL DEFAULT ''   COMMENT '车位编号',
  status                  TINYINT     NOT NULL DEFAULT 0    COMMENT '车位状态(0:空闲中, 1:被预约, 2:正在使用)',
  create_time             DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='车位表 v1.0';

-- --------------------------
-- TABLE: tb_station_client
-- 充电桩表(终端)
-- --------------------------
DROP TABLE IF EXISTS `tb_station_client`;
CREATE TABLE `tb_station_client` (
  id                      INT         AUTO_INCREMENT        COMMENT '主键-充电桩ID',
  station_id              INT         NOT NULL DEFAULT 0    COMMENT '所属桩站ID',
  code                    VARCHAR(50) NOT NULL DEFAULT ''   COMMENT '充电桩编号',
  terminal_no             VARCHAR(50) NOT NULL DEFAULT ''   COMMENT '终端号（硬件终端号）',
  brand                   VARCHAR(50) NOT NULL DEFAULT ''   COMMENT '品牌',
  gun_type                TINYINT     NOT NULL DEFAULT 1    COMMENT '单双枪类型(1:单枪，2:双枪)',
  charge_type             TINYINT     NOT NULL DEFAULT 1    COMMENT '充电方式(1:直流快充,2:交流快充,3:交流慢充)',
  charge_interface_type   TINYINT     NOT NULL DEFAULT 1    COMMENT '充电接口(1:国标2011,2:国标2015,3:特斯拉)',
  power_min               INT         NOT NULL DEFAULT 0    COMMENT '最低功率(kW)',
  power_max               INT         NOT NULL DEFAULT 0    COMMENT '最高功率(kW)',
  voltage_min             INT         NOT NULL DEFAULT 0    COMMENT '最低电压(V)',
  voltage_max             INT         NOT NULL DEFAULT 0    COMMENT '最高电压(V)',
  auxiliary_type          VARCHAR(50) NOT NULL DEFAULT ''   COMMENT '辅源类型',
  create_time             DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='充电桩表 v1.0';



-- ------------------------------
-- TABLE: tb_station_client_gun
-- 充电枪表
-- ------------------------------
DROP TABLE IF EXISTS `tb_station_client_gun`;
CREATE TABLE `tb_station_client_gun` (
  id                      INT AUTO_INCREMENT  COMMENT '主键-充电枪ID',
  station_id              INT         NOT NULL DEFAULT 0    COMMENT '所属桩站ID',
  station_parking_lot_id  INT         NOT NULL DEFAULT 0    COMMENT '绑定车位ID',
  station_client_id       INT         NOT NULL DEFAULT 0    COMMENT '绑定充电桩ID',
  code                    VARCHAR(50) NOT NULL DEFAULT ''   COMMENT '充电枪编号（自定义）',
  gun_no                  VARCHAR(50) NOT NULL DEFAULT ''   COMMENT '充电枪号（硬件枪编号）',
  status                  TINYINT     NOT NULL DEFAULT 1    COMMENT '充电枪状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)',
  voltage                 VARCHAR(10) NOT NULL DEFAULT ''   COMMENT '充电时电压(V)',
  electric                VARCHAR(10) NOT NULL DEFAULT ''   COMMENT '充电时电流(A)',
  power                   VARCHAR(10) NOT NULL DEFAULT ''   COMMENT '充电时功率(KW)',
  temperature             VARCHAR(10) NOT NULL DEFAULT ''   COMMENT '充电时温度(℃)',
  percentage              VARCHAR(10) NOT NULL DEFAULT ''   COMMENT '充电百分比(%)',
  create_time             DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='充电枪表 v1.0';




-- ------------------------------------
-- TABLE: tb_coupon
-- 优惠券表，包含总后台发布和桩站发布
-- 需要做逻辑删除
-- 桩站只能发放用电券，总后台两种都能发放
-- ------------------------------------
DROP TABLE IF EXISTS `tb_coupon`;
CREATE TABLE `tb_coupon` (
  id                  INT AUTO_INCREMENT  COMMENT '主键-优惠券ID',
  station_id          INT             NOT NULL DEFAULT 0  COMMENT '所属桩站ID',
  type                TINYINT         NOT NULL DEFAULT 2  COMMENT '优惠券类型（1：充值通用优惠券 2：用电通用优惠券,3:桩站用电优惠券）',
  scope_type          TINYINT         NOT NULL DEFAULT 0  COMMENT '通用券适用范围类型(0: 全部-通用优惠券, 1: 指定桩站适用-用电优惠券)',
  price               DECIMAL(10,2)   NOT NULL DEFAULT 0  COMMENT '面额',
  threshold_price     DECIMAL(10,2)   NOT NULL DEFAULT 0  COMMENT '门槛金额',
  total_number        INT             NOT NULL DEFAULT 0  COMMENT '总数',
  limit_number        INT             NOT NULL DEFAULT 0  COMMENT '限领',
  left_number         INT             NOT NULL DEFAULT 0  COMMENT '剩余数量',
  status              INT             NOT NULL DEFAULT 0  COMMENT '状态(0：生效中 1：已过期 2：已删除)',
  collect_start_time  DATETIME        NOT NULL DEFAULT now()  COMMENT '领取开始时间',
  collect_end_time    DATETIME        NOT NULL DEFAULT now()  COMMENT '领取结束时间',
  use_start_time      DATETIME        NOT NULL DEFAULT now()  COMMENT '使用开始时间',
  use_end_time        DATETIME        NOT NULL DEFAULT now()  COMMENT '使用结束时间',
  create_time         DATETIME        NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='优惠券表 v1.0';


-- ------------------------------------
-- TABLE: tb_coupon_station_ref
-- 优惠券适用桩站关联表
-- 如果一张优惠券没有在此表中找到关联记录，则表示适用所有桩站
-- ------------------------------------
DROP TABLE IF EXISTS `tb_coupon_station_ref`;
CREATE TABLE `tb_coupon_station_ref` (
  id                  INT AUTO_INCREMENT  COMMENT '主键-优惠券适用桩站ID',
  coupon_id           INT             NOT NULL DEFAULT 0  COMMENT '优惠券ID',
  station_id          INT             NOT NULL DEFAULT 0  COMMENT '适用桩站ID',
  create_time         DATETIME        NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='优惠券适用桩站关联表 v1.0';



-- ------------------------------------
-- TABLE: tb_coupon_user_read_ref
-- 优惠券用户是否已读表
-- 如果用户已经查看过优惠券,则在此表中针对该用户对查看过的优惠券加入已读标记
-- ------------------------------------
DROP TABLE IF EXISTS `tb_coupon_user_read_ref`;
CREATE TABLE `tb_coupon_user_read_ref` (
  id                 INT AUTO_INCREMENT  COMMENT '主键-优惠券已读ID',
  coupon_id          INT             NOT NULL DEFAULT 0  COMMENT '优惠券ID',
  user_id            INT             NOT NULL DEFAULT 0  COMMENT '用户ID',
  is_read            TINYINT         NOT NULL DEFAULT 0  COMMENT '已读标记(0:未读,1:已读)',
  create_time        DATETIME        NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='用户是否已读优惠券表 v1.0';


-- -------------------------
-- TABLE: tb_user_coupon
-- 车主用户领取优惠券记录表
-- -------------------------
DROP TABLE IF EXISTS `tb_user_coupon`;
CREATE TABLE `tb_user_coupon` (
  id            INT AUTO_INCREMENT  COMMENT '主键-用户领取优惠券ID',
  coupon_id     INT         NOT NULL DEFAULT 0   COMMENT '优惠券ID',
  user_id       INT         NOT NULL DEFAULT 0   COMMENT '用户ID',-- 一般只会是车主用户
  serial_number VARCHAR(20) NOT NULL DEFAULT ''  COMMENT '领取流水号',
  status        TINYINT     NOT NULL DEFAULT 0   COMMENT '优惠券状态（0：未使用 1：已使用 2:冻结 3:已过期）',
  frozen_time   DATETIME                         COMMENT '冻结时间',
  order_sn      VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '对应的订单', -- TODO：优惠券是否能多次使用？
  create_time   DATETIME NOT NULL DEFAULT now()  COMMENT '领取时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='用户领取优惠券表 v1.0';


-- --------------------------------
-- TABLE: tb_order
-- TODO 订单表
-- --------------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  id              INT   AUTO_INCREMENT  COMMENT '主键-订单id',
  order_sn        VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '订单编号',
  order_type      TINYINT       NOT NULL DEFAULT 1  COMMENT '订单类型（1:账户充值，2:充电消费,3:余额退款）',
  user_id         INT           NOT NULL DEFAULT 0  COMMENT '用户id',
  total_price     DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '支付金额',
  pay_status      TINYINT       NOT NULL DEFAULT 0  COMMENT '支付状态（0:未支付,1:支付成功,2:支付失败）',
  pay_type        TINYINT       NOT NULL DEFAULT 1  COMMENT '支付方式（1:微信支付,2:账户余额）',
  pay_time        DATETIME      NOT NULL DEFAULT now()  COMMENT '支付时间',
  create_time     DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='订单表 v1.0';


-- --------------------------------
-- TABLE: tb_recharge_log
-- TODO 车主用户充值记录表
-- --------------------------------
DROP TABLE IF EXISTS `tb_recharge_log`;
CREATE TABLE `tb_recharge_log` (
  id              INT   AUTO_INCREMENT  COMMENT '主键-用户充值订单id',
  order_id        INT           NOT NULL DEFAULT 0 COMMENT '订单id',
  order_sn        VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '订单编号',
  user_id         INT           NOT NULL DEFAULT 0  COMMENT '用户id（车主用户）',
  pre_charge_id   INT           NOT NULL DEFAULT 0  COMMENT '预充值套餐id',
  original_price  DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '预充值套餐原价金额',
  final_price     DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '预充值套餐最终金额',
  user_coupon_id  INT           NOT NULL DEFAULT 0  COMMENT '用户领取优惠券id',
  coupon_price    DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '优惠券减免价格',
  total_price     DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '实付金额',
  pay_time        DATETIME      NOT NULL DEFAULT now()  COMMENT '支付时间',
  create_time     DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='车主用户充值记录表 v1.0';


-- --------------------------------
-- TABLE: tb_refund_log
-- TODO 用户退款记录表
-- --------------------------------
DROP TABLE IF EXISTS `tb_refund_log`;
CREATE TABLE `tb_refund_log` (
  id              INT   AUTO_INCREMENT  COMMENT '主键-用户充值订单id',
  order_id        INT           NOT NULL DEFAULT 0 COMMENT '订单id',
  order_sn        VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '订单编号',
  payment_sn      varchar(64)   NOT NULL DEFAULT '' COMMENT '企业付款成功，返回的微信付款单号',
  user_id         INT           NOT NULL DEFAULT 0  COMMENT '用户id（车主用户）',
  total_price     DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '退款金额',
  refund_status   TINYINT       NOT NULL DEFAULT 1  COMMENT '退款状态（1：退款成功,2:退款失败）',
  pay_time        DATETIME      NOT NULL DEFAULT now()  COMMENT '退款时间',
  create_time     DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='用户退款记录表 v1.0';


-- -------------------------
-- TABLE: tb_charge_log
-- TODO:车主用户充电记录表
-- TODO:时段充电度数如何统计？？？？
-- -------------------------
DROP TABLE IF EXISTS `tb_charge_log`;
CREATE TABLE `tb_charge_log` (
  id                  INT AUTO_INCREMENT  COMMENT '主键-用户充电记录id',
  user_id             INT           NOT NULL DEFAULT 0 COMMENT '车主用户ID',
  order_id            INT           NOT NULL DEFAULT 0 COMMENT '订单id',
  order_sn            VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '订单编号',
  station_id          INT           NOT NULL DEFAULT 0  COMMENT '桩站id',
  station_name        VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '桩站名称',
  client_id           INT           NOT NULL DEFAULT 0  COMMENT '充电桩id',
  client_code         VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '充电桩编号',
  client_charge_type  TINYINT       NOT NULL DEFAULT 1  COMMENT '充电桩类型(1:直流快充,2:交流快充,3:交流慢充)',
  parking_lot_id      INT           NOT NULL DEFAULT 0  COMMENT '车位id',
  parking_lot_code    VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '车位编号',
  client_gun_id       INT           NOT NULL DEFAULT 0  COMMENT '充电枪id',
  client_gun_code     VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '充电枪编号',
  begin_time          DATETIME      NOT NULL DEFAULT now()  COMMENT '充电开始时间',
  end_time            DATETIME      NOT NULL DEFAULT now()  COMMENT '充电结束时间',
  degree_predict      DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '预估充电度数--用不到',
  degree_real         DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '实际充电度数',
  user_coupon_id      INT           NOT NULL DEFAULT 0  COMMENT '用户使用优惠券id',
  price_coupon        DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '优惠券减免的金额',
  charge_price        DECIMAL(10,4) NOT NULL DEFAULT 0  COMMENT '充电单价-基础电价',
  charge_prices       VARCHAR(50)   NOT NULL DEFAULT 0  COMMENT '电价-充电过程中涉及到的电价',
  service_price       DECIMAL(10,4) NOT NULL DEFAULT 0  COMMENT '服务费价格',
  price_real          DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '需要支付的金额',
  vehicle_type        VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '车型',
  plate_number        VARCHAR(20)   NOT NULL DEFAULT '' COMMENT '车牌号',
  status              TINYINT       NOT NULL DEFAULT 2  COMMENT '充电状态(1:充电中,2:充电成功,3:充电失败)',
  create_time         DATETIME      NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='车主用户充电记录表 v1.0';



-- -----------------------------
-- TABLE:
-- TODO:桩站结算记录（定时结算）
-- -----------------------------


-- -----------------------------
-- TABLE:
-- TODO:桩站自动转账记录--一期不做
-- -----------------------------

-- -----------------------------
-- TABLE: tb_new_life_spend_log
-- TODO: 新活资金流水表
-- 如果类型是1、2、3,则用户ID应该是车主ID
-- 如果类型是4、5，则用户ID是此桩站的主账号
-- -----------------------------
DROP TABLE IF EXISTS `tb_new_life_spend_log`;
CREATE TABLE `tb_new_life_spend_log` (
  id                            INT AUTO_INCREMENT  COMMENT '主键-新活资金流水id',
  money_sn                      VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '资金流水号',
  order_id                      INT           NOT NULL DEFAULT 0  COMMENT '订单id',
  order_sn                      VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '订单号',
  type                          TINYINT       NOT NULL DEFAULT 1  COMMENT '类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)',
  type_remark                   TINYINT       NOT NULL DEFAULT 1  COMMENT '类型备注(1:充值,2:消费,3:退款,4:卖电收入,5:服务费收入)',
  amount                        DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '操作金额',
  user_id                       INT           NOT NULL DEFAULT 0  COMMENT '用户id',
  user_type                     INT           NOT NULL DEFAULT 0  COMMENT '用户类型',
  user_name                     VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '用户姓名',
  user_mobile                   VARCHAR(11)   NOT NULL DEFAULT '' COMMENT '用户手机号码',
  user_avatar_url               VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '用户微信头像',
  user_old_balance              DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '用户历史余额',
  user_gain_amount              DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '用户可得金额',
  user_now_balance              DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '用户当前余额',
  station_id                    INT           NOT NULL DEFAULT 0  COMMENT '桩站id',
  station_name                  VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '桩站名称',
  commission_ration             DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '抽佣比',
  service_price                 DECIMAL(10,4) NOT NULL DEFAULT 0  COMMENT '服务费',
  station_gain_amount           DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站可得金额',
  station_gain_amount_charge    DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站可得电费',
  station_gain_amount_service   DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站可得服务费',
  station_old_no_check_amount   DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站历史未结算金额',
  station_now_no_check_amount   DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站未结算金额',
  station_old_balance           DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站历史余额',
  station_now_balance           DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站余额',
  new_life_gain_amount          DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '新活可得金额',
  coupon_amount                 DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '优惠券减免金额',
  coupon_type                   TINYINT       NOT NULL DEFAULT 0  COMMENT '优惠券类型',
  remark                        VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '备注',
  create_time                   DATETIME    NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='新活资金流水表 v1.0';


--
-- TABLE: tb_weixin_pay_log
-- 微信支付记录表(充值支付)
--
DROP TABLE IF EXISTS `tb_weixin_pay_log`;
CREATE TABLE `tb_weixin_pay_log` (
  id                   INT AUTO_INCREMENT  COMMENT '主键-微信支付记录ID',
  appid                VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '开发者ID',
  mch_id               VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '商户号ID',
  device_info          VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '设备号',
  nonce_str            VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '随机字符串',
  sign                 VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '签名',
  sign_type            VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '签名类型',
  result_code          VARCHAR(16)    NOT NULL DEFAULT ''  COMMENT '业务结果',
  err_code             VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '错误代码',
  err_code_des         VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '错误代码描述',
  openid               VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '微信用户标识openid',
  is_subscribe         VARCHAR(1)     NOT NULL DEFAULT ''  COMMENT '是否关注公众号',
  trade_type           VARCHAR(16)    NOT NULL DEFAULT ''  COMMENT '交易类型',
  bank_type            VARCHAR(16)    NOT NULL DEFAULT ''  COMMENT '付款银行',
  total_fee            DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '订单金额',
  settlement_total_fee DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '应结订单金额',
  fee_type             VARCHAR(8)     NOT NULL DEFAULT ''  COMMENT '货币种类',
  cash_fee             DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '现金支付金额',
  cash_fee_type        VARCHAR(16)    NOT NULL DEFAULT ''  COMMENT '现金支付货币类型',
  coupon_fee           DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '总代金券金额',
  coupon_count         INT            NOT NULL DEFAULT 0   COMMENT '代金券使用数量',
  transaction_id       VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '微信支付订单号',
  out_trade_no         VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '商户订单号(本系统内订单号)',
  attach               VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '商家数据包',
  time_end             VARCHAR(14)    NOT NULL DEFAULT ''  COMMENT '支付完成时间',
  return_code          VARCHAR(16)    NOT NULL DEFAULT ''  COMMENT '返回状态码',
  return_msg           VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '返回信息',
  create_time          DATETIME       NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='微信支付记录表 v1.0';


--
-- TABLE: tb_user
-- 用户表(包含车主用户,桩站用户（主/子账号），总后台用户)
--
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  id            INT            AUTO_INCREMENT       COMMENT '主键-用户ID',
  user_type     TINYINT        NOT NULL DEFAULT 1   COMMENT '用户类型(1:车主用户,2:桩站系统主账号,3:桩站系统子账号,4:总后台系统用户)',
  status        TINYINT        NOT NULL DEFAULT 1   COMMENT '账号状态(1:启用,2:禁用,3:锁定,4:过期)',
  user_name     VARCHAR(50)    NOT NULL DEFAULT ''  COMMENT '登录账号(可能与手机号码一致)',
  password      VARCHAR(128)   NOT NULL DEFAULT ''  COMMENT '密码',
  salt          VARCHAR(32)    NOT NULL DEFAULT ''  COMMENT '盐值',
  mobile        VARCHAR(11)    NOT NULL DEFAULT ''  COMMENT '手机号码',
  email         VARCHAR(50)    NOT NULL DEFAULT ''  COMMENT '电子邮箱',
  real_name     VARCHAR(100)   NOT NULL DEFAULT ''  COMMENT '姓名',
  nick_name     VARCHAR(100)   NOT NULL DEFAULT ''  COMMENT '昵称/账号名称',
  avatar_url    VARCHAR(500)   NOT NULL DEFAULT ''  COMMENT '头像',
  balance       DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '账户总余额',
  gender        TINYINT        NOT NULL DEFAULT 0   COMMENT '性别(0男，1女)',
  verify_email  TINYINT        NOT NULL DEFAULT 0   COMMENT '是否验证邮箱(0否，1是)',
  verify_mobile TINYINT        NOT NULL DEFAULT 0   COMMENT '是否验证手机(0否，1是)',
  ip            VARCHAR(20)    NOT NULL DEFAULT ''  COMMENT 'ip地址',
  create_user   VARCHAR(30)    NOT NULL DEFAULT ''  COMMENT '创建者',
  lift_ban_time DATETIME       NOT NULL DEFAULT now()  COMMENT '解锁时间',
  login_time    DATETIME       NOT NULL DEFAULT now()  COMMENT '登录时间',
  create_time   DATETIME       NOT NULL DEFAULT now()  COMMENT '注册时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='用户表 v1.0';


-- ------------------------------------------------
-- TABLE: tb_user_account
-- 用户账户表-特指车主用户
-- -------------------------------------------------
DROP TABLE IF EXISTS `tb_user_account`;
CREATE TABLE `tb_user_account` (
  id                    INT            AUTO_INCREMENT       COMMENT '主键-用户账户ID',
  user_id               INT            NOT NULL DEFAULT 0   COMMENT '用户ID',
  charge_balance        DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '充值金额(未参与优惠)', -- 实得金额=实付金额
  coupon_charge_balance DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '优惠充值金额(使用优惠券实付金额)', -- 使用优惠券充值实付金额
  give_balance          DECIMAL(10,2)  NOT NULL DEFAULT 0   COMMENT '赠送金额(优惠券赠送金额)', -- 使用优惠券充值赠送的金额
  create_time           DATETIME       NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='用户账户表 v1.0';


-- ----------------------------
-- TABLE: tb_station_user_ref
-- 桩站用户关联表
-- -----------------------------
DROP TABLE IF EXISTS `tb_station_user_ref`;
CREATE TABLE `tb_station_user_ref` (
  id          INT AUTO_INCREMENT  COMMENT '主键-桩站用户关联id',
  station_id  INT       NOT NULL DEFAULT 0  COMMENT '桩站id',
  user_id     INT       NOT NULL DEFAULT 0  COMMENT '用户id(桩站用户)',
  status      TINYINT   NOT NULL DEFAULT 1  COMMENT '状态(1:启用，2停用)',
  create_time DATETIME  NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='桩站用户关联表 v1.0';




--
-- TABLE: tb_role_user_ref
-- 角色用户关联表
--
DROP TABLE IF EXISTS `tb_role_user_ref`;
CREATE TABLE `tb_role_user_ref` (
  id            INT AUTO_INCREMENT  COMMENT '主键ID',
  role_id       INT       NOT NULL DEFAULT 0  COMMENT '角色id',
  user_id       INT       NOT NULL DEFAULT 0  COMMENT '用户id',
  create_time   DATETIME  NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='角色用户关联表 v1.0';


-- -----------------------
-- TABLE: tb_user_token
-- 用户Token表
-- -----------------------
DROP TABLE IF EXISTS `tb_user_token`;
CREATE TABLE `tb_user_token` (
  id          INT AUTO_INCREMENT  COMMENT '主键-用户Token id',
  token       VARCHAR(1500) NOT NULL DEFAULT '' COMMENT 'token值',
  user_id     INT      NOT NULL DEFAULT 0 COMMENT '用户ID', --
  expire_time DATETIME NOT NULL DEFAULT now()  COMMENT '过期时间',
  create_time DATETIME NOT NULL DEFAULT now()  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='用户Token表 v1.0';



-- --------------------------
-- TABLE: tb_user_weixin
-- 用户绑定的微信账号信息表
-- --------------------------
DROP TABLE IF EXISTS `tb_user_weixin`;
CREATE TABLE `tb_user_weixin` (
  id          INT AUTO_INCREMENT COMMENT '主键-用户微信信息ID',
  user_id     INT           NOT NULL DEFAULT 0  COMMENT '所属用户id',-- 用户表主键ID
  open_id     VARCHAR(128)  NOT NULL DEFAULT '' COMMENT '微信openId',
  nick_name   VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '微信昵称',
  union_id    VARCHAR(128)  NOT NULL DEFAULT '' COMMENT 'unionId',
  avatar_url  VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '微信头像',
  province    VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '微信所在省份',
  city        VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '微信所在城市',
  gender      TINYINT       NOT NULL DEFAULT 0  COMMENT '微信性别(0:男,1女:)',
  create_time DATETIME      NOT NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = INNODB
  COMMENT ='用户微信信息表 v1.0';




-- ------------------------------
-- TABLE: tb_write_invoice_log
-- 发票记录表（业务暂时不涉及）
-- 一般来说只涉及桩站用户才会申请发票
-- ------------------------------
DROP TABLE IF EXISTS `tb_write_invoice_log`;
CREATE TABLE `tb_write_invoice_log` (
 id                   INT            AUTO_INCREMENT      COMMENT '主键-发票记录ID',
 user_id              INT            NOT NULL DEFAULT 0  COMMENT '用户ID',
 invoice_Sn           VARCHAR(32)    NOT NULL DEFAULT '' COMMENT '申请流水号',
 amount               DECIMAL(10,2)  NOT NULL DEFAULT 0  COMMENT '发票面额',
 status               TINYINT        NOT NULL DEFAULT 1  COMMENT '发票状态（1：待发送 2：已发送）',
 receiver_address     VARCHAR(500)   NOT NULL DEFAULT '' COMMENT '收货地址',
 express_company_type TINYINT        NOT NULL DEFAULT 0  COMMENT '快递类型',
 express_no           VARCHAR(30)    NOT NULL DEFAULT '' COMMENT '快递单号',
 create_time          DATETIME       NOT NULL DEFAULT now()  COMMENT '创建时间',
 PRIMARY KEY (id)
)
ENGINE = INNODB
COMMENT ='发票记录表 v1.0';



-- ----------------------------
-- Table ：tb_dict_type
-- 数据类型表
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_type`;
CREATE TABLE `tb_dict_type`  (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键-数据类型id',
  `data_type_code` varchar(25) NOT NULL DEFAULT '' COMMENT '数据类型编码',
  `data_type_name` varchar(50) NOT NULL DEFAULT '' COMMENT '数据类型名称',
  `remark` varchar(20) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据类型表 v1.0';



-- ----------------------------
-- Table : tb_common_dict
-- 数据字典表
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_data`;
CREATE TABLE `tb_dict_data`  (
  `id`            INT NOT NULL AUTO_INCREMENT COMMENT '主键-数据字典id',
  `parent_id`     INT NOT NULL DEFAULT 0 COMMENT '父节点ID',
  `valid`         TINYINT NOT NULL DEFAULT 1 COMMENT '是否有效(0否，1是)',
  `sort_number`   INT NOT NULL DEFAULT 0 COMMENT '排序号',
  `dict_type_id`  TINYINT NOT NULL DEFAULT 0 COMMENT '数据类型ID',
  `dict_code`     varchar(20) NOT NULL DEFAULT '' COMMENT '数据编码',
  `dict_name`     varchar(20) NOT NULL DEFAULT '' COMMENT '数据名称',
  `create_time`   DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `remark`        varchar(20) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '数据字典表 v1.0';



-- ----------------------------
-- Table ： tb_task_error_log
-- 定时任务错误日志表
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_error_log`;
CREATE TABLE `tb_task_error_log`  (
  `id`            int NOT NULL AUTO_INCREMENT,
  `class_path`    varchar(125)  NOT NULL DEFAULT '' COMMENT '任务执行类-全路径',
  `extend_param`  varchar(255)  NOT NULL DEFAULT '' COMMENT '扩展参数',
  `action_type`   varchar(20)   NOT NULL DEFAULT '' COMMENT '操作类型',
  `action_status` varchar(30)   NOT NULL DEFAULT '' COMMENT '操作状态',
  `remark`        TEXT                              COMMENT '备注',
  `create_time` datetime        NOT NULL  DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '定时任务错误日志表 v1.0';



-- ----------------------------
-- Table ： tb_sms_log
-- 短信日志表
-- ----------------------------
DROP TABLE IF EXISTS `tb_sms_log`;
CREATE TABLE `tb_sms_log`  (
  `id`          int NOT NULL AUTO_INCREMENT,
  `mobile`      varchar(11)  NOT NULL DEFAULT '' COMMENT '手机号',
  `sms_content` varchar(255) NOT NULL DEFAULT '' COMMENT '短信内容',
  `template_id` varchar(10)  NOT NULL DEFAULT '' COMMENT '模版ID',
  `send_status` tinyint      NOT NULL DEFAULT 1 COMMENT '发送状态',
  `create_time` datetime     NOT NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '短信日志表 v1.1' ;



-- ------------------------------
-- TABLE:
-- TODO：快递类型表（暂时不涉及）
--
-- ------------------------------

-- =======================结束===================================

