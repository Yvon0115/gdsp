package com.gdsp.platform.workflow.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.workflow.model.LeaveVO;
import com.gdsp.platform.workflow.service.ILeaveTestService;
import com.gdsp.platform.workflow.service.IVariableService;

@Service
@Transactional(readOnly = true)
public class VariableServiceImpl implements IVariableService {

    @Autowired
    protected ILeaveTestService leaveService;

    @Override
    public Map<String, Object> getFormVariable(String formName, String formID, String... varaibleName) {
        Map<String, Object> map = new HashMap<String, Object>();
        LeaveVO leaveVO = leaveService.load(formID);
        if (leaveVO == null) {
            return map;
        }
        map.put("leaveDay", leaveVO.getLeaveDay());
        return map;
    }
}
