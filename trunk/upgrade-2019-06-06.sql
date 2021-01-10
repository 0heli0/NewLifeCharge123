

-- 新活资金统计表增加4个字段：
alter table tb_new_life_spend_log  add station_gain_amount_charge DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站可得电费';
alter table tb_new_life_spend_log  add station_gain_amount_service DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '桩站可得服务费';
alter table tb_new_life_spend_log  add coupon_amount DECIMAL(10,2) NOT NULL DEFAULT 0  COMMENT '优惠券减免金额';
alter table tb_new_life_spend_log  add coupon_type  TINYINT NOT NULL DEFAULT 0  COMMENT '优惠券类型';

