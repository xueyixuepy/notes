# 一.单表查询
## 1.基础语句
- SELECT (DISTINCT) 列名,列名,... FROM 表名 WHERE 条件
>约定俗称,关键字用全大写

>条件中用到于判断的列可以不用在选择中,这是一个最基础的语句

>DISTINCT:表示选择独一无二的行,只要有一列不一样就好
## 2.条件
| 操作符 | 条件说明 | SQL 示例 |
| --- | --- | --- |
| `=`, `!=`, `<`, `<=`, `>`, `>=` | 标准数值比较运算符 | `col_name != 4` |
| `BETWEEN … AND …` | 数值处于两个值的范围内（包含两端边界） | `col_name BETWEEN 1.5 AND 10.5` |
| `NOT BETWEEN … AND …` | 数值不处于两个值的范围内（包含两端边界） | `col_name NOT BETWEEN 1 AND 10` |
| `IN (…)` | 数值存在于指定列表中 | `col_name IN (2, 4, 6)` |
| `NOT IN (…)` | 数值不存在于指定列表中 | `col_name NOT IN (1, 3, 5)` |
|LIKE '...'|字符串匹配||
|IS/NOT NULL|判断是否为空|col_name NOT NULL;col_name IS NULL |

字符串匹配:
| 操作符 | 功能说明 | SQL 示例 |
| --- | -- | --- |
| `LIKE` | 不区分大小写的精确字符串匹配 | `col_name LIKE "ABC"` |
| `NOT LIKE` | 不区分大小写的精确字符串不匹配 | `col_name NOT LIKE "ABCD"` |
| `%` | 通配符，可放在字符串任意位置，匹配零个或多个任意字符（仅能与 `LIKE` / `NOT LIKE` 搭配使用） | `col_name LIKE "%AT%"`（可匹配 `"AT"`、`"ATTIC"`、`"CAT"` 甚至 `"BATS"`） |
| `_` | 通配符，可放在字符串任意位置，匹配恰好一个任意字符（仅能与 `LIKE` / `NOT LIKE` 搭配使用） | `col_name LIKE "AN_"`（可匹配 `"AND"`，但无法匹配 `"AN"`） |

>LIKE可以用 列 NOT LIKE "..."
>但是 = 之类的不行,要 NOT 列 = ...
>LIKE也可以那样
## 3.求值顺序
- AND优先于OR
- NOT优先于AND
- ()最优先
## 4.排序
- ORDER BY 列名 (DESC),列名 (DESC),...
- 按列位置排序:
  ORDER BY 列位置1,列位置2,..
>一定要放在最后面

>可以理解为前面的语句得到了一个视图,最后对这个视图排序,所以按列位置排序的位置也是这个视图里的位置

> 默认升序,使用DESC是降序

>多个列的情况下是按第一个列升序(降序)然后再按第二个升序(降序)...  
## 5.取其中的几行
- 不同DBMS用的方法不同

| 数据库类型 | 语法关键字 | 功能说明 | SQL 示例 |
| --- | --- | --- | --- |
| SQL Server | `TOP 行数` | 仅返回前指定行数的数据 | `SELECT TOP 5 列名 FROM 表名;` |
| DB2 | `FETCH FIRST 行数 ROWS ONLY` | 仅返回前指定行数的数据 | `SELECT 列名 FROM 表名 FETCH FIRST 5 ROWS ONLY;` |
| Oracle | `ROWNUM <= 行数`（写在 WHERE 条件中） | 通过内置行号伪列 `ROWNUM` 限制返回行数 | `SELECT 列名 FROM 表名 WHERE ROWNUM <= 5;` |
| MySQL、MariaDB、PostgreSQL、SQLite | `LIMIT 行数 [OFFSET 起始行]` | `LIMIT` 指定返回的最大行数；`OFFSET` 指定起始偏移位置（行号从 0 开始）；MySQL / MariaDB / SQLite 还支持 `LIMIT 偏移, 行数` 的简写写法 | 基础用法：`SELECT 列名 FROM 表名 LIMIT 5;`分页用法：`SELECT 列名 FROM 表名 LIMIT 5 OFFSET 5;` |
>这些语句都是对最后结果进行截断
>除了TOP,用WHERE的那个,后面的语句应当放在ORDER BY后面
>用WHERE判断行数的,若要取排序后的前多少行,也得先排序,但是它又不能放在排序后,所以得先用个子查询
## 6.别名
- 列标题的别名
  SELECT 列名/列变换 AS 别名 FROM 表名
- 字段拼接
  SELECT 列名1 || "修饰用的符号"/列名2 FROM 表名
  >得到的结果是单列
  >例: Movies表里有Title,Country,...
  >SELECT Title + '(' + Country + ')' FROM Movies;
  >得到单列的表,比如一个值是 Iron Man (USA)
  >这个新列没有名字,应当取别名
  >SELECT Title + '(' + Country + ')' AS Movie FROM Movies;

  >函数RTRIM(),作用是除去值的右边的所有括号,在字段拼接中常用
 
  >除了||也可能是+,大概
