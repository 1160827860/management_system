package xatu.csce.fzs.util;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author mars
 */
public class Md5Utils {
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private final static Logger LOGGER = Logger.getLogger(Md5Utils.class);

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();

        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * 返回大写MD5
     *
     * @param origin 未加密的字符串
     * @return 加密后的字符串
     */
    private static String md5encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteArrayToHexString(md.digest(resultString.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return resultString != null ? resultString.toUpperCase() : null;
    }

    /**
     * 加密字符串
     * @param origin 未加密的字符串
     * @return 加密后的字符串
     */
    public static String md5EncodeUtf8(String origin) {
        origin = origin + "FZS.MANAGEMENT";

        LOGGER.debug(origin);

        return md5encode(origin);
    }
}