

-- 订单表表增加2个字段：
ALTER TABLE `new_life_charge`.`tb_order`
ADD COLUMN `sum_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '订单总额（支付金额+优惠券减免金额）' AFTER `user_id`,
ADD COLUMN `coupon_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠券减免金额' AFTER `total_price`;

-- 充电记录表增加一个字段
ALTER TABLE `new_life_charge`.`tb_charge_log`
ADD COLUMN `stop_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '结束类型(1:充电中,2：用户手动停止，3：意外停止，4：余额不足停止，5：充电充满后自动停止)' AFTER `status`;