## 7.列之间计算
- 使用+,-,*,/,%
- 尽量取别名
## 8.聚集函数
- 对整列计算,而不能作用到不同列之间
- AVG()
- COUNT(),忽略空值
- MAX/MIN()
- SUM()
>只返回一行
## 9.分组
==这里我的理解是,"SELECT 列名"其实是最后进行的.使用GROUP BY,会把前面得到的表按某个字段分成不同份,最后SELECT只会得到不同份数个行==

- SELECT 列名,聚集函数(列名)
  FROM 表名
  WHERE 分组前过滤条件
  GROUP BY 列名
  (HAVING 分组后过滤条件)
  (ORDER BY )
>要配合聚集函数使用
>最后只会返回分组数个行
## 10.子查询
- SELECT 列名
  FROM 表名
  WHERE 列名
  IN
  (SELECT 列名 FROM 表名
  WHERE 条件)
- SELECT 列名,
  (SELECT AVG(列名)
  FROM 表名
  WHERE 列名 = ? ) AS 别名
  FROM 表名
  >会对表中的每一行都会执行子查询,会增加一个新的列
  >子查询里的WHERE中要用到外面那个表的字段,此时那个字段值就是当前查到的行的值
  
# 二.多表查询
## 1.基础语句
- SELECT 列名1,列名2,... 
  FROM 表名1,表名2,...
  WHERE 条件 
>表名1,表名2...是笛卡尔积
>如果不同表有相同的列名,用表名.列名指定
>一般是取对应的部分,所以一般WHERE 表1.对应列 = 表2.对应列  
- SELECT 列名1,列名2,...
  FROM 表名1
  INNER/LEFT/RIGHT/FULL JOIN 表名2
  ON 条件
  (
  INNER JOIN 表名3
  ON 条件
  )
>这种写法更推荐,把联结显示给出,更加直观,不会混在WHERE里
>INNER:内联结,只保留笛卡尔积中满足条件的行
>LEFT:左外联结,以左表为基准保留全部，右表匹配上的返回对应数据，匹配不上的行，右表所有字段自动填充 `NULL`
>RIGHT:保留右表中匹配不上的行
>FULL:保留左右表中匹配不上的行
>注:保留的未匹配行不会重复
# 三.修改表格
## 1.行修改(内容修改)
### 插入行
- INSERT INTO
  表名
  (列1,列2,...)
  VALUES(值1,值2,...)
  >可以省略部分列,如果允许值为NULL的话
  >可以不指出列名,但那就得插入一整行了
- INSERT INTO
  表名(列1,列2,...)
  SELECT 列1,列2,...
  FROM 表名
  >插入检索出来的行
### 更新行
- UPDATE 表名
  SET 列名1 = ?,列名2 = ?,...
  WHERE 筛选条件
>对符合条件的所有行进行修改,更改其字段值
### 删除行
- DELETE FROM 表名
  WHERE 筛选条件
>对所有符合条件的行进行删除

>**可以发现没有使用TABLE关键字,而后面的操作都有TABLE**
## 2.创建与删除表
### (1)创建表
1. 拷贝一个表
   - 除去DB:
     CREATE TABLE 表名 AS SELECT * FROM 旧表
   - SQL SEVER:
     SELECT * INTO 表名 FROM 旧表
  >都会创建一个新表并拷贝旧表内容
2. 创建一个新表
   - CREATE TABLE 表名
     (
      列1 值类型 ((NOT) NULL) (DEFAULT 值),
      列2 值类型 ((NOT) NULL) (DEFAULT 值),
      ...
     )
  >默认是允许空值
  >
### (2)删除表
- DROP TABLE 表名;
### (3)重命名表
- 不同DBMS不同
## 3.列修改(结构修改)
### (1)添加列
- ALTER TABLE 表名
  ADD 列名 类型;
### (2)删除列
- ALTER TABLE 表名
  DROP COLUMN 列名;
### (3)约束修改
# 四.视图
## 1.创建视图
- CREATE VIEW 视图名 AS
  SELECT ....
# 五.UNION 组合查询
## 1.意义
允许执行多个SELECT指令并将结果作为一个查询返回
## 2.要求
- 必须由两个或以上SELECT语句组成
- 列数据类型必须兼容,至少DBMS可以隐式转换
- 列数必须一致
- 最终列名由第一条查询决定,结果集的列名、别名，全部以第一条 `SELECT` 为准，后面查询的列名会被忽略。
- 排序只能写在最后一条查询之后
## 3.语法
SELECT 列1, 列2 FROM 表A WHERE 条件1
UNION
SELECT 列1, 列2 FROM 表B WHERE 条件2;
>会自动去掉重复的行!
>可以对同一张表进行组合查询
# 六.存储过程
## 1.意义
- 相当于自定义函数
  预先编写好、编译后保存在数据库里的一段 SQL 程序。
