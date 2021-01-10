

-- 充电枪表增加字段
alter table tb_station_client_gun  add charge_time VARCHAR(10) NOT NULL DEFAULT ''  COMMENT '充电时间(秒)';
alter table tb_station_client_gun  add charge_energy VARCHAR(10) NOT NULL DEFAULT ''  COMMENT '充电电能(KWH)';
alter table tb_station_client_gun  add charge_amount VARCHAR(10) NOT NULL DEFAULT ''  COMMENT '充电金额(元)';

