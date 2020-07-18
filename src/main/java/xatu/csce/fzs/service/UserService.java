package xatu.csce.fzs.service;

import xatu.csce.fzs.entity.Token;
import xatu.csce.fzs.entity.User;
import xatu.csce.fzs.entity.UserLanguage;
import xatu.csce.fzs.util.ServerResponse;

/**
 * Service 层示例代码
 * @author mars
 */
public interface UserService {
    /**
     * 登录示例接口
     *
     * @param account 用户帐号
     * @param password 用户密码
     * @return API 统一调用返回接口
     */
    ServerResponse login(String account, String password);

    /**
     * 注册接口
     *
     * @param user 注册用户信息
     * @return API 统一调用返回接口
     */
    ServerResponse register(User user);

    /**
     * 登出接口
     *
     * @param tokenStr 用户端token的值
     * @return API 统一调用返回接口
     */
    ServerResponse logout(String tokenStr);

    /**
     * 注销接口
     *
     * @param tokenStr 用户端的token值
     * @param password 用户的密码
     * @return API 统一调用返回接口
     */
    ServerResponse logoff(String tokenStr, String password);

    /**
     * 查询接口
     *
     * @param  tokenStr 用户端的token值
     * @return API 统一调用返回接口
     */
    ServerResponse query(String tokenStr);

    /**
     * 修改接口
     *
     * @param user 需要修改的用户信息
     * @param tokenSrc  用户端的token值
     * @return API 统一调用返回接口
     */
     ServerResponse update(User user, String tokenSrc);
}