## 2.示例(MySQL语法)
  - 创建
  ```sql
  -- 临时修改语句结束符（因为存储过程内部有分号，避免冲突）
  DELIMITER // 

  CREATE PROCEDURE GetCustOrderCount(IN custId CHAR(10))
  BEGIN
      -- 存储过程内部的SQL逻辑，可以写多条
      SELECT COUNT(*) AS order_count
      FROM Orders
      WHERE cust_id = custId;
  END //

  DELIMITER ; -- 改回默认的分号结束符
  ```
  - 调用
  ```sql
  CALL GetCustOrderCount('1000000001');
  ```
  >参数前面的IN:表示输入型参数,只可以传值进去用
  >还有OUT,INOUT
# 七.管理事务处理
## 1.意义
事务（Transaction）就是把多条 SQL 语句打包成一个不可分割的整体：要么所有语句全部执行成功，要么只要有一条出错，就全部撤销回退到执行前的状态，绝对不会出现 “一半成功、一半失败” 的中间状态。
## 2.事务的四大核心特性（ACID）
1. **原子性（Atomicity）**：事务是最小执行单元，不可拆分。要么全做，要么全不做，是事务最核心的特性。
2. **一致性（Consistency）**：事务执行前后，数据库的数据始终保持合法、完整的状态（比如库存不会扣成负数、订单金额和明细总和一致）。
3. **隔离性（Isolation）**：多个事务同时执行时，互相不干扰，不会因为并发操作导致数据错乱。
4. **持久性（Durability）**：事务一旦提交成功，数据就会永久写入数据库，哪怕系统崩溃也不会丢失。
## 3.事务的三个核心操作
对应 SQL 语法，管理事务就是控制这三个动作：
### (1)开启事务
手动标记一组 SQL 的开始，开启后后续的 SQL 不会立即生效，而是进入临时状态。
不同数据库语法略有差异：
- MySQL / MariaDB：`START TRANSACTION;` 或 `BEGIN;`
- SQL Server：`BEGIN TRANSACTION;`
- Oracle：默认关闭自动提交，直接写 SQL 即可，或 `SET TRANSACTION;`
### (2)提交事务 `COMMIT`
确认事务里的所有操作都正确，把数据永久写入数据库。提交后就无法再撤销了。
### (3)回滚事务 `ROLLBACK`
如果执行过程中出错，直接撤销事务内的所有修改，把数据恢复到事务开启前的状态。
## 4.保留点
如果事务很长、步骤很多，不希望出错时全部回滚，可以在事务内设置**保留点**，出错时只回滚到指定位置，不用从头再来。
- 例:
  ```sql
  START TRANSACTION;
  -- 第一步操作
  INSERT INTO Orders ...;
  SAVEPOINT step1; -- 设置保留点
  -- 第二步操作
  INSERT INTO OrderItems ...;
  -- 如果第二步出错，只回滚到step1，第一步的结果保留
  ROLLBACK TO step1;
  -- 最后确认没问题再整体提交
  COMMIT;
  ```
>只有 `INSERT`、`UPDATE`、`DELETE` 这类**数据修改语句（DML）**可以回滚。
>- `SELECT` 查询语句不存在 “回滚” 的说法，因为它不修改数据；
>- `CREATE TABLE`、`DROP TABLE`、`ALTER TABLE` 
>
>这类**表结构语句（DDL）**，绝大多数数据库执行后会自动提交，**无法回滚**，执行前一定要谨慎。

>使用场景应该是:
>1.我打算做些什么
>2.启动一个事务
>3.写一些语句并执行
>4.出错了,写ROLLBACK TO 保留点;语句回退
>5.干完了,提交
>所以其实是一步步手敲SQL语句的
# 八.约束
## 1.主键
- 要求
  任意两行主键不相同
  每行都有一个主键值,不能是NULL
  包含主键的列不修改或更新
  主键不能重用,如果表中删除某一行,它的主键值也不分配给新行
- 定义
  - 创建表时定义
    直接在(NOT) NULL 后面写PRIMARY KEY
    ```
    列名 类型 ((NOT) NULL) (PRIMARY KEY)
    ``` 
  - 给表的列添加主键约束
    ALTER TABLE 表名
    ADD CONSTRAINT PRIMARY KEY (列名);
    >这里的括号是要有的,列名也是要有的
    >SQLite不允许
## 2.外键
- 意义
  外键是其他表的主键
  有外键的列,其值必须出现在另一表的主键中
  作为外键的一方，不能随意删除，因为会导致指向外键的表的列不满足约束
