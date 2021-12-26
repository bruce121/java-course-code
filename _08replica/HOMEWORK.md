2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。 代码、sql 和配置文件，上传到 Github。 代码：当前模块
配置文件1：[config-sharding.yaml](sharding-sphere-proxy/config-sharding.yaml)
配置文件2：[server.yaml](sharding-sphere-proxy/server.yaml)

额外说明： order表设计时使用的uuid作为order_id，同时user_id也是uuid； 分库策略为：根据user_id最后一个字符对2取模的值作为数据库的索引；
分表策略为：根据order.id最后一个字符对16取模的值作为表名的索引； 自定义策略类在同项目的另一模块中（_08sharding_algorithm）；

```
## curl -X POST localhost:8080/order
[INFO ] 2021-12-26 19:09:24.919 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: SELECT @@session.transaction_read_only
[INFO ] 2021-12-26 19:09:24.920 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLSelectStatement(limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
[INFO ] 2021-12-26 19:09:24.920 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: SELECT @@session.transaction_read_only
[INFO ] 2021-12-26 19:09:24.923 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: INSERT INTO `t_order`  ( id,user_id,order_status,order_amount_total,created_time,last_modified_time ) VALUES( '05bf0bc7-ac08-4a34-a6e7-d18212526ae3','67d07a7a-7ec1-4a93-8610-b74eb63b9a65',1,100,1640516964908,1640516964908 )
[INFO ] 2021-12-26 19:09:24.923 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
[INFO ] 2021-12-26 19:09:24.923 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: INSERT INTO `order_3`  ( id,user_id,order_status,order_amount_total,created_time,last_modified_time ) VALUES('05bf0bc7-ac08-4a34-a6e7-d18212526ae3', '67d07a7a-7ec1-4a93-8610-b74eb63b9a65', 1, 100, 1640516964908, 1640516964908)


##  curl -X GET localhost:8080/order/05bf0bc7-ac08-4a34-a6e7-d18212526ae3 
[INFO ] 2021-12-26 19:10:13.548 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: SELECT id,user_id,order_status,order_amount_total,created_time,last_modified_time  FROM `t_order`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3'
[INFO ] 2021-12-26 19:10:13.548 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLSelectStatement(limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
[INFO ] 2021-12-26 19:10:13.548 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_0 ::: SELECT id,user_id,order_status,order_amount_total,created_time,last_modified_time  FROM `order_3`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3' ORDER BY id ASC
[INFO ] 2021-12-26 19:10:13.548 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: SELECT id,user_id,order_status,order_amount_total,created_time,last_modified_time  FROM `order_3`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3' ORDER BY id ASC


## curl -X PUT localhost:8080/order/05bf0bc7-ac08-4a34-a6e7-d18212526ae3
[INFO ] 2021-12-26 19:10:45.924 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: SELECT id,user_id,order_status,order_amount_total,created_time,last_modified_time  FROM `t_order`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3'
[INFO ] 2021-12-26 19:10:45.924 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLSelectStatement(limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
[INFO ] 2021-12-26 19:10:45.924 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_0 ::: SELECT id,user_id,order_status,order_amount_total,created_time,last_modified_time  FROM `order_3`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3' ORDER BY id ASC
[INFO ] 2021-12-26 19:10:45.924 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: SELECT id,user_id,order_status,order_amount_total,created_time,last_modified_time  FROM `order_3`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3' ORDER BY id ASC
[INFO ] 2021-12-26 19:10:45.930 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: SELECT @@session.transaction_read_only
[INFO ] 2021-12-26 19:10:45.930 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLSelectStatement(limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
[INFO ] 2021-12-26 19:10:45.930 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: SELECT @@session.transaction_read_only
[INFO ] 2021-12-26 19:10:45.932 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: UPDATE `t_order`  SET id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3',user_id = '67d07a7a-7ec1-4a93-8610-b74eb63b9a65',order_status = 1,order_amount_total = 100,created_time = 1640517045929,last_modified_time = 1640517045929 WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3' and user_id = '67d07a7a-7ec1-4a93-8610-b74eb63b9a65'
[INFO ] 2021-12-26 19:10:45.932 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLUpdateStatement(orderBy=Optional.empty, limit=Optional.empty)
[INFO ] 2021-12-26 19:10:45.932 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: UPDATE `order_3`  SET id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3',user_id = '67d07a7a-7ec1-4a93-8610-b74eb63b9a65',order_status = 1,order_amount_total = 100,created_time = 1640517045929,last_modified_time = 1640517045929 WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3' and user_id = '67d07a7a-7ec1-4a93-8610-b74eb63b9a65'


## curl -X DELETE localhost:8080/order/05bf0bc7-ac08-4a34-a6e7-d18212526ae3
[INFO ] 2021-12-26 19:11:16.656 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: SELECT @@session.transaction_read_only
[INFO ] 2021-12-26 19:11:16.656 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLSelectStatement(limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
[INFO ] 2021-12-26 19:11:16.656 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: SELECT @@session.transaction_read_only
[INFO ] 2021-12-26 19:11:16.659 [ShardingSphere-Command-10] ShardingSphere-SQL - Logic SQL: DELETE FROM `t_order`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3'
[INFO ] 2021-12-26 19:11:16.659 [ShardingSphere-Command-10] ShardingSphere-SQL - SQLStatement: MySQLDeleteStatement(orderBy=Optional.empty, limit=Optional.empty)
[INFO ] 2021-12-26 19:11:16.659 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_0 ::: DELETE FROM `order_3`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3'
[INFO ] 2021-12-26 19:11:16.659 [ShardingSphere-Command-10] ShardingSphere-SQL - Actual SQL: ds_1 ::: DELETE FROM `order_3`  WHERE  id = '05bf0bc7-ac08-4a34-a6e7-d18212526ae3'

```