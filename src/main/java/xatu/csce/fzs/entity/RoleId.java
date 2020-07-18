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
 * @date 2018/11/12
 */
public class RoleId {
    /**
     *用户user表id
     */
    private Integer userId;

    /**
     *角色表authority表id
     */
    private Integer roleId;

    /**
     *项目id
     */
    private Integer projectId;

    @ColumnName("gmt_create")
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date gmtCreate;

    @ColumnName("gmt_modified")
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date gmtModified;

    public Integer getUserId() {
        return userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }


}
