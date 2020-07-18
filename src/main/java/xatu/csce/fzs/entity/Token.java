package xatu.csce.fzs.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import xatu.csce.fzs.util.TokenUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Token 用户唯一登录凭证实体类
 *
 * @author mars
 */
@SuppressWarnings("unused")
public class Token {
    /**
     * 用户唯一登录凭证
     */
    private String tokenStr;
    /**
     * 用户 ID
     */
    private Integer userId;
    /**
     * 用户最近一次访问系统的时间
     */
    private Calendar lastTime;

    public Token(Integer userId) {
        this.tokenStr = TokenUtils.getInstance().makeToken();
        this.userId = userId;
        this.lastTime = Calendar.getInstance();
    }

    @JsonValue
    public String getTokenStr() {
        return tokenStr;
    }

    public Integer getUserId() {
        return userId;
    }

    public Calendar getCreateTime() {
        return lastTime;
    }

    /**
     * 更新用户最后一次登录的时间
     */
    public void updateLatsTime() {
        this.lastTime.setTime(new Date());
    }

    /**
     * 判断用户登录凭证是否过期
     *
     * @return true 过期；false 有效
     */
    public boolean isExpired() {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, -3);
        return lastTime.before(nowTime);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "Token [" +
                "token='" + tokenStr + '\'' +
                ", userId='" + userId + '\'' +
                ", lastTime='" + sdf.format(lastTime.getTime()) +
                "']";
    }
}

