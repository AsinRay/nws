package cn.bittx.nws.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     *  使用下边方法需要导入依赖包：
     * <dependency>
     *     <groupId>com.fasterxml.jackson.core</groupId>
     *     <artifactId>jackson-databind</artifactId>
     *     <version>2.12.3</version>
     * </dependency>
     *
     * @param object 集合（List）、Map（HashMap）、对象（new Date）
     * //@param format 时间格式化  yyyy-MM-dd hh:mm:ss
     * @return JSON格式化的字符串
     */
    public static String toJson(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Error writing json object: {}", e.getMessage());
        }
        return "";
    }

    public static <T> T fromString(String s, Class<T> cls) {
        try {
            return getMapper().readValue(s, cls);
        } catch (Exception e) {
            logger.error("Error parse string to json object: {}", e.getMessage());
        }
        return null;
    }

    public static <T> T fromString(String s, TypeReference<T> typeReference) {

        try {
            return getMapper().readValue(s, typeReference);
        } catch (Exception e) {
            logger.error("Error parse string to json object: {}", e.getMessage());
        }
        return null;
    }

    private static ObjectMapper mapper;

    public static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }
        return mapper;
    }

    public static void configureDateFormatString() {
        getMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        getMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static void configureTimeZone(TimeZone timeZone) {
        getMapper().setTimeZone(timeZone);
    }
}

