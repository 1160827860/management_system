# 常见问题及其解决办法汇总：

1. Mysql8 连接提示 Client does not support authentication protoco; requested by server; consider upgrading MySQL Client 解决办法

 **解决办法**：
  ```shell
  USE mysql;
  ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
  FLUSH PRIVILEGES;
  ```

2. 本地与远程分支数目不同步

 **解决办法**：
 ```bash
git remote update origin --prune
 ```
