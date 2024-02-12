drop table if exists `member`;
create table `member`
(
    `id`     bigint not null comment 'id',
    `mobile` varchar(11) not null comment '手机号',
    primary key (`id`),
    unique key `mobile_unique` (`mobile`)
) engine=InnoDB default charset=utf8 comment='会员表';

drop table if exists `passenger`;
create table `passenger`
(
    `id`     bigint not null comment 'id',
    `member_id` bigint not null comment '会员id',
    `name` varchar(50) not null comment '姓名',
    `id_card` varchar(18) not null comment '身份证号',
    `type` char(2) not null comment '乘客类型|枚举[PassengerTypeEnum]',
    `create_time` datetime not null comment '创建时间',
    `update_time` datetime not null comment '更新时间',
    primary key (`id`),
    index `member_id_index` (`member_id`)
) engine=InnoDB default charset=utf8 comment='乘客表';

drop table if exists member_ticket;
create table member_ticket
(
    `id`     bigint not null comment 'id',
    `member_id` bigint not null comment '会员id',
    `passenger_id` bigint not null comment '乘客id',
    `passenger_name` varchar(50) not null comment '乘客姓名',
    `date` date not null comment '日期',
    `train_code` varchar(20) not null comment '车次编号',
    `carriage_index` int not null comment '车厢索引',
    `row` char(2) not null comment '行号|01、02',
    `col` char(2) not null comment '列号|枚举[SeatColEnum]',
    `start` varchar(20) not null comment '出发站',
    `start_time` time not null comment '出发时间',
    `end` varchar(20) not null comment '终点站',
    `end_time` time not null comment '到站时间',
    `seat_type` char(2) not null comment '座位类型|枚举[SeatTypeEnum]',
    `create_time` datetime not null comment '创建时间',
    `update_time` datetime not null comment '更新时间',
    primary key (`id`),
    index `member_id_index` (`member_id`)
) engine=InnoDB default charset=utf8 comment='会员车票表';