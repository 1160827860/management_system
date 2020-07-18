package xatu.csce.fzs.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import xatu.csce.fzs.entity.User;
import xatu.csce.fzs.entity.UserLanguage;

import java.util.List;
import java.util.Map;

/**
 * 数据库持久层
 *
 * @author mars
 */
@Mapper
public interface UserMapper {
    /**
     * 持久层示例代码<p/>
     * 如何将参数注入SQL之中，请注意 #{} 与 ${} 之间的区别
     *
     * @param account 用户帐号
     * @return 用户密码
     */
    @Select("SELECT id,exist,password as passwordSQL FROM user WHERE account = #{account} LIMIT 1")
    Map<String, Object> queryByAccount(@Param("account") String account);

    /**
     * 单独在注销里面使用
     * 核对密码是否错误
     * @param id 从user表里面提取的id
     * @return 用户的密码
     */
     @Select("SELECT password as passwordSQL FROM user WHERE id=#{id} LIMIT 1")
     String queryById(@Param("id") Integer id);

    /**
     * 单独在在注销里面使用
     * @param id  用户的id
     * @return 是否跟新成功
     */
    @Update("UPDATE user set exist=0 where id=#{id}")
    boolean updateExistById(@Param("id") Integer id);

    /**
     * 注册功能
     *
     * @param user 参数，根据此参数中的值生成动态SQL
     *
     */
    @InsertProvider(type = UserSqlProvider.class, method = "buildInsertUser")
    void insertUser(User user);

    /**
     * 注册功能
     *
     * @param userLanguage 参数，根据此参数生成动态SQL
     */
    @InsertProvider(type = UserSqlProvider.class,method = "buildInsertLanguage")
    void insertUserLanguage(UserLanguage userLanguage);

    /**
     * 查询功能
     *
     * @param id 用户的id
     * @return user信息
     */
      @SelectProvider(type = UserSqlProvider.class,method = "queryUserById")
      Map<String, Object> queryByToken(Integer id);

    /**
     * 查询功能
     *
     * 超级管理员和次级管理员可使用的查询
     * @param user 用户信息的包装类
     * @return user信息
     */
     @SelectProvider(type = UserSqlProvider.class,method = "queryByUser")
     Map<String, Object> queryByUser(User user);
    /**
     * 修改信息
     *
     * 修改个人表的信息
     * @param user user用户的包装类信息
     * @return true 修改成功 false 修改失败
     */
    @UpdateProvider(type = UserSqlProvider.class,method = "updateUser")
    boolean updateUserById(User user);



    /**
     * 动态SQL生成类
     */
    class UserSqlProvider {
        /**
         * 通过动态SQL生成用户注测时的SQL语句
         * 用户注册
         * @param user 用户信息包装类
         * @return 用户注册SQL语句
         */
        public static String buildInsertUser(User user) {
            List<String> fieldsNames = user.getSqlFieldsNames();
            return new SQL() {{
                INSERT_INTO("user");
                fieldsNames.forEach((v) -> VALUES(v, String.format("#{%s}", v)));
            }}.toString();
        }


        /**
         * 语言表的动态sql
         *
         * @param userLanguage 用户语言表的信息包装类
         * @return  用户注册SQL语句
         */
        public static String buildInsertLanguage(UserLanguage userLanguage){
            List<String> fieldsNames = userLanguage.getSqlFieldsNames();
            return new SQL() {{
                INSERT_INTO("code_language");
                fieldsNames.forEach((v) -> VALUES(v, String.format("#{%s}", v)));
            }}.toString();
        }
        /**
         * 查询user表的信息(仅在当前登陆用户，查询自己信息的时候调用）
         *
         * @param id 用户的id
         * @return  用户注册SQL语句
         */
        public static String queryUserById(Integer id){
            return new SQL() {
                {
                    SELECT("account,name,sex,phone,email,bank_number,education,major,status,gmt_create,gmt_modified");
                    FROM("user");
                    WHERE("id=#{id}");
                }}.toString();
        }

        /**
         * 更新sql语句
         *
         * @param user 从客户端传输的user包装类的信息
         * @return 用户修改SQL语句
         */
        public static String updateUser(User user){
            return new SQL() {
                {

                    UPDATE("user");
                    if(user.getBankNumber()!=null) {
                        SET("bank_number=#{bankNumber}");
                    }
                    if(user.getEducation()!=null) {
                        SET("education=#{education}");
                    }
                    if(user.getName()!=null) {
                        SET("name=#{name}");
                    }
                    if(user.getSex()!=null) {
                        SET("sex=#{sex}");
                    }
                    if(user.getMajor()!=null) {
                        SET("major=#{major}");
                    }
                    if(user.getPhone()!=null) {
                        SET("phone=#{phone}");
                    }
                    if(user.getEmail()!=null) {
                        SET("email=#{email}");
                    }
                    WHERE("id=#{id}");

                }}.toString();
        }

        /**
         * 生成查询动态查询SQL
         *
         * @param user
         * @return 用户查询SQL语句
         */
        public static String queryByUser(User user){
            return new SQL() {
                {
                    SELECT("account,name,sex,phone,email,bank_number,education,major,status,gmt_create,gmt_modified");
                    FROM("user");
                    if (user.getId() != null) {
                        WHERE("id=#{id}");
                    }
                    if (user.getAccount() != null) {
                        WHERE("account=#{account}");
                    }
                    if (user.getName() != null) {
                        WHERE("name=#{name}");
                    }
                }

            }.toString();
        }
        }




}
