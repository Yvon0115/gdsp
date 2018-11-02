package com.gdsp.dev.core.data.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.utils.clazz.ClassUtils;
import com.gdsp.dev.base.utils.clazz.ReflectionUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;

/**
 * 自定义vo或map转换成json串的对象映射,可用在springmvc的responesebody中
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Service("json2ObjectMapper")
public class Json2ObjectMapper extends ObjectMapper {

    /**
     * 序列id
     */
    private static final long   serialVersionUID = -8698804858509087643L;
    /**
     * 日志变量
     */
    private static final Logger       logger           = LoggerFactory.getLogger(Json2ObjectMapper.class);
    /**
     * object2json类型系列化扩展在appconfig中的扩展
     */
    private static final String SERIALIZER_EXT   = "json.objectmapper.serializer";
    /**
     * json2object类型反系列化扩展在appconfig中的扩展
     */
    private static final String DESERIALIZER_EXT = "json.objectmapper.deserializer";

    /**
     * 取得对象映射服务
     * @return 对象映射服务
     */
    public static Json2ObjectMapper getInstance() {
        return AppContext.lookupBean("json2ObjectMapper", Json2ObjectMapper.class);
    }

    /**
     * 构造方法
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Json2ObjectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new JsonSerializer<Date>() {

            @Override
            public void serialize(Date value, JsonGenerator jsonGenerator,
                    SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                jsonGenerator.writeString(sdf.format(value));
            }
        });
        module.addSerializer(DDate.class, new JsonSerializer<DDate>() {

            @Override
            public void serialize(DDate value, JsonGenerator jsonGenerator,
                    SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                jsonGenerator.writeString(value.toString());
            }
        });
        module.addSerializer(DDateTime.class, new JsonSerializer<DDateTime>() {

            @Override
            public void serialize(DDateTime value, JsonGenerator jsonGenerator,
                    SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                jsonGenerator.writeString(value.toString());
            }
        });
        module.addSerializer(DBoolean.class, new JsonSerializer<DBoolean>() {

            @Override
            public void serialize(DBoolean value, JsonGenerator jsonGenerator,
                    SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                jsonGenerator.writeString(value.toString());
            }
        });
        module.addDeserializer(Date.class, new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonParser jp, DeserializationContext ctxt)
                    throws IOException {
                try {
                    return DDate.defaultFormat().parse(jp.getText());
                } catch (ParseException e) {
                    return null;
                }
            }
        });
        module.addDeserializer(DDate.class, new JsonDeserializer<DDate>() {

            @Override
            public DDate deserialize(JsonParser jp, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {
                return DDate.parseDDate(jp.getText());
            }
        });
        module.addDeserializer(DDateTime.class, new JsonDeserializer<DDateTime>() {

            @Override
            public DDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {
                return DDateTime.parseDDateTime(jp.getText());
            }
        });
        module.addDeserializer(DBoolean.class, new JsonDeserializer<DBoolean>() {

            @Override
            public DBoolean deserialize(JsonParser jp, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {
                return DBoolean.valueOf(jp.getText());
            }
        });
        String serializerExt = AppConfig.getInstance().getString(SERIALIZER_EXT);
        if (StringUtils.isNotBlank(serializerExt)) {
            String[] classNames = serializerExt.split(",");
            for (String className : classNames) {
                try {
					Class<? extends JsonSerializer> clazz = (Class<? extends JsonSerializer>) ClassUtils.loadClass(className);
                    JsonSerializer<?> serializer = clazz.newInstance();
					Class typeClass = ReflectionUtils.getSuperClassGenricType(clazz);
                    if (typeClass == null || typeClass == Object.class) {
                        typeClass = serializer.handledType();
                    }
                    if (typeClass == null || typeClass == Object.class) {
                        logger.error("Object2Json mapper Serializer data type is not exist" + className);
                        continue;
                    }
                    module.addSerializer(typeClass, serializer);
                } catch (ClassNotFoundException e) {
                    logger.error("Object2Json mapper Serializer class load error-" + className, e);
                } catch (InstantiationException e) {
                    logger.error("Object2Json mapper Serializer class can't instantiate-" + className, e);
                } catch (IllegalAccessException e) {
                    logger.error("Object2Json mapper Serializer class can't access constructor-" + className, e);
                }
            }
        }
        String deserializerExt = AppConfig.getInstance().getString(DESERIALIZER_EXT);
        if (StringUtils.isNotBlank(deserializerExt)) {
            String[] classNames = deserializerExt.split(",");
            for (String className : classNames) {
                try {
					Class<? extends JsonDeserializer> clazz = (Class<? extends JsonDeserializer>) ClassUtils.loadClass(className);
                    JsonDeserializer<?> deserializer = clazz.newInstance();
					Class typeClass = ReflectionUtils.getSuperClassGenricType(clazz);
                    if (typeClass == null || typeClass == Object.class) {
                        typeClass = deserializer.handledType();
                    }
                    if (typeClass == null || typeClass == Object.class) {
                        logger.error("Json2Object mapper Deserializer data type is not exist" + className);
                        continue;
                    }
                    module.addDeserializer(typeClass, deserializer);
                } catch (ClassNotFoundException e) {
                    logger.error("Json2Object mapper Deserializer class load error-" + className, e);
                } catch (InstantiationException e) {
                    logger.error("Json2Object mapper Deserializer class can't instantiate-" + className, e);
                } catch (IllegalAccessException e) {
                    logger.error("Json2Object mapper Deserializer class can't access constructor-" + className, e);
                }
            }
        }
        registerModule(module);
    }
}