package com.gdsp.platform.config.global.utils;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.platform.config.global.model.ParamVO;

public class ParamValueUtils {

    public static Object getParamValue(int valtype, String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        switch (valtype) {
            case ParamVO.PARAM_INT:
                return Integer.valueOf(value);
            case ParamVO.PARAM_DOUBLE:
                return Double.valueOf(value);
            case ParamVO.PARAM_BOOLEAN:
                return DBoolean.valueOf(value);
            case ParamVO.PARAM_DATE:
                return new DDate(value);
            default:
                return value;
        }
    }

    public static String checkParamValue(int valtype, String value) {
        try {
            switch (valtype) {
                case ParamVO.PARAM_INT:
                    Integer intval = Integer.valueOf(value);
                    break;
                case ParamVO.PARAM_DOUBLE:
                    Double doubleval = Double.valueOf(value);
                    break;
                case ParamVO.PARAM_BOOLEAN:
                    if (!("Y".equalsIgnoreCase(value) || "TRUE".equalsIgnoreCase(value)
                            || "N".equalsIgnoreCase(value) || "False".equalsIgnoreCase(value)))
                        return "参数值请输入false、true、Y、N";
                    break;
                case ParamVO.PARAM_DATE:
                    DDate dateval = new DDate(value);
                    break;
                default:
                    return null;
            }
        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                if (valtype == ParamVO.PARAM_INT) {
                    return "参数值只能输入整数类型。";
                } else if (valtype == ParamVO.PARAM_DOUBLE) {
                    return "参数值只能输入Double类型。";
                }
            } else if (e instanceof DevRuntimeException) {
                return "参数值只能为yyyy-MM-dd格式。";
            }
        }
        return null;
    }
}
