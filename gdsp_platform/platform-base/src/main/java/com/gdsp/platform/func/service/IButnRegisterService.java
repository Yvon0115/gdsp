package com.gdsp.platform.func.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.model.ButnRegisterVO;

public interface IButnRegisterService {

    public Page<ButnRegisterVO> queryBtnRegister(Condition condition,
            Sorter sort, Pageable pageable);

    public ButnRegisterVO loadButnRegisterVOById(String id);

    public void updateButnRegister(ButnRegisterVO butnRegisterPages);

    public void insertButnRegister(ButnRegisterVO butnRegisterPages);

    public void deleteButnRegister(String... id);

    public List<ButnRegisterVO> queryButnByFunCode(
            String funcode);

    public List<ButnRegisterVO> queryButnByParentID(String id);
}
