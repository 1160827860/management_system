package xatu.csce.fzs.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 反射帮助类
 * @author mars
 * @date 2018.11.05
 */
public class ClassReflection {

    /**
     * <p>根据所传入的操作获取对应的字段的名称</p>
     * <p>默认不添加 NULL</p>
     * @param object 对象
     * @param function 检验操作
     * @param <T> 对象数据类型
     * @return 字段名称集合
     */
    public static <T> List<String> getAllFieldNames(T object, Function<Field, String> function) {
        Class<?> objectClass = object.getClass();
        Field[] objectFields = objectClass.getDeclaredFields();

        List<String> fieldNames = new ArrayList<>(objectFields.length);
        for (Field field : objectFields) {
            if (function.apply(field) != null) {
                fieldNames.add(function.apply(field));
            }
        }
        return fieldNames;
    }
}
