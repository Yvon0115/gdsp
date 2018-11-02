package com.gdsp.dev.web.mvc.bind;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;

/**
 * SpringMvc日期类型转换
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class CustomWebBindingInitializer implements WebBindingInitializer {

    /* (non-Javadoc)
     * @see org.springframework.web.bind.support.WebBindingInitializer#initBinder(org.springframework.web.bind.WebDataBinder, org.springframework.web.context.request.WebRequest)
     */
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(DDate.class, new DDateEditor(DDate.class));
        binder.registerCustomEditor(DDateTime.class, new DDateEditor(DDateTime.class));
        binder.registerCustomEditor(DTime.class, new DDateEditor(DTime.class));
        binder.registerCustomEditor(DBoolean.class, new DBooleanEditor());
    }
}