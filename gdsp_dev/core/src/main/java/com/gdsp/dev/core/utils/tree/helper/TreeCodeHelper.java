package com.gdsp.dev.core.utils.tree.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;
import com.gdsp.dev.core.utils.tree.service.ITreeCodeService;

/**
 * 树编码助手
 * @author yaboocn
 * @version 1.0 2011-9-19
 * @since 1.6
 */
public final class TreeCodeHelper {

    /**
     * 编码引擎
     */
    private static ITreeCodeService service = null;

    /**
     * 取得编码服务
     * @return 编码服务
     */
    public static ITreeCodeService getService() {
        if (service == null) {
            service = AppContext.getContext().lookup(ITreeCodeService.class);
        }
        //兼容dubbo框架
        if (service == null) {
            service = AppContext.getContext().lookup("service", ITreeCodeService.class);
        }
        return service;
    }

    /**
     * 为指定实体生成树编码
     * @param entity 树形实体
     * @param parentCode 上级编码
     * @return 树形实体
     */
    public static ITreeEntity generateTreeCode(ITreeEntity entity, String parentCode) {
        return getService().generateTreeCode(entity, parentCode);
    }

    /**
     * 根据上级实体生成下级编码，如果上级实体为空则传入空实体
     * @param parent 上级实体
     * @return 树形编码
     */
    public static String generateTreeCodeByParent(ITreeEntity parent) {
        return getService().generateTreeCodeByParent(parent);
    }

    /**
     * 根据表名上级节点id上级节点编码生成新的下级编码
     * @param tableName 表名
     * @param parentCode 上级节点编码
     * @return 新的编码
     */
    public static String generateTreeCode(String tableName, String parentCode) {
        return getService().generateTreeCode(tableName, parentCode);
    }

    /**
     * 为一组相同上级的实体生成树编码
     * @param entitys 树形实体数组
     * @param parentCode 上级编码
     * @return 树形实体数组
     */
    public static ITreeEntity[] generateTreeCodes(ITreeEntity[] entitys, String parentCode) {
        return getService().generateTreeCodes(entitys, parentCode);
    }

    /**
     * 根据上级实体生成多个下级编码，如果上级实体为空则传入空实体
     * @param parent 上级实体
     * @param length 编码个数
     * @return 编码数组
     */
    public static String[] generateTreeCodesByParent(ITreeEntity parent, int length) {
        return getService().generateTreeCodesByParent(parent, length);
    }

    /**
     * 根据实体树编码同步其所有子节点编码
     * @param entity 需要同步子节点编码的节点
     * @param oldTreeCode 原有的实体树编码
     */
    public static void synchronizeSubTree(ITreeEntity entity, String oldTreeCode) {
        getService().synchronizeSubTree(entity, oldTreeCode);
    }

    /**
     * 树形实体更改上级后的处理
     * @param entity 树形实体
     * @param parentCode 上级编码
     */
    public static ITreeEntity afterMoveNodeToParent(ITreeEntity entity, String parentCode) {
        return getService().afterMoveNodeToParent(entity, parentCode);
    }

    /**
     * 取得当前编码的所有上级节点编码数组
     * @param treeCode 树编码
     * @return 上级节点编码数组
     */
    public static String[] getSuppriorTreeCodes(String treeCode) {
        if (StringUtils.isBlank(treeCode) || treeCode.length() == ITreeCodeService.TREECODELENGTH)
            return null;
        if (treeCode.length() % ITreeCodeService.TREECODELENGTH != 0)
            throw new IllegalArgumentException();
        List<String> parentCodes = new ArrayList<String>();
        for (int i = ITreeCodeService.TREECODELENGTH; i < treeCode.length(); i += ITreeCodeService.TREECODELENGTH) {
            String code = treeCode.substring(0, i);
            parentCodes.add(code);
        }
        return parentCodes.toArray(new String[parentCodes.size()]);
    }
}