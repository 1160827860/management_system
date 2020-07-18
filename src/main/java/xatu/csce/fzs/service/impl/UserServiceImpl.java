package xatu.csce.fzs.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xatu.csce.fzs.commonservice.TokenPool;
import xatu.csce.fzs.entity.Token;
import xatu.csce.fzs.entity.User;
import xatu.csce.fzs.entity.UserLanguage;
import xatu.csce.fzs.mapper.UserMapper;
import xatu.csce.fzs.service.UserService;
import xatu.csce.fzs.util.Md5Utils;
import xatu.csce.fzs.util.ServerResponse;
import xatu.csce.fzs.util.TokenUtils;

import javax.swing.plaf.synth.Region;
import java.sql.Date;
import java.util.Map;
//TODO：权限和编程语言还没写完，基本功能写完（token池，动态SQL）
/**
 * Controller 代码示例
 * @author mars
 */
@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final TokenPool tokenPool;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public UserServiceImpl(UserMapper userMapper, TokenPool tokenPool) {
        this.userMapper = userMapper;
        this.tokenPool = tokenPool;
    }

    @Override
    public ServerResponse login(String account, String password) {

        // 调用 mapper 查询用户密码（MD5加密之后）
        Map<String, Object> resultSQL =  userMapper.queryByAccount(account);
        String exist="exist";
        if (resultSQL == null) {
            return ServerResponse.createByErrorMessage("不存在该用户");

        }
        //[start]判断该用户是否被注销
        else if(Boolean.valueOf(resultSQL.get(exist).toString())!=true){
            return ServerResponse.createByErrorMessage("该用户已经被注销！");
        }
        //[end]
        else {
            String fileNamePasswordSQL = "passwordSQL";
            if (StringUtils.equals(Md5Utils.md5EncodeUtf8(password), resultSQL.get(fileNamePasswordSQL).toString())) {
                String id = "id";
                Token token = new Token(Integer.valueOf(resultSQL.get(id).toString()));
                LOGGER.debug(token);
                tokenPool.addToken(token);
                return ServerResponse.createBySuccess("登录成功", token);
            } else {
                return ServerResponse.createByErrorMessage("用户名或密码错误");
            }
        }
    }

    @Override
    public ServerResponse register(User user) {
        //[start]检查账号是否被注册
        Map<String, Object> resultSQL = userMapper.queryByAccount(user.getAccount());
        if (resultSQL !=null) {
            return ServerResponse.createByErrorMessage("注册失败，该帐号已存在");
         //[end]
        }else {

            //[start]加密密码
            user.setPassword(user.getPassword());
            //[end]

            //[start]插入User表
            userMapper.insertUser(user);
            //[end]
            Map<String, Object> result = userMapper.queryByUser(user);
            return ServerResponse.createBySuccessMessage("注册成功");
        }
    }

    @Override
    public ServerResponse logout(String tokenStr) {
       //[start]查询tokenPool里面是否有此token
        Token token= tokenPool.getToken(tokenStr);
       if(token==null){
           return ServerResponse.createByErrorMessage("不存在该token或者token已经过期");
           //[end]

       }else {
               return ServerResponse.createBySuccessMessage("成功登出");
       }
    }

    @Override
    public ServerResponse logoff(String tokenStr, String password) {

        //[start]检查tokenPool里面是否有此token
        Token token= tokenPool.getToken(tokenStr);
        if(token!=null){
        //[end]

            //[start]查询数据库里面的密码
            Integer userID= Integer.valueOf(token.getUserId());
            String passwordSQL=  userMapper.queryById(userID);
            //[end]

            //[start]比较密码是否正确
            if(StringUtils.equals(Md5Utils.md5EncodeUtf8(password),passwordSQL)){
                //[end]

                //[start]改变数据库里面的exist的值
                if(userMapper.updateExistById(userID)){
                    return ServerResponse.createBySuccessMessage("注销成功");
                }else {
                    return ServerResponse.createByErrorMessage("注销失败请稍后重试");
                }
                //[end]
            }else {
                return ServerResponse.createByErrorMessage("密码错误");
            }

        }else {
            return ServerResponse.createByErrorMessage("不存在该token");
        }

    }


    @Override
    public ServerResponse query(String tokenStr) {
        //TODO：权限表未作，所以未改用动态SQL查询
        //[start]检查tokenPool里面是否有此token
        Token token= tokenPool.getToken(tokenStr);
        if(token!=null){
            //[end]

            //[start]通过tokenPool池里面的id查询所有信息
            return ServerResponse.createBySuccess("查询成功",userMapper.queryByToken(Integer.valueOf(token.getUserId())));
            //[end]


        }else {
            return ServerResponse.createByErrorMessage("不存在该token或登录凭证无效，超出最大等待时间，已经强制下线");
        }
    }

    @Override
    public ServerResponse update(User user, String tokenSrc) {
        //[start]检查tokenPool里面是否有此token
        Token token= tokenPool.getToken(tokenSrc);
        if(token!=null){
            //[end]
            //[start]将查询id，最后修改时间放入User的包装类，调用动态SQL updateUserById 修改数据库的文件
            user.setId(Integer.valueOf(token.getUserId()));
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            user.setGmtModified(sqlDate);
            if(userMapper.updateUserById(user)){
                return ServerResponse.createBySuccess("修改成功");
            }else {
                return ServerResponse.createByErrorMessage("修改失败，请稍后重试");
            }
            //[end]
        }else {
            return ServerResponse.createByErrorMessage("不存在该token或登录凭证无效，超出最大等待时间，已经强制下线");
        }

    }


}
