CREATE TABLE sc_user (
  telephone VARCHAR(200) COMMENT '电话号码',
  user_name VARCHAR(200)           NOT NULL
  COMMENT '用户名',
  email     VARCHAR(200) COMMENT '邮箱',
  age       INT COMMENT '年龄',
  avatar    VARCHAR(36) COMMENT '头像',
  real_name VARCHAR(200) COMMENT '真实姓名',
  id        BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  account   VARCHAR(200)           NOT NULL
  COMMENT '账号',
  password  VARCHAR(200)           NOT NULL
  COMMENT '密码',
  create_on DATETIME               NOT NULL
  COMMENT '创建时间',
  status    VARCHAR(100)           NOT NULL
  COMMENT '状态',
  birthday  DATE COMMENT '生日'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户登录表';

CREATE TABLE sc_user_school_data (
  id                   BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  school_name          VARCHAR(200)           NOT NULL
  COMMENT '学校姓名',
  school_province      VARCHAR(200)           NOT NULL
  COMMENT '学校省份',
  school_city          VARCHAR(200)           NOT NULL
  COMMENT '学校城市',
  school_card_account  VARCHAR(200) COMMENT '学生证号码',
  school_card_password VARCHAR(200) COMMENT '学生证密码',
  user_id              BIGINT(20)             NOT NULL
  COMMENT '用户id',
  create_on            DATETIME               NOT NULL
  COMMENT '创建时间',
  FOREIGN KEY `user_link_user_school_by_user_id` (user_id) REFERENCES sc_user (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户学校信息表';

CREATE TABLE sc_post (
  id VARCHAR(36) not NULL PRIMARY KEY ,
  title          VARCHAR(200)            NOT NULL
  COMMENT '帖子标题',
  cotent         MEDIUMTEXT              NOT NULL
  COMMENT '帖子正文',
  author_id      BIGINT(20)              NOT NULL
  COMMENT '作者编号',
  thumbnail      VARCHAR(200) COMMENT '缩略图',
  post_type      VARCHAR(30)             NOT NULL
  COMMENT '帖子类型',
  like_count     BIGINT(11)              NOT NULL DEFAULT 0
  COMMENT '点赞次数',
  reply_count    BIGINT(11)              NOT NULL DEFAULT 0
  COMMENT '回复次数',
  view_count     BIGINT(11)              NOT NULL DEFAULT 0
  COMMENT '浏览次数',
  interest_count BIGINT(11)              NOT NULL DEFAULT 0
  COMMENT '被关注次数',
  create_on      DATETIME                NOT NULL
  COMMENT '创建时间',
  FOREIGN KEY `user_link_post_by_user_id` (author_id) REFERENCES sc_user (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '帖子信息表';

CREATE TABLE sc_reply (
  id             VARCHAR (36) PRIMARY KEY  NOT NULL,
  cotent         MEDIUMTEXT              NOT NULL
  COMMENT '回复正文',
  author_id      BIGINT(20)              NOT NULL
  COMMENT '作者编号',
  post_type      TINYINT(3)              NOT NULL
  COMMENT '回复类型 0帖子的回复， 1回复的回复',
  like_count     BIGINT(11)              NOT NULL DEFAULT 0
  COMMENT '点赞次数',
  reply_count    BIGINT(11)              NOT NULL DEFAULT 0
  COMMENT '回复次数',
  interest_count BIGINT(11)              NOT NULL DEFAULT 0
  COMMENT '被收藏次数',
  parent_post_id BIGINT(20)              NOT NULL
  COMMENT '父帖子或父回复的id',
  reply_to       BIGINT(20)              NOT NULL
  COMMENT '被回复者的id',
  root_post_id   BIGINT(20)              NOT NULL
  COMMENT '根帖子的id',
  create_on      DATETIME                NOT NULL
  COMMENT '创建时间',
  FOREIGN KEY `user_link_reply_by_user_id` (author_id) REFERENCES sc_user (id),
  FOREIGN KEY `user_link_reply_by_reply_to` (reply_to) REFERENCES sc_post (id),
  FOREIGN KEY `post_link_reply_by_post_id` (root_post_id) REFERENCES sc_post (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '回复信息表';

CREATE TABLE sc_course_table (
  week           INT(4)                 NOT NULL
  COMMENT '第几周',
  weekday        INT(4)                 NOT NULL
  COMMENT '星期几',
  lesson         INT(4)                 NOT NULL
  COMMENT '第几节课开始',
  class_duration INT(4)                 NOT NULL
  COMMENT '课程节数',
  name           VARCHAR(200)           NOT NULL
  COMMENT '课程名字',
  teacher        VARCHAR(200)           NOT NULL
  COMMENT '老师名字',
  classroom      VARCHAR(200)           NOT NULL
  COMMENT '教室地址',
  user_id        BIGINT(20)             NOT NULL
  COMMENT '该课程所属用户id',
  id             BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT
  COMMENT '课程id',
  INDEX `course_table_by_user_id` (user_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户课程表';

CREATE TABLE sc_user_message (
  id           BIGINT(20)    NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '用户消息编号',
  from_user_id BIGINT(20)    NOT NULL
  COMMENT '消息发送方',
  to_user_id   BIGINT(20)    NOT NULL
  COMMENT '消息接受方',
  message_body VARCHAR(1000) NOT NULL
  COMMENT '消息体',
  send_time    DATETIME      NOT NULL
  COMMENT '发送时间',
  accept_time  DATETIME      NOT NULL
  COMMENT '接收(浏览)时间',
  INDEX `user_message_by_from_user_id` (from_user_id, to_user_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户消息表';

CREATE TABLE sc_user_unread_message (
  id            BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '用户未读消息统计编号',
  user_id       BIGINT(20) NOT NULL
  COMMENT '用户id',
  from_id       BIGINT(20) NOT NULL
  COMMENT '消息来源id',
  message_count BIGINT(11) NOT NULL             DEFAULT 0
  COMMENT '未读消息数量',
  INDEX `sc_user_unread_message_by_from_user_id` (user_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户未读消息统计表';

CREATE TABLE sc_user_friend (
  id          BIGINT(20)   NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '用户好友记录id',
  user_id     BIGINT(20)   NOT NULL
  COMMENT '用户id',
  friend_id   BIGINT(20)   NOT NULL
  COMMENT '好友id',
  status      VARCHAR(20)  NOT NULL
  COMMENT '状态 好友，黑名单，已删除',
  add_time    DATETIME     NOT NULL
  COMMENT '添加时间',
  update_time DATETIME     NOT NULL
  COMMENT '状态更新时间',
  remark      VARCHAR(200) NOT NULL
  COMMENT '好友备注',
  group_id    BIGINT(20)   NOT NULL
  COMMENT '好友分组',
  FOREIGN KEY `user_friend_link_user_by_friend_id` (friend_id) REFERENCES sc_user (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户好友表';

CREATE TABLE sc_user_friend_group (
  id           BIGINT(20)   NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '用户分组id',
  user_id      BIGINT(20)   NOT NULL
  COMMENT '用户id',
  group_name   VARCHAR(200) NOT NULL
  COMMENT '分组名字',
  group_number BIGINT(11)   NOT NULL             DEFAULT 0
  COMMENT '分组内好友数量',
  FOREIGN KEY `friend_group_friend_link_user_friend_by_friend_id` (user_id) REFERENCES sc_user_friend (friend_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户好友分组表';

CREATE TABLE sc_verify_code(
  code_id VARCHAR(36) PRIMARY KEY COMMENT '验证码id',
  code_string VARCHAR(20) not NULL COMMENT '验证码内容',
  code_create_on DATETIME NOT NULL COMMENT '验证码产生时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '验证码表';


CREATE TABLE sc_multipart_file(
  multipart_id VARCHAR (36) PRIMARY KEY  COMMENT '富文本附件id',
  multipart_type TINYINT(4) NOT NULL DEFAULT 0 COMMENT '富文本类型0 图片 1 视频 2 语音',
  multipart_from TINYINT(4) NOT NULL DEFAULT 0 COMMENT '来源0 帖子 1 对话',
  create_on DATETIME NOT NULL COMMENT '创建时间',
  relation_id VARCHAR (36) NOT NULL COMMENT '来源id'
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '富文本表';

CREATE TABLE sc_login (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '表的id',
  user_id BIGINT not NULL COMMENT '登入用户id',
  token VARCHAR(36) NOT NULL COMMENT '服务器保存的token',
  update_time DATETIME NOT NULL COMMENT '保存这个token的时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户登入token表';