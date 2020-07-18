package xatu.csce.fzs.util;

import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Token 帮助类
 * @author mars
 */
public class TokenUtils {
    private static volatile TokenUtils tokenUtilsInstance;

    private final static Logger LOGGER = Logger.getLogger(TokenUtils.class);
    /**
     * 私有化初始方法返,防止外部调用,破坏单利模式
     */
    private TokenUtils() {

    }

    /**
     * 得到唯一的 Token 生成器
     * @return tokenUtilsInstance 唯一的 token 生成器
     */
    public static TokenUtils getInstance() {
        if (tokenUtilsInstance == null) {
            synchronized (TokenUtils.class) {
                if (tokenUtilsInstance == null) {
                    tokenUtilsInstance = new TokenUtils();
                }
            }
        }

        return tokenUtilsInstance;
    }

    /**
     * 生成Token
     * @return 唯一的 Token 序列
     */
    public String makeToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
