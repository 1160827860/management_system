package xatu.csce.fzs.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * API 接口调用返回封装类
 *
 * @author mars
 * @param <T> 返回数据类型
 */
@SuppressWarnings("ALL")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ServerResponse<T> {
    /**
     * API 调用状态码，并使当前调用的状态
     */
    private ResponseCode code;

    /**
     * API 调用信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private ServerResponse(ResponseCode code) {
        this.code = code;
    }

    private ServerResponse(ResponseCode code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(ResponseCode code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    private ServerResponse(ResponseCode code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * API 调用成功，状态码默认为 0， 调用信息默认为 调用成功，不返回数据
     *
     * @param <T> 数据类型
     * @return API 调用信息
     */
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS);
    }

    /**
     * 调用成功，写入调用信息，状态码默认为 0，不返回数据
     * @param msg 调用成功信息
     * @param <T> 数据类型
     * @return API 调用信息
     */
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS, msg);
    }

    /**
     * 调用成功，写入返回的数据，状态码默认为 0 ，调用信息默认为 调用成功
     * @param data 调用成功后返回的数据
     * @param <T> 数据类型
     * @return API 调用信息
     */
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS, data);
    }

    /**
     * 调用成功，写入信息和返回数据，状态码默认为 0 ，调用成功
     * @param msg 调用成功信息
     * @param data 调用成功后返回的数据
     * @param <T> 数据类型
     * @return API 调用信息
     */
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS, msg, data);
    }

    /**
     * 调用失败，写入具体失败原因，失败状态码默认为 1 ，表示访问失败
     * @param errorMessage 错误具体信息
     * @param <T> 数据类型
     * @return API 调用信息
     */
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(ResponseCode.ERROR, errorMessage);
    }

    /**
     * 调用失败，写入失败具体状态码、具体失败原因
     * @param errorCode 错误类型代码
     * @param errorMessage 错误具体信息
     * @param <T> 数据类型
     * @return API 调用信息
     */
    public static <T> ServerResponse<T> createByErrorCodeMessage(ResponseCode errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode, errorMessage);
    }

    /**
     * 状态码
     */
    public enum ResponseCode {
        /**
         * 通用接口调用成功状态码
         */
        SUCCESS(0, "访问成功"),
        /**
         * 通用接口调用失败状态码
         */
        ERROR(1, "访问失败"),
        /**
         * 未登录状态下调用接口状态码
         */
        NEED_LOGIN(10, "需要登录"),
        /**
         * 非法调用接口状态码
         */
        ILLEGAL_ARGUMENT(2, "权限不足");

        private final int code;
        private final String desc;

        ResponseCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @JsonValue
        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String  toString() {
            return this.desc;
        }
    }
}
