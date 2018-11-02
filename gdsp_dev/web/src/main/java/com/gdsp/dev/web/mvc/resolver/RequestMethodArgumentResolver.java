package com.gdsp.dev.web.mvc.resolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;
import com.gdsp.dev.base.utils.data.TypeConvert;
import com.gdsp.dev.core.data.json.Json2ObjectMapper;
import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;

/**
 * 查询参数注入类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class RequestMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

    /**
     * 构造方法
     * @param beanFactory 工厂
     */
    public RequestMethodArgumentResolver(ConfigurableBeanFactory beanFactory) {
        super(beanFactory);
    }

    /**
     * 无参构造方法
     */
    public RequestMethodArgumentResolver() {
        super(null);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
     */
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> paramType = parameter.getParameterType();
        return paramType.equals(Condition.class) || paramType.equals(Pageable.class) || paramType.equals(Sorter.class) || paramType.isAssignableFrom(DDate.class);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver#createNamedValueInfo(org.springframework.core.MethodParameter)
     */
    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        RequestParam annotation = parameter.getParameterAnnotation(RequestParam.class);
        return (annotation != null) ? new RequestParamNamedValueInfo(annotation) : new RequestParamNamedValueInfo();
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver#resolveName(java.lang.String, org.springframework.core.MethodParameter, org.springframework.web.context.request.NativeWebRequest)
     */
    @Override
    protected Object resolveName(String name,
            MethodParameter parameter,
            NativeWebRequest request) throws JsonParseException, JsonMappingException, IOException  {
        Class<?> clazz = parameter.getParameterType();
        RequestParam annotation = parameter.getParameterAnnotation(RequestParam.class);
        String paramKey = null;
        if (annotation != null) {
            paramKey = annotation.value();
        }
        if (StringUtils.isEmpty(paramKey)) {
            paramKey = parameter.getParameterName();
        }
        if (clazz.equals(Condition.class)) {
            Condition condition = new Condition();
            String query = request.getParameter("__xquery");
            if (StringUtils.isNotEmpty(query)) {
                @SuppressWarnings("unchecked")
				List<Map<String, Object>> params = (List<Map<String, Object>>) Json2ObjectMapper.getInstance().readValue(query, List.class);
                for (int i = 0; i < params.size(); i++) {
                    Map<String, Object> obj = params.get(i);
                    String conName = (String) obj.get("name");
                    if (StringUtils.isEmpty(conName))
                        continue;
                    Object value = obj.get("value");
                    if (value != null && value instanceof String) {
                        value = ((String) value).replace("%", "\\%").replace("_", "\\_");
                    }
                    if (obj.containsKey("op")) {
                        String op = (String) obj.get("op");
                        condition.addExpression(conName, value, op);
                    } else {
                        condition.addExpression(conName, value);
                    }
                }
            }
            String freeCon = request.getParameter("_freeCon");
            String freeValue = request.getParameter("_freeConVal");
            if (StringUtils.isNotEmpty(freeCon) && StringUtils.isNotEmpty(freeValue)) {
                condition.addFreeCondition(freeCon.split(","));
                freeValue = freeValue.replace("%", "\\%").replace("_", "\\_");
                condition.setFreeValue(freeValue);
            }
            return condition;
        } else if (clazz.equals(Pageable.class)) {
            //收集分页信息
            String no = request.getParameter("_pageNo");
            String size = request.getParameter("_pageSize");
            int pageNo = 0, pageSize = 10;
            if (StringUtils.isNotEmpty(no) || StringUtils.isNotEmpty(size)) {
                pageSize = Integer.valueOf(size);
                pageNo = Integer.valueOf(no) - 1;
            }
            if (pageSize == 0)
                pageSize = 10;
            if (pageNo < 0)
                pageNo = 0;
            Sorter sort = getQuerySort(request);
            return new PageSerRequest(pageNo, pageSize, sort);
        } else if (clazz.equals(Sorter.class)) {
            return getQuerySort(request);
        } else if (clazz.equals(DDate.class)) {
            String v = request.getParameter(paramKey);
            if (StringUtils.isEmpty(v))
                return null;
            return TypeConvert.parseDDate(v);
        } else if (clazz.equals(DDateTime.class)) {
            String v = request.getParameter(paramKey);
            if (StringUtils.isEmpty(v))
                return null;
            return TypeConvert.parseDDateTime(v);
        } else if (clazz.equals(DTime.class)) {
            String v = request.getParameter(paramKey);
            if (StringUtils.isEmpty(v))
                return null;
            return TypeConvert.parseDTime(v);
        }
        return null;
    }

    /**
     * 初始化查询的排序信息
     */
    protected Sorter getQuerySort(NativeWebRequest request) {
        String sord = request.getParameter("sord");
        String sortName = request.getParameter("sidx");
        if (sortName != null && !sortName.equals("")) {
            Direction direction = "desc".equalsIgnoreCase(sord) ? Direction.DESC : Direction.ASC;
            Sorter.Order o = new Sorter.Order(direction, sortName);
            return new Sorter(o);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver#handleMissingValue(java.lang.String, org.springframework.core.MethodParameter)
     */
    @Override
    protected void handleMissingValue(String paramName, MethodParameter parameter) throws ServletException {
        throw new MissingServletRequestParameterException(paramName, parameter.getParameterType().getSimpleName());
    }

    /**
     * 参数名称信息
     * @author yangbo
     * @version 1.0 2013-12-16
     * @since 1.6
     */
    private class RequestParamNamedValueInfo extends NamedValueInfo {

        private RequestParamNamedValueInfo() {
            super("", false, ValueConstants.DEFAULT_NONE);
        }

        private RequestParamNamedValueInfo(RequestParam annotation) {
            super(annotation.value(), annotation.required(), annotation.defaultValue());
        }
    }
}