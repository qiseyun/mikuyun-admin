-- 建库 库名mikuyun,可以换成自己想要的
# create schema mikuyun collate utf8mb4_general_ci;

-- auto-generated definition
create table mk_captcha
(
    id              int auto_increment comment 'id'
        primary key,
    account         varchar(64)                         not null comment '接收账号',
    captcha_str     varchar(16)                         not null comment '验证码',
    captcha_type    tinyint   default 0                 not null comment '验证码类型(1手机号验证码,2邮箱验证码)',
    gmt_created     timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    expiration_time timestamp default CURRENT_TIMESTAMP not null comment '到期时间'
)
    comment '验证码表';

create index index_account
    on mk_captcha (account);

create index index_type_account
    on mk_captcha (captcha_type, account);




create table mk_mq_msg_record
(
    id             int auto_increment
        primary key,
    msg_id         varchar(128) default ''                not null comment 'MQ消息id',
    msg_body       text                                   null comment 'MQ消息体',
    business_type  tinyint      default 0                 not null comment '业务类型枚举',
    consume_status tinyint      default 0                 not null comment '消息消费状态(0:未消费  1:已销费)',
    gmt_created    timestamp    default CURRENT_TIMESTAMP null comment '创建时间',
    gmt_modified   timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '消息队列消息记录';

create index index_gmt_created_consume_status
    on mk_mq_msg_record (gmt_created, consume_status);

create index index_msg_id
    on mk_mq_msg_record (msg_id);





create table mk_sys_config
(
    id           bigint auto_increment comment '参数配置ID'
        primary key,
    config_name  varchar(50)                         not null comment '参数名',
    config_key   varchar(50)                         not null comment '参数key',
    config_value longtext                            not null comment '参数value',
    is_lock      tinyint   default 0                 null comment '是否锁定',
    remark       varchar(255)                        null comment '备注',
    gmt_created  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by    int       default 0                 not null comment '创建人id',
    update_by    int       default 0                 not null comment '更新人id',
    is_delete    tinyint   default 0                 not null comment '0：正常 1：删除',
    constraint config_key
        unique (config_key)
)
    comment '参数配置表';




create table mk_sys_file
(
    id             int auto_increment comment 'id'
        primary key,
    original_name  varchar(500)                           not null comment '原始名称',
    type           varchar(32)  default ''                not null comment '文件类型',
    md5            varchar(32)                            not null comment '文件MD5',
    file_ext       varchar(16)  default ''                not null comment '文件格式',
    url            varchar(512) default ''                not null comment '文件地址',
    file_size_byte bigint       default 0                 not null comment '文件大小(byte)',
    gmt_created    timestamp    default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified   timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by      int          default 0                 not null comment '创建人id',
    update_by      int          default 0                 not null comment '更新人id',
    is_delete      tinyint      default 0                 not null comment '0：正常 1：删除'
)
    comment '文件表';

create index index_gmt_created
    on mk_sys_file (gmt_created);

create index index_md5
    on mk_sys_file (md5)
    comment 'md5';

create index index_original_name
    on mk_sys_file (original_name);

create index index_type
    on mk_sys_file (type);




create table mk_sys_menu
(
    id           int auto_increment comment '菜单ID'
        primary key,
    name         varchar(32)  default ''                not null comment '菜单名称',
    permission   varchar(32)  default ''                not null comment '菜单权限标识',
    parent_id    int          default 0                 not null comment '父菜单ID',
    keep_alive   tinyint      default 0                 not null comment '0-开启，1- 关闭',
    type         tinyint      default 0                 not null comment '菜单类型 （-1主菜单 0页面 1组件 2接口）',
    `describe`   varchar(100) default ''                not null comment '描述',
    gmt_created  timestamp    default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by    int          default 0                 not null comment '创建人id',
    update_by    int          default 0                 not null comment '更新人id',
    is_delete    tinyint      default 0                 not null comment '0：正常 1：删除'
)
    comment '系统菜单表(菜单权限)';




create table mk_sys_role
(
    id           int auto_increment
        primary key,
    role_name    varchar(64)  default ''                not null comment '角色名称',
    role_code    varchar(64)  default ''                not null comment '唯一code',
    role_desc    varchar(255) default ''                not null comment '描述',
    gmt_created  timestamp    default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by    int          default 0                 not null comment '创建人id',
    update_by    int          default 0                 not null comment '更新人id',
    is_delete    tinyint      default 0                 not null comment '0：正常 1：删除',
    constraint index_role_code
        unique (role_code)
)
    comment '系统角色表';



create table mk_sys_role_menu
(
    role_id int not null comment '角色ID',
    menu_id int not null comment '菜单ID',
    primary key (menu_id, role_id)
)
    comment '角色菜单表';



create table mk_sys_user
(
    id           int auto_increment comment '主键ID'
        primary key,
    username     varchar(64)  default ''                not null comment '用户名',
    password     varchar(128) default ''                not null comment '密码',
    real_name    varchar(32)  default ''                not null comment '真实姓名',
    salt         varchar(128) default ''                not null comment '随机盐',
    phone        varchar(11)  default ''                not null comment '手机号',
    avatar       varchar(512) default ''                not null comment '头像',
    dept_id      int          default 0                 not null comment '部门ID',
    lock_flag    tinyint      default 0                 not null comment '0-正常，9-锁定',
    email        varchar(128) default ''                not null comment '邮箱',
    user_type    tinyint      default 0                 not null comment '用户类型(0平台用户 1平台管理员 2超级管理员)',
    gmt_created  timestamp    default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by    int          default 0                 not null comment '创建人id',
    update_by    int          default 0                 not null comment '更新人id',
    is_delete    tinyint      default 0                 not null comment '0：正常 1：删除'
)
    comment '用户表';

create index index_username
    on mk_sys_user (username);




create table mk_sys_user_role
(
    user_id int not null comment '用户ID',
    role_id int not null comment '角色ID',
    primary key (user_id, role_id)
)
    comment '用户角色表';




create table mk_user
(
    id            int auto_increment comment '主键,自增'
        primary key,
    telephone     varchar(11)  default ''                not null comment '用户手机号',
    password      varchar(64)  default ''                not null comment '用户密码',
    nickname      varchar(16)  default ''                not null comment '用户昵称',
    head_portrait varchar(128) default ''                not null comment '头像',
    gender        int          default 0                 not null comment '性别 1：男 2：女 0：未知',
    province_id   int          default 0                 not null comment '省',
    birthday      timestamp                              null comment '生日',
    gmt_created   timestamp    default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified  timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_delete     tinyint      default 0                 not null comment '0：正常 1：删除'
)
    comment '用户表';

create index index_gmt_created
    on mk_user (gmt_created);

create index index_nickname
    on mk_user (nickname);

create index index_telephone
    on mk_user (telephone, nickname);




create table mk_version
(
    id             int auto_increment comment '主键,自增'
        primary key,
    version_key    varchar(126) default ''                not null,
    remark         varchar(500) default ''                not null comment '版本备注',
    version_number int                                    not null comment '版本号',
    download_path  varchar(500) default ''                not null comment '下载路径',
    gmt_created    timestamp    default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modified   timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_by      int          default 0                 not null comment '创建人id',
    update_by      int          default 0                 not null comment '更新人id',
    is_delete      tinyint      default 0                 not null comment '0：正常 1：删除'
)
    comment '软件版本';




create table region
(
    id        int auto_increment comment '主键,自增'
        primary key,
    name      varchar(50) default '' not null comment '区域名称',
    parent_id int         default 0  not null comment '父节点编号',
    pinyin    varchar(50)            null comment '拼音'
)
    comment '城市地区表' row_format = DYNAMIC;

create index parent_id
    on region (parent_id);




create table region_details
(
    id        int auto_increment comment 'ID'
        primary key,
    pid       int          null comment '父id',
    shortname varchar(100) null comment '简称',
    name      varchar(100) null comment '名称',
    mergename varchar(255) null comment '全称',
    level     tinyint      null comment '层级 0 1 2 省市区县',
    pinyin    varchar(100) null comment '拼音',
    code      varchar(100) null comment '长途区号',
    zip       varchar(100) null comment '邮编',
    first     varchar(50)  null comment '首字母',
    lng       varchar(100) null comment '经度',
    lat       varchar(100) null comment '纬度'
)
    comment '地区表' charset = utf8mb3
                     row_format = DYNAMIC;

create index pid
    on region_details (pid);

create index zip
    on region_details (zip);

