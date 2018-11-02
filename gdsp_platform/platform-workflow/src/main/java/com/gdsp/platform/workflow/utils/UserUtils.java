package com.gdsp.platform.workflow.utils;

import com.gdsp.dev.core.common.AppContext;

public class UserUtils {

    public static String getCurrentUserID() {
        return AppContext.getContext().getContextUserId();
    }
}
