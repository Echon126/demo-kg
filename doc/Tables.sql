-- 用户表
create table tb_user(
--   u_id int primary key, -- 整数
                        d_id varchar(100), -- 地磅唯一标识，可以为空
                        username varchar(100) PRIMARY KEY,
                        password varchar(100) NOT NULL
);

-- 地磅表
create table tb_dibang(
                          d_id varchar(100) PRIMARY KEY, -- 地磅唯一标识，可以为空
                          d_name varchar(100),
                          d_status varchar(100),
);

-- 地磅数据表(对telit发来的信息进行筛选)
create table tb_dibangdata(
                              d_id varchar(100) PRIMARY KEY, -- 地磅唯一标识，可以为空
                              weight int NOT NULL,
                              create_time datetime,
);

-- 订单表
create table tb_order(
                         o_id int PRIMARY KEY, -- 地磅唯一标识，可以为空
                         num_plate varchar(100) REFERENCES KEY,
                         d_name varchar(100) NOT NULL,
                         weight int NOT NULL,
                         entry_type varchar(100) NOT NULL, -- 进出类型
                         gross_weight DOUBLE, -- 毛重
                         tare_weight  DOUBLE, -- 皮重
                         item_name varchar(100), -- 物品名称
                         unit_price int, -- 单价
                         shipper varchar(100) NOT NULL,  -- 发货单位
                         receiver varchar(100) NOT NULL, -- 收货单位
                         o_status int DEFAULT(1),  -- 订单状态默认为1，当状态为2时提示打印
                         create_time datetime,
                         descrebe varchar(100)
);



-- 车牌关联
create table tb_plate(
                         num_plate varchar(100) PRIMARY KEY, -- 车牌号
                         shipper varchar(100),  -- 发货单位
                         receiver varchar(100)  -- 收货单位
);




