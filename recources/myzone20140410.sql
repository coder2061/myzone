/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : myzone

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-04-10 23:08:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accesslog
-- ----------------------------
DROP TABLE IF EXISTS `accesslog`;
CREATE TABLE `accesslog` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ClickEntry` varchar(100) DEFAULT NULL COMMENT '点击入口',
  `MemberID` int(11) DEFAULT NULL,
  `Url` varchar(255) DEFAULT NULL,
  `Params` varchar(255) DEFAULT NULL,
  `Ip` varchar(20) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accesslog
-- ----------------------------

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Role` tinyint(4) DEFAULT NULL COMMENT '角色',
  `Power` varchar(255) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '附件表',
  `MailID` int(11) DEFAULT NULL COMMENT '邮件内容ID',
  `Name` varchar(255) DEFAULT NULL,
  `Path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `Type` tinyint(4) DEFAULT NULL COMMENT '文件类型',
  `OrderNum` tinyint(4) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for basic
-- ----------------------------
DROP TABLE IF EXISTS `basic`;
CREATE TABLE `basic` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间（最后）',
  `OrderNum` tinyint(4) DEFAULT '0' COMMENT '序号',
  `IsDelete` tinyint(1) DEFAULT NULL,
  `Status` tinyint(4) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of basic
-- ----------------------------

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '配置信息ID',
  `Key` varchar(45) DEFAULT NULL COMMENT '关键词',
  `Val` varchar(500) DEFAULT NULL COMMENT '值',
  `Description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置信息';

