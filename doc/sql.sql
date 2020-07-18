drop database if exists manager_system_develop;
create database manager_system_develop;

use manager_system_develop;

-- 建立用户表
drop table if exists user;
create table user (
  `id` int unsigned primary key auto_increment,
  `account` varchar(20) not null comment '用户帐号，不可为空',
  `password` varchar(32) not null comment '用户密码',
  `name` varchar(20) not null comment '用户名',
  `sex` enum('M', 'W') not null default 'M' comment '性别',
  `phone` varchar(20) not null default 'NULL' comment '电话',
  `email` varchar(20) not null default 'NULL' comment '邮箱',
  `bank_number` varchar(25) not null default 'NULL' comment '银行卡号',
  `education` ENUM('B', 'M', 'D', 'PD') not null default 'B' comment '学历',
  `major` int not null default 'NULL' comment '专业',
  `status`	ENUM('AT', 'LEAVE') not null default 'AT' comment '是否在校',
  `gmt_create` datetime not null default now() comment '创建时间',
  `gmt_modified` datetime not null default now() comment '最近一次修改时间'
) charset = utf8;


drop table if exists `code_language`;
create table `code_language` (
  `id` int unsigned primary key auto_increment comment '主键',
  `languang` varchar(20) not null comment '语言名称'
) charset = utf8;


drop table if exists `major`;
create table `major` (
  `id` int unsigned primary key auto_increment comment '主键',
  `major_code` int not null comment '专业编号',
  `name` varchar(20) not null comment '专业名称'
) charset = utf8;

drop table if exists `authority`;
create table `authority` (
  `id`        int unsigned primary key auto_increment comment '主键',
  `role_name` varchar(20) not null comment '角色名称'
) charset = utf8;

drop table if exists `project_manage`;
create table `project_manage`(
   `id` int unsigned primary key auto_increment comment '主键',
   `name` varchar (20) not null comment '项目名',
   `parent_id`  int not null comment '区别是否是子模块还是主模块',
   `create_id` int not null comment '创建人id',
   `project_description` varchar (100) not null comment,'对于本项目或者本模块的描述',
   `project_situation` int not null comment,'工程或者模块完成情况，未完成，完成',
    `gmt_create` datetime  not null default now() comment '创建时间',
    `gmt_modified` datetime  not null default now() comment '最近一次修改时间',
   `reserved_filds` varchar (255) '预留位1',
    `reserved_filds2` varchar (255)  '预留位2',
    `reserved_filds3` varchar (255)  '预留位3',
   `reserved_filds4` varchar (255)  '预留位4',
   `reserved_filds5` varchar (255)  '预留位5'
)charset = utf8;

drop table if exists `project_manage_user`;
create table `project_manage_user`(
  `user_id`      int      not null comment '用户主键',
  `project_manage_id`   int      not null comment '项目管理表主键',
  `parent_id`   int      not null comment '主模块主键',
    `gmt_create`   datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间'
  )charset = utf8;

drop table if exists `project_user_authority`;
create table `project_user_authority` (
  `user_id`      int      not null comment '用户主键',
  `role_id`      int      not null comment '角色主键',
  `project_id`   int      not null comment '项目主键',
  `gmt_create`   datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间'
)charset = utf8;

drop table if exists `user_language`;
create table `user_language` (
  `user_id`      int      not null comment '用户主键',
  `language_id`  int      not null comment '语言主键',
  `gmt_create`   datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间'
)charset = utf8;

-- 插入用户表测试数据
insert into user (account, password, name, phone, email, major, gmt_create, gmt_modified)
values ('17060101101', UPPER(MD5('123456789FZS.MANAGEMENT')), '张三', '15548967723', '123465@163.com', 0601, now(), now()),
       ('17060202102', UPPER(MD5('123456789FZS.MANAGEMENT')), '李四', '11577889966', '155778475@qq.com', 0602, now(), now());

insert into major (major_code, name)
values (0601, '计算机科学与技术'),
       (0602, '软件工程');

insert into code_language (languang)
values ('JAVA'),
       ('PYTHON'),
       ('C'),
       ('CPP'),
       ('JAVASCRIPT'),
       ('HTML/CSS'),
       ('C#');

insert into authority (role_name)
values ('老师'),
       ('学生'),
       ('主管理员'),
       ('次级管理员');






-- 对数据库中的明文密码进行加密
update manager_system_develop.user set password = upper(md5(password + 'FZS.MANAGEMENT'));

-- 修改旧有数据库中用户表密码字段长度不足的问题
-- MD5加密之后密码长度会固定的为32位，原SQL脚本之中将该字段的长度设置为20，在存储加密密码时会报错
ALTER TABLE manager_system_develop.user MODIFY COLUMN password varchar(32);