- 定义
  - 建表时定义
    ```
    列名 类型 ((NOT) NULL) REFERENCE 另一表名(主键列名)
    ```
  - 添加约束
    ALTER TABLE 表名
    ADD CONSTRAINT
    FOREIGN KEY (本表的列名) REFFERENCE 另一表 (另一表主键列名)
## 3.唯一约束
- 意义
  可以有多个列是这样
  只要求每一行不同
  可以为NULL
- 定义
## 4.检查约束
- 意义
  字段值要满足检查
- 定义
  - 建表时定义
    列名 类型 ((NOT) NULL) CHECK 列名满足什么条件
  - 后续增加约束
    ADD CONSTRAINT CHECK (列名满足什么条件);
# 九.索引
## 1.概念
索引是数据库为表中一列 / 多列的值构建的「有序快速查找目录」，核心作用是用「空间换时间」，避免逐行扫描整张表，大幅提升数据查询和排序的速度。
## 2.类型

| 索引类型 | 说明 |
| --- | --- |
| 主键索引 | 主键自动创建，唯一且不允许 NULL，是查询速度最快的索引 |
| 普通索引 | 最基础的索引，仅用于加速查询，没有其他约束 |
| 唯一索引 | 要求列中所有值唯一，允许 NULL 值，比如用户邮箱、手机号列 |
| 联合索引（复合索引） | 多个列组合成一个索引，遵循「最左前缀原则」，适合多条件组合查询 |
| 全文索引 | 针对长文本字段做关键词搜索，比 `LIKE '%xxx%'` 效率高得多 |
## 3.作用
1. **加速 WHERE 条件过滤**
比如 `WHERE prod_id = 'BR01'`、`WHERE cust_id = 1000000001`，如果过滤列上有索引，数据库不用扫全表，直接定位到目标行。
> 补充：主键列会自动创建主键索引，所以按主键查询永远是最快的。

2. **加速 ORDER BY 排序**
如果排序列上有索引，数据本身已经是有序的，查询时不用再做临时排序，直接按索引顺序读取即可，避免了排序的性能开销。
3. **加速多表 JOIN 联结**
两张表联结的关联列（比如 `Orders.cust_id` 和 `Customers.cust_id`）如果建了索引，匹配对应行的速度会大幅提升；联结的表越多、数据量越大，索引的收益越明显。
4. **加速 GROUP BY 分组统计**
分组列有索引时，数据库可以直接按索引顺序分组，不用额外做分组计算。
## 4.创建与删除
### 1.创建
```
-- 给 Products 表的 prod_name 列建普通索引
CREATE INDEX prod_name_ind ON Products (prod_name);

-- 建唯一索引
CREATE UNIQUE INDEX 索引名 ON 表名(列名);

-- 建联合索引
CREATE INDEX 索引名 ON 表名(列1, 列2);

```
>索引名不能重复
### 2. 删除
```
DROP INDEX 索引名 ON 表名;
```
## 5.索引的使用
索引的选择和使用完全由数据库的查询优化器自动判断、自动完成
## 6.取名的意义
方便修改,查看,删除索引
调试的时候可以看到使用了哪些索引,方便优化
# 十.触发器
## 1.概念
**触发器（Trigger）是绑定在某张表上、自动触发执行的 SQL 程序**：当这张表发生 `INSERT`（插入）、`UPDATE`（更新）、`DELETE`（删除）操作时，数据库会自动执行触发器里预先写好的逻辑，完全不需要手动调用。
## 2.组成
1. **触发时机**：操作执行前（`BEFORE`）还是执行后（`AFTER`）
   - `BEFORE`：操作生效前触发，常用于数据校验、预处理、阻止非法操作
   - `AFTER`：操作生效后触发，常用于记录日志、级联更新其他表
2. **触发事件**：哪种操作会触发
   - `INSERT`：插入数据时触发
   - `UPDATE`：更新数据时触发
   - `DELETE`：删除数据时触发
3. **触发逻辑**：触发后要执行的 SQL 语句

> 补充：一张表可以建多个触发器，但同一张表、同时机、同事件的触发器一般只能有一个。
## 3.语法示例
```sql
DELIMITER //

CREATE TRIGGER trg_products_before_insert
BEFORE INSERT ON Products
FOR EACH ROW
BEGIN
    -- NEW 代表即将插入的新行数据
    SET NEW.prod_name = UPPER(NEW.prod_name);
END //

DELIMITER ;
```
>适用于MySQL/MariaDB
关键说明：
- `BEFORE INSERT ON Products`：在 Products 表执行插入操作**之前**触发
- `FOR EACH ROW`：每影响一行就触发一次（行级触发器）
- `NEW`：关键字，代表「新插入 / 更新后」的行数据，可以读取、修改
- 对应的 `DELETE` 操作用 `OLD` 关键字，代表「被删除 / 更新前」的行数据