-- ----------------------------
-- Records of config
-- ----------------------------

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '联系人表',
  `MemberID` int(11) DEFAULT NULL,
  `ContactID` int(11) DEFAULT NULL,
  `Email` varchar(200) DEFAULT NULL,
  `Mobile` varchar(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `IsFirst` tinyint(1) DEFAULT '0' COMMENT 'MemberID是否为第一次发送请求',
  `IsDelete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `Status` tinyint(4) DEFAULT NULL COMMENT '0,等待',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`) USING BTREE,
  KEY `memberID` (`MemberID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人表';

-- ----------------------------
-- Records of contact
-- ----------------------------

-- ----------------------------
-- Table structure for contactgroup
-- ----------------------------
DROP TABLE IF EXISTS `contactgroup`;
CREATE TABLE `contactgroup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '联系人分组',
  `Name` varchar(64) DEFAULT NULL COMMENT '小组名',
  `Desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `OrderNum` int(11) DEFAULT '0' COMMENT '序号',
  `IsDelete` tinyint(1) DEFAULT NULL,
  `Status` tinyint(4) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人分组';

-- ----------------------------
-- Records of contactgroup
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户反馈表',
  `MemberID` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Content` varchar(1000) DEFAULT NULL,
  `AdminID` int(11) DEFAULT NULL,
  `IsReply` tinyint(1) DEFAULT '0' COMMENT '是否回复',
  `ReplyContent` varchar(1000) DEFAULT NULL,
  `ReplyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户反馈表';

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志表',
  `LogTypeID` int(11) DEFAULT NULL,
  `AdminID` int(11) DEFAULT NULL,
  `MemberID` int(11) DEFAULT NULL,
  `MessageID` int(11) DEFAULT NULL,
  `OperIp` varchar(20) DEFAULT NULL,
  `OperTime` datetime DEFAULT NULL,
  `OperDesc` varchar(255) DEFAULT NULL,
  `RequestContext` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for logtype
-- ----------------------------
DROP TABLE IF EXISTS `logtype`;
CREATE TABLE `logtype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志类型',
  `TypeName` varchar(50) DEFAULT NULL,
  `TypeDesc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志类型';

-- ----------------------------
-- Records of logtype
-- ----------------------------

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '邮件内容表',
  `Subject` varchar(50) DEFAULT NULL COMMENT '主题',
  `Content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `Icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `Url` varchar(255) DEFAULT NULL COMMENT '链接',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `IsDelete` tinyint(4) DEFAULT '0' COMMENT '发送者是否删除(0未删除  1标记删除  2彻底删除)',
  `DeleteTime` datetime DEFAULT NULL COMMENT '发送者删除时间',
  `IsDraft` tinyint(1) DEFAULT '0' COMMENT '是否草稿',
  `IsTag` tinyint(1) DEFAULT '0' COMMENT '是否被标记',
  `OrderNum` int(11) DEFAULT '0' COMMENT '序号',
  `Status` tinyint(4) DEFAULT '0' COMMENT '0:未读1：已读2：回复3：转发4：全部转发 ',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件内容表';

-- ----------------------------
-- Records of mail
-- ----------------------------

-- ----------------------------
-- Table structure for mailinbox
-- ----------------------------
DROP TABLE IF EXISTS `mailinbox`;
CREATE TABLE `mailinbox` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收件箱',
  `ReceiverID` int(11) DEFAULT NULL COMMENT '接收者ID,0 表示为所有人',
  `CreateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `ReceiveType ` tinyint(4) DEFAULT NULL COMMENT '接收类型；0：普收 1：抄收 2：密收',
  `IsRead` tinyint(1) DEFAULT '0' COMMENT '接收者是否已阅',
  `ReadTime` datetime DEFAULT NULL COMMENT '阅读时间',
  `IsDelete` tinyint(4) DEFAULT '0' COMMENT '接收者是否删除(0未删除  1标记删除  2彻底删除)',
  `DeleteTime` datetime DEFAULT NULL COMMENT '接收者删除时间',
  `IsTag` tinyint(1) DEFAULT '0' COMMENT '是否被标记',
  `IsReply` tinyint(1) DEFAULT '0' COMMENT '是否回复',
  `ReplyID` int(11) DEFAULT NULL COMMENT '回复邮件ID',
  `OrderNum` int(11) DEFAULT '0' COMMENT '序号',
  `Status` tinyint(4) DEFAULT '0' COMMENT '0:未读1：已读2：回复3：转发4：全部转发 ',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收件箱';

-- ----------------------------
-- Records of mailinbox
-- ----------------------------

-- ----------------------------
-- Table structure for mailoutbox
-- ----------------------------
DROP TABLE IF EXISTS `mailoutbox`;
CREATE TABLE `mailoutbox` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '发件箱',
  `SenderID` int(11) DEFAULT NULL COMMENT '发送者ID，0表示系统',
  `ReceiverID` int(11) DEFAULT NULL COMMENT '接收者ID,0 表示为所有人',
  `CopyerID` varchar(500) DEFAULT NULL COMMENT '抄送ID,0 表示为所有人',
  `SecreterID` varchar(500) DEFAULT NULL COMMENT '密送ID,0 表示为所有人',
  `MailID` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `IsSend` tinyint(1) DEFAULT '0' COMMENT '是否发送',
  `SendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `SendType ` tinyint(4) DEFAULT NULL COMMENT '发送类型；0：普通1：急件',
  `IsDelete` tinyint(4) DEFAULT '0' COMMENT '发送者是否删除(0未删除  1标记删除  2彻底删除)',
  `DeleteTime` datetime DEFAULT NULL COMMENT '发送者删除时间',
  `IsDraft` tinyint(1) DEFAULT '0' COMMENT '是否草稿',
  `IsTag` tinyint(1) DEFAULT '0' COMMENT '是否被标记',
  `OrderNum` int(11) DEFAULT '0' COMMENT '序号',
  `Status` tinyint(4) DEFAULT '0' COMMENT '0:未读1：已读2：回复3：转发4：全部转发 ',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发件箱';

-- ----------------------------
-- Records of mailoutbox
-- ----------------------------

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `Name` varchar(60) DEFAULT NULL COMMENT '用户名',
  `NickName` varchar(60) DEFAULT NULL COMMENT '用户名',
  `Gender` tinyint(1) DEFAULT '0' COMMENT '性别（1男，0女）',
  `Birthday` datetime DEFAULT NULL COMMENT '生日',
  `HeadPic` varchar(300) DEFAULT NULL COMMENT '头像',
  `SpacePic` varchar(100) DEFAULT NULL COMMENT '空间头图',
  `Passwd` varchar(64) DEFAULT NULL COMMENT '密码',
  `Mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `Email` varchar(200) DEFAULT NULL COMMENT '电子邮件',
  `QQ` varchar(18) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL COMMENT '注册时间',
  `Address` varchar(100) DEFAULT NULL COMMENT '地址',
  `PostCode` varchar(6) DEFAULT NULL COMMENT '邮编',
  `Level` tinyint(4) DEFAULT '0' COMMENT '等级',
  `LastLoginTime` datetime DEFAULT NULL COMMENT '最后登录时间',
  `IsLock` tinyint(1) DEFAULT '0' COMMENT '用户是否锁定，锁定后就不能登录,1是0否',
  `IsLogin` tinyint(1) DEFAULT '0' COMMENT '是否登录，1 login, 0 logout',
  `LockTime` datetime DEFAULT NULL COMMENT '账户锁住时间',
  `EncryptedID` varchar(100) DEFAULT NULL COMMENT '加密的用户ID',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `mobile` (`Mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', 'jade', null, '0', null, null, null, '123456', '18779885539', 'Jade@jxau.com', null, null, null, null, '0', null, '0', '0', null, null);
INSERT INTO `member` VALUES ('2', 'jade1', null, '0', null, null, null, '123', null, null, null, null, null, null, '0', null, '0', '0', null, null);
INSERT INTO `member` VALUES ('3', 'jade2', null, '0', null, null, null, '123', null, null, null, null, null, null, '0', null, '0', '0', null, null);
INSERT INTO `member` VALUES ('4', 'jyf', null, '0', null, null, null, '123', null, null, null, null, null, null, '0', null, '0', '0', null, null);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户消息表',
  `SenderID` int(11) DEFAULT NULL COMMENT '发送者ID，0表示系统',
  `ReceiverID` int(11) DEFAULT NULL COMMENT '接收者ID,0 表示为所有人',
  `CopyerID` varchar(500) DEFAULT NULL COMMENT '抄送ID,0 表示为所有人',
  `SecreterID` varchar(500) DEFAULT NULL COMMENT '密送ID,0 表示为所有人',
  `Title` varchar(50) DEFAULT NULL COMMENT '主题',
  `Content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `Icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `Url` varchar(255) DEFAULT NULL COMMENT '链接',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `IsSend` tinyint(1) DEFAULT '0' COMMENT '是否发送',
  `SendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `SendType ` tinyint(4) DEFAULT NULL COMMENT '发送类型；0：普通1：急件',
  `IsRead` tinyint(1) DEFAULT '0' COMMENT '接收者是否已阅',
  `ReadTime` datetime DEFAULT NULL COMMENT '阅读时间',
  `IsReceiverDelete` tinyint(4) DEFAULT '0' COMMENT '接收者是否删除(0未删除  1标记删除  2彻底删除)',
  `ReceiverDeleteTime` datetime DEFAULT NULL COMMENT '接收者删除时间',
  `IsSenderDelete` tinyint(4) DEFAULT '0' COMMENT '发送者是否删除(0未删除  1标记删除  2彻底删除)',
  `SenderDeleteTime` datetime DEFAULT NULL COMMENT '发送者删除时间',
  `IsDraft` tinyint(1) DEFAULT '0' COMMENT '是否草稿',
  `IsTag` tinyint(1) DEFAULT '0' COMMENT '是否被标记',
  `IsReply` tinyint(1) DEFAULT '0' COMMENT '是否回复',
  `ReplyID` int(11) DEFAULT NULL COMMENT '回复邮件ID',
  `OrderNum` int(11) DEFAULT '0' COMMENT '序号',
  `Status` tinyint(4) DEFAULT '0' COMMENT '0:未读1：已读2：回复3：转发4：全部转发 ',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户消息表';

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for right
-- ----------------------------
DROP TABLE IF EXISTS `right`;
CREATE TABLE `right` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单导航',
  `RightCode` char(4) DEFAULT NULL,
  `RightAction` varchar(50) DEFAULT NULL COMMENT '定位到Action',
  `RightController` varchar(50) DEFAULT NULL COMMENT '定位到Conroller',
  `RightName` varchar(45) DEFAULT NULL,
  `RightType` int(11) DEFAULT NULL COMMENT '权限类型0=一级菜单（没入口）   1=有入口的（一级或二级菜单） 2=按钮',
  `ParentID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单导航';

-- ----------------------------
-- Records of right
-- ----------------------------

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '邮件模板表',
  `TemplateTypeID` int(11) DEFAULT NULL COMMENT '短信类型ID',
  `Title` varchar(255) DEFAULT NULL COMMENT '短信标题',
  `Content` varchar(500) DEFAULT NULL,
  `SideNote` varchar(255) DEFAULT NULL COMMENT '备注',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `OrderNum` int(11) DEFAULT NULL,
  `IsDelete` tinyint(1) DEFAULT NULL,
  `Status` tinyint(4) DEFAULT NULL COMMENT '0=待受理 （1、2=审核中）1=编审审核通过 2=技术支持提交到第三方 3=审核未通过   4=审核通过',
  `Description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件模板表';

-- ----------------------------
-- Records of template
-- ----------------------------

-- ----------------------------
-- Table structure for templatetype
-- ----------------------------
DROP TABLE IF EXISTS `templatetype`;
CREATE TABLE `templatetype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板类型表',
  `TypeName` varchar(100) DEFAULT NULL,
  `IsDelete` tinyint(1) DEFAULT '0' COMMENT '是否使用',
  `Status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板类型表';

-- ----------------------------
-- Records of templatetype
-- ----------------------------
