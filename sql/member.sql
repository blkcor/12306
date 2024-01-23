drop table if exists `member`;
create table `member`
(
    `id`     bigint not null comment 'id',
    `mobile` varchar(11) not null comment '手机号',
    primary key (`id`),
    unique key `mobile_unique` (`mobile`)
) engine=InnoDB default charset=utf8 comment='会员表';
