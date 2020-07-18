package xatu.csce.fzs.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xatu.csce.fzs.entity.Token;
import xatu.csce.fzs.entity.User;
import xatu.csce.fzs.entity.UserLanguage;
import xatu.csce.fzs.service.UserService;
import xatu.csce.fzs.util.ServerResponse;

/**
 * @author mars
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     *
     * @param account  帐号
     * @param password 用户密码
     * @return API 接口调用返回封装类
     */
    @GetMapping("login")
    public ServerResponse login(String account, String password) {
        if (StringUtils.isAnyBlank(account, password)) {
            return ServerResponse.createByErrorMessage("参数为空");
        }
        return userService.login(account, password);
    }

    @PutMapping("register")
    public ServerResponse register(User user) {
        return userService.register(user);
    }

    @GetMapping("logout")
    public  ServerResponse logout(String tokenSrc){
        return userService.logout(tokenSrc);
    }

    @GetMapping("logoff")
    public ServerResponse logoff(String tokenSrc,String password){
        return userService.logoff(tokenSrc,password);
    }

    @GetMapping("query")
    public ServerResponse query(String tokenSrc){return userService.query(tokenSrc); }

    @PutMapping("update")
    public ServerResponse update(User user,String tokenSrc){
        return userService.update(user,tokenSrc);
    }
}
