package com.gdsp.platform.common.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;
import com.gdsp.dev.core.utils.tree.service.ITreeCodeService;
import com.gdsp.platform.common.dao.SimpleTreeCodeDao;

/**
 * 树形编码服务简单实现,目前支持单节点，集群时增加一位节点代码
 * @author yaboocn
 * @version 1.0 2011-9-20
 * @since 1.6
 */
@Service
public class SimpleTreeCodeService implements ITreeCodeService {

    /**
     * 数据访问对象
     */
    @Resource
    private SimpleTreeCodeDao dao      = null;
    /**
     * 服务器编号
     */
    private char              serverNo = AppConfig.getInstance().getString("server.node", "0").charAt(0);

    @Override
    public ITreeEntity generateTreeCode(ITreeEntity entity, String parentCode) {
        String tableName = entity.getTableName();
        String code = generateTreeCode(tableName, parentCode);
        entity.setInnercode(code);
        return entity;
    }

    @Override
    public String generateTreeCodeByParent(ITreeEntity parent) {
        return generateTreeCode(parent.getTableName(), parent.getInnercode());
    }

    @Override
    public String generateTreeCode(String tableName, String parentCode) {
        if (parentCode == null)
            parentCode = "";
        synchronized (tableName) {
            String maxCode = dao.getMaxCode(tableName, parentCode);
            if (maxCode != null) {
                maxCode = maxCode.substring(parentCode.length());
            }
            maxCode = increase(maxCode);
            return parentCode + maxCode;
        }
    }

    @Override
    public ITreeEntity[] generateTreeCodes(ITreeEntity[] entitys, String parentCode) {
        if (entitys == null || entitys.length == 0)
            return entitys;
        int length = entitys.length;
        ITreeEntity entity = entitys[0];
        String tableName = entity.getTableName();
        synchronized (this) {
            String maxCode = dao.getMaxCode(tableName, parentCode);
            if (maxCode != null) {
                maxCode = maxCode.substring(parentCode.length());
            }
            for (int i = 0; i < length; i++) {
                maxCode = increase(maxCode);
                entitys[i].setInnercode(parentCode + maxCode);
            }
        }
        return entitys;
    }

    @Override
    public String[] generateTreeCodesByParent(ITreeEntity parent, int length) {
        String parentCode = parent.getInnercode();
        if (parentCode == null)
            parentCode = "";
        String tableName = parent.getTableName();
        String[] codes = new String[length];
        synchronized (this) {
            String maxCode = dao.getMaxCode(tableName, parentCode);
            if (maxCode != null) {
                maxCode = maxCode.substring(parentCode.length());
            }
            for (int i = 0; i < length; i++) {
                maxCode = increase(maxCode);
                codes[i] = parentCode + maxCode;
            }
        }
        return codes;
    }

    @Override
    public void synchronizeSubTree(ITreeEntity entity, String oldTreeCode) {
        if (entity == null || StringUtils.isEmpty(entity.getInnercode()))
            return;
        String tableName = entity.getTableName();
        String parentCode = entity.getInnercode();
        dao.updateSubTreeCode(tableName, parentCode, oldTreeCode);
    }

    @Override
    public ITreeEntity afterMoveNodeToParent(ITreeEntity entity, String parentCode) {
        if (entity == null || StringUtils.isEmpty(entity.getInnercode()))
            return entity;
        String oldTreeCode = entity.getInnercode();
        generateTreeCode(entity, parentCode);
        synchronizeSubTree(entity, oldTreeCode);
        return entity;
    }

    /**
     * 递增最大编码
     * @param maxCode 原有最大编码
     * @return 递增后编码
     */
    public String increase(String maxCode) {
        long v = 0;
        if (maxCode != null) {
            maxCode = maxCode.substring(0, maxCode.length() - 1);
            v = Long.parseLong(maxCode, Character.MAX_RADIX);
            v++;
        }
        maxCode = Long.toString(v, Character.MAX_RADIX);
        int digit = TREECODELENGTH - maxCode.length() - 1;
        if (digit < 0)
            throw new DevRuntimeException("Tree Code digit overflow!");
        char[] chars = new char[TREECODELENGTH];
        for (int i = 0; i < digit; i++) {
            chars[i] = '0';
        }
        System.arraycopy(maxCode.toCharArray(), 0, chars, digit, maxCode.length());
        chars[TREECODELENGTH - 1] = serverNo;
        return new String(chars);
    }
}
