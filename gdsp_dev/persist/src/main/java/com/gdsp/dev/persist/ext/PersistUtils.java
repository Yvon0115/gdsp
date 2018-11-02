package com.gdsp.dev.persist.ext;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author paul.yang
 * @version 1.0 14-12-18
 * @since 1.6
 */
public final class PersistUtils {

    private PersistUtils() {}

    public static boolean hasTransaction(DataSource dataSource) {
        ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
        return conHolder != null && conHolder.isSynchronizedWithTransaction();
    }
}
