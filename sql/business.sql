drop table if exists `station`;
create table `station`
(
    `id`     bigint not null comment 'id',
    `name` varchar(20) not null comment '站名',
    `name_pinyin` varchar(50) not null comment '站名拼音',
    `name_py` varchar(50) not null comment '站名拼音首字母',
    `create_time` datetime(3) comment '新增时间',
    `update_time` datetime(3) comment '更新时间',
    primary key (`id`),
    unique key `name_unique` (`name`)
) engine=InnoDB default charset=utf8 comment='车站表';
