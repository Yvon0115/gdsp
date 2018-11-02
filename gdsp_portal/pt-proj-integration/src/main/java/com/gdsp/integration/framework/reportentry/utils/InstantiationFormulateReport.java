package com.gdsp.integration.framework.reportentry.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.integration.framework.reportentry.service.IReportOprationService;

public class InstantiationFormulateReport {
	
	private static final Logger logger = LoggerFactory.getLogger(InstantiationFormulateReport.class);
    
	public IReportOprationService getReportClass(String type) {
        IReportOprationService reportOprationService = null;
        Class clazz = null;
        String className = AppConfig.getInstance().getString(type);
        try {
            clazz = Class.forName(className);
            if (clazz != null) {
                reportOprationService = (IReportOprationService) clazz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        } catch (InstantiationException e) {
        	logger.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
		}
        return reportOprationService;
    }
}
