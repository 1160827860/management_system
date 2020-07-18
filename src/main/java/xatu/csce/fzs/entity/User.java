package xatu.csce.fzs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import xatu.csce.fzs.mapper.annotation.ColumnName;
import xatu.csce.fzs.util.ClassReflection;
import xatu.csce.fzs.util.Md5Utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * 用户模型信息，对应数据库中的表
 *
 * @author mars
 */
@SuppressWarnings("unused")
@JsonRootName("user")
public final class User {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private Sex sex;
    private String phone;
    private String email;
    @ColumnName("bank_number")
    private String bankNumber;
    private Education education;

    private Integer major;

    private Status status;

    @ColumnName("gmt_create")
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date gmtCreate;

    @ColumnName("gmt_modified")
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Md5Utils.md5EncodeUtf8(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 得到用户模型信息中对应的数据库表的列名
     * @return 数据库表列名
     */
    public List<String> getSqlFieldsNames() {
        Function<Field, String> function = field -> {
            field.setAccessible(true);
            try {
                if (field.get(this) == null) {
                    return null;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (field.isAnnotationPresent(ColumnName.class)) {
                return field.getAnnotation(ColumnName.class).value();
            }
            return field.getName();
        };
        return ClassReflection.getAllFieldNames(this, function);
    }

    /**
     * 用户状态信息枚举类
     */
    public enum Status {
        /**
         * 表示当前状态在校
         */
        AT("AT", "在校"),
        /**
         * 表示当前状态离校
         */
        LEAVE("LEAVE", "离校");
        private String statusStr;
        private String statusCode;

        Status(String statusCode, String statusStr) {
            this.statusCode = statusCode;
            this.statusStr = statusStr;
        }

        @JsonValue
        public String getStatusStr() {
            return statusStr;
        }

        public String getStatusCode() {
            return statusCode;
        }


    }

    /**
     * 用户性别枚举类
     */
    public enum Sex {
        /**
         * 性别女
         */
        W("W", "女"),
        /**
         * 性别男
         */
        M("M", "男");
        private String sexCode;
        private String sexStr;

        Sex(String sexCode, String sexStr) {
            this.sexCode = sexCode;
            this.sexStr = sexStr;
        }

        @JsonValue
        public String getSexStr() {
            return sexStr;
        }

        public String getSexCode() {
            return sexCode;
        }
    }

    /**
     * 用户学历枚举类
     */
    public enum Education {
        /**
         * 本科
         */
        B("B", "本科"),
        /**
         * 研究生
         */
        M("M", "研究生"),
        /**
         * 博士
         */
        D("D", "博士"),
        /**
         * 博士后
         */
        PD("PD", "博士后");
        private String educationCode;
        private String educationStr;

        Education(String educationCode, String educationStr) {
            this.educationStr = educationStr;
            this.educationCode = educationCode;
        }

        @JsonValue
        public String getEducationStr() {
            return educationStr;
        }

        public String getEducationCode() {
            return educationCode;
        }
    }
}
