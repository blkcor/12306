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