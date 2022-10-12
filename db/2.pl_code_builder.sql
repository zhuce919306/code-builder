

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pl_datasource
-- ----------------------------
DROP TABLE IF EXISTS `pl_datasource`;
CREATE TABLE `pl_datasource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `connection_type` int NULL DEFAULT 0 COMMENT '连接类型 0:主机连接,1:URl连接',
  `db_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'mysql' COMMENT '数据库类型',
  `host` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主机',
  `port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口',
  `instance` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实例名',
  `db_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库名称',
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '地址',
  `username` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_flag` int NULL DEFAULT 0 COMMENT '逻辑删除 0:正常，1:删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据源' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for pl_generate_config
-- ----------------------------
DROP TABLE IF EXISTS `pl_generate_config`;
CREATE TABLE `pl_generate_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置名称',
  `config_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_flag` int NULL DEFAULT 0 COMMENT '逻辑删除 0:正常，1:删除',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pl_generate_config
-- ----------------------------
INSERT INTO `pl_generate_config` VALUES (1, '默认配置-copy', '{\"author\":\"plserver\",\"columnConfig\":{\"commonColumns\":[\"create_time\",\"update_time\",\"create_user_id，update_user_id\",\"delete_flag\"],\"insertFillColumns\":[\"create_time\",\"create_user_id\"],\"insertOrUpdateFillColumns\":[\"update_time\",\"update_user_id\"],\"logicDeleteColumns\":[\"delete_flag\"]},\"controllerConfig\":{\"basicApi\":true,\"responseEntity\":{\"errorMethod\":\"fail\",\"packageName\":\"com.pl.core.response.Result\",\"successMethod\":\"ok\"}},\"entityConfig\":{\"extendsSuper\":false,\"lombokConfig\":{\"accessors\":{\"enable\":false,\"mode\":\"\",\"prefixModeValue\":\"\"},\"allArgsConstructor\":false,\"builder\":false,\"data\":true,\"enable\":true,\"enableAccessors\":false,\"equalsAndHashCode\":false,\"getter\":false,\"noAllArgsConstructor\":false,\"setter\":false,\"toStr\":false},\"superClassPackage\":\"\"},\"mapperConfig\":{\"buildXml\":true,\"idType\":\"assign_id\",\"mapperAnnotation\":true},\"packageName\":\"com.pl\",\"style\":\"pl-web\",\"swagger\":true,\"tablePrefix\":\"pl_\",\"typeMapperConfig\":[{\"columnType\":\"decimal\",\"importPackage\":true,\"javaType\":\"BigDecimal\",\"packageName\":\"java.math.BigDecimal\"},{\"columnType\":\"float\",\"importPackage\":false,\"javaType\":\"Float\",\"packageName\":\"java.lang.Float\"},{\"columnType\":\"integer\",\"importPackage\":false,\"javaType\":\"Integer\",\"packageName\":\"java.lang.Integer\"},{\"columnType\":\"timestamp\",\"importPackage\":true,\"javaType\":\"LocalDateTime\",\"packageName\":\"java.time.LocalDateTime\"},{\"columnType\":\"bigint\",\"importPackage\":false,\"javaType\":\"Long\",\"packageName\":\"java.lang.Long\"},{\"columnType\":\"datetime\",\"importPackage\":true,\"javaType\":\"LocalDateTime\",\"packageName\":\"java.time.LocalDateTime\"},{\"columnType\":\"mediumint\",\"importPackage\":false,\"javaType\":\"Integer\",\"packageName\":\"java.lang.Integer\"},{\"columnType\":\"tinyint\",\"importPackage\":false,\"javaType\":\"Integer\",\"packageName\":\"java.lang.Integer\"},{\"columnType\":\"varchar\",\"importPackage\":false,\"javaType\":\"String\",\"packageName\":\"java.lang.String\"},{\"columnType\":\"mediumtext\",\"importPackage\":false,\"javaType\":\"String\",\"packageName\":\"java.lang.String\"},{\"columnType\":\"date\",\"importPackage\":true,\"javaType\":\"LocalDate\",\"packageName\":\"java.time.LocalDate\"},{\"columnType\":\"smallint\",\"importPackage\":false,\"javaType\":\"Integer\",\"packageName\":\"java.lang.Integer\"},{\"columnType\":\"int\",\"importPackage\":false,\"javaType\":\"Integer\",\"packageName\":\"java.lang.Integer\"},{\"columnType\":\"bit\",\"importPackage\":false,\"javaType\":\"Boolean\",\"packageName\":\"java.lang.Boolean\"},{\"columnType\":\"tinytext\",\"importPackage\":false,\"javaType\":\"String\",\"packageName\":\"java.lang.String\"},{\"columnType\":\"double\",\"importPackage\":false,\"javaType\":\"Double\",\"packageName\":\"java.lang.Double\"},{\"columnType\":\"text\",\"importPackage\":false,\"javaType\":\"String\",\"packageName\":\"java.lang.String\"},{\"columnType\":\"char\",\"importPackage\":false,\"javaType\":\"String\",\"packageName\":\"java.lang.String\"},{\"columnType\":\"longtext\",\"importPackage\":false,\"javaType\":\"String\",\"packageName\":\"java.lang.String\"}],\"wired\":true,\"wiredType\":\"autoWired\"}', '2021-06-18 15:28:21', NULL, '2021-06-18 15:28:21', 0, NULL, NULL);

