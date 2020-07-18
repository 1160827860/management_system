package xatu.csce.fzs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import xatu.csce.fzs.mapper.annotation.ColumnName;
import xatu.csce.fzs.util.ClassReflection;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author Darksouls
 * @date 2018/11/13
 */
public class UserLanguage {
    /**
     *用户user表id
     */
    private Integer userId;

    /**
     *code_language表id
     */
    private Integer languageId;

    @ColumnName("gmt_create")
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date gmtCreate;

    @ColumnName("gmt_modified")
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date gmtModified;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

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

}
