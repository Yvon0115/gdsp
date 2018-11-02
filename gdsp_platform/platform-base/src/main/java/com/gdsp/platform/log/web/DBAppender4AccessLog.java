package com.gdsp.platform.log.web;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;

import com.gdsp.dev.base.utils.UUIDUtils;

public class DBAppender4AccessLog extends DBAppenderBase<ILoggingEvent> {

    protected String               insertSQL;
    protected static final Method  GET_GENERATED_KEYS_METHOD;
    static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();
    private static final Logger logger = LoggerFactory.getLogger(DBAppender4AccessLog.class);
    static {
        Method getGeneratedKeysMethod;
        try {
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
        } catch (Exception ex) {
            getGeneratedKeysMethod = null;
            logger.error(ex.getMessage(),ex);
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }

    @Override
    public void start() {
        insertSQL = "insert into res_accesslog(id,res_id,name, type,url, msg, pk_user,createTime ) values("
                + "?,?,?,?,?,?,?,?)";
        super.start();
    }

    @Override
    protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement)
            throws Throwable {
        bindLoggingEventArgumentsWithPreparedStatement(insertStatement, event.getArgumentArray());
        int updateCount = insertStatement.executeUpdate();
        if (updateCount != 1) {
            addWarn("Failed to insert loggingEvent");
        }
    }

    protected void secondarySubAppend(ILoggingEvent event, Connection connection, long eventId) throws Throwable {}

    void bindLoggingEventArgumentsWithPreparedStatement(PreparedStatement stmt, Object[] argArray) throws SQLException {
        int arrayLen = argArray != null ? argArray.length : 0;
        int index = 1;
        stmt.setString(index, UUIDUtils.getRandomUUID());//id
        index++;
        //记录日志信息
        //res_id,name, type, button, url, msg,
        for (int i = 0; i < arrayLen; i++) {
            stmt.setString(index, argArray[i] + "");
            index++;
        }
        //	    //记录用户信息
        //	    stmt.setString(index, AppContext.getContext().getContextUserId());
        //	    index++;
        //记录时间
        stmt.setString(index, System.currentTimeMillis() / 1000 + "");
    }

    @Override
    protected String getInsertSQL() {
        return insertSQL;
    }

    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    @Override
    public void append(ILoggingEvent eventObject) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        try {
            connection = connectionSource.getConnection();
            if(connection != null){
            	connection.setAutoCommit(false);
                insertStatement = connection.prepareStatement(getInsertSQL());
                synchronized (this) {
                    subAppend(eventObject, connection, insertStatement);
                }
                connection.commit();
            }
        } catch (Throwable sqle) {
            addError("problem appending event", sqle);
            logger.error(sqle.getMessage(),sqle);
        } finally {
            try {
            	if(insertStatement != null){
            		insertStatement.close();
            	}
            	if(connection != null){
            		connection.close();
            	}
            } catch (SQLException e) {
            	logger.error(e.getMessage(),e);
            }
        }
    }
}
