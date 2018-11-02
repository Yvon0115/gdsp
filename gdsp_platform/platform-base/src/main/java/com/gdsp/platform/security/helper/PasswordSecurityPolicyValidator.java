package com.gdsp.platform.security.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.platform.config.customization.model.PasswordConfVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 密码安全策略校验器
 * @author guoyang
 * @date 2016年12月1日
 */
@Component
public class PasswordSecurityPolicyValidator {

    @Autowired
    private ISystemConfExtService systemConfExtService;

    /**
     * 判断当前登录用户的密码是否需要重置
     * @param account
     * @author guoyang
     * @since 2017年4月5日
     */
    public boolean validatePasswordByAccount(UserVO user) {
        boolean flag = false;
        // 若用户为新增、密码被重置状态，需要修改密码
        if (user.isNeedReset()) {
            return true;
        }
        PasswordConfVO conf = systemConfExtService.queryPasswordConf();
        // 如果系统密码安全策略时效不为零，即启用时效，则判断当前用户时效是否已过期
        if (conf.getTimeLimit() != 0) {
            if (user.getLastUpdatePasswordTime() == null) {
                return true;
            }
            DDateTime limitDay = user.getLastUpdatePasswordTime().getDateAfter(conf.getTimeLimit());
            DDateTime today = new DDateTime();
            int result = limitDay.compareTo(today);
            if (result <= 0) {
                return true;
            }
        }
        return flag;
    };
}
