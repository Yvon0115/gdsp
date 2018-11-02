package com.gdsp.platform.workflow.service;

import java.util.Map;

public interface IVariableService {

    public Map<String, Object> getFormVariable(String formName, String formID, String... varaibleName);
}
