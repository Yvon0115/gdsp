package com.gdsp.platform.security.helper;

public class PassProcessor implements UrlVerifyProcessor {

    @Override
    public boolean verifyUrl(MenuContext context, String url) {
        return true;
    }
}
