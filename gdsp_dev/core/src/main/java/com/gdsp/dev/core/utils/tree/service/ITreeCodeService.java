package com.gdsp.dev.core.utils.tree.service;

import com.gdsp.dev.core.utils.tree.model.ITreeEntity;

/**
 * 树代码生成引擎
 * @author yaboocn
 * @version 1.0 2011-9-20
 * @since 1.6
 */
public interface ITreeCodeService {

    /**
     * 树编码每级码长
     */
    public final static int TREECODELENGTH = 4;

    /**
     * 为指定实体生成树编码
     * @param entity 树形实体
     * @param parentCode 上级编码
     * @return 树形实体
     */
    ITreeEntity generateTreeCode(ITreeEntity entity, String parentCode);

    /**
     * 根据上级实体生成下级编码，如果上级实体为空则传入空实体
     * @param parent 上级实体
     * @return 树形编码
     */
    public String generateTreeCodeByParent(ITreeEntity parent);

    /**
     * 根据表名上级节点id上级节点编码生成新的下级编码
     * @param tableName 表名
     * @param parentCode 上级节点编码
     * @return 新的编码
     */
    public String generateTreeCode(String tableName, String parentCode);

    /**
     * 为一组相同上级的实体生成树编码
     * @param entitys 树形实体数组
     * @param parentCode 櫖上级编码
     * @return 树形实体数组
     */
    public ITreeEntity[] generateTreeCodes(ITreeEntity[] entitys, String parentCode);

    /**
     * 根据上级实体生成多个下级编码，如果上级实体为空则传入空实体
     * @param parent 上级实体
     * @param length 编码个数
     * @return 编码数组
     */
    public String[] generateTreeCodesByParent(ITreeEntity parent, int length);

    /**
     * 根据实体树编码同步其所有子节点编码
     * @param entity 需要同步子节点编码的节点
     * @param oldTreeCode 原有的实体树编码
     */
    public void synchronizeSubTree(ITreeEntity entity, String oldTreeCode);

    /**
     * 树形实体更改上级后的处理
     * @param entity 树形实体
     * @param parentCode 新的上级编码
     */
    public ITreeEntity afterMoveNodeToParent(ITreeEntity entity, String parentCode);
}
