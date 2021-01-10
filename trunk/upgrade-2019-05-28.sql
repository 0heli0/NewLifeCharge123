ALTER TABLE `new_life_charge`.`tb_weixin_refund_log`
MODIFY COLUMN `payment_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '企业付款成功时间' AFTER `attach`;
