package com.gdsp.dev.core.data.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.core.data.query.trans.ChainLogicExpressionTranslator;
import com.gdsp.dev.core.data.query.trans.LogicExpressionTranslator;
import com.gdsp.dev.core.data.query.trans.ValueExpressionTranslator;
import com.gdsp.dev.core.model.query.IExpression;

public class ExpressionTranslatorFactory {
    /** 日志对象不能依赖common包 */
    private static final Logger logger = LoggerFactory.getLogger(ExpressionTranslatorFactory.class);
    /**
     * 简单表达式转换器
     */
    private static ValueExpressionTranslator      valueExpressionTranslator      = null;
    /**
     * 逻辑表达式转换器
     */
    private static LogicExpressionTranslator      logicExpressionTranslator      = null;
    /**
     * 链式逻辑表达式转换器
     */
    private static ChainLogicExpressionTranslator chainLogicExpressionTranslator = null;
    
    private ExpressionTranslatorFactory() {
        
    }
    public static IExpressionTranslator getExpressionTranslatorServise(String type)
    {
        if(type==null)
        {
            logger.debug(type);
            throw new IllegalArgumentException("非法的表达式!");
        }
        switch (type) {
            case IExpression.TYPE_SIMPLE:
                if(valueExpressionTranslator==null)
                {
                    synchronized (ValueExpressionTranslator.class) {
                        if(valueExpressionTranslator==null)
                        {
                           valueExpressionTranslator = new ValueExpressionTranslator();
                        }
                    }
                }
                return  valueExpressionTranslator;
            case IExpression.TYPE_LOGIC:
                if(logicExpressionTranslator==null)
                {
                    synchronized (LogicExpressionTranslator.class) {
                        if(logicExpressionTranslator==null)
                        {
                            logicExpressionTranslator = new LogicExpressionTranslator();
                        }
                    }
                }
                return  logicExpressionTranslator;
            case IExpression.TYPE_CHAINLOGIC:
                if(chainLogicExpressionTranslator==null)
                {
                    synchronized (ChainLogicExpressionTranslator.class) {
                        if(chainLogicExpressionTranslator==null)
                        {
                            chainLogicExpressionTranslator = new ChainLogicExpressionTranslator();
                        }
                    }
                }
                return  chainLogicExpressionTranslator;
            default:
                logger.debug(type);
                throw new IllegalArgumentException("非法的表达式!");
        }
    }
}
