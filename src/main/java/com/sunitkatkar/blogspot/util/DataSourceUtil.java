/*
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunitkatkar.blogspot.util;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunitkatkar.blogspot.master.model.MasterTenant;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Utility class for DataSource
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 *
 */
public final class DataSourceUtil {

    private static final Logger LOG = LoggerFactory
            .getLogger(DataSourceUtil.class);

    /**
     * Utility method to create and configure a data source
     * 
     * @param masterTenant
     * @return
     */
    public static DataSource createAndConfigureDataSource(
            MasterTenant masterTenant) {
        HikariDataSource ds = new HikariDataSource();
        ds.setUsername(masterTenant.getUsername());
        ds.setPassword(masterTenant.getPassword());
        ds.setJdbcUrl(masterTenant.getUrl());
        ds.setDriverClassName("com.mysql.jdbc.Driver");

        // HikariCP settings - could come from the master_tenant table but
        // hardcoded here for brevity
        // Maximum waiting time for a connection from the pool
        ds.setConnectionTimeout(20000);

        // Minimum number of idle connections in the pool
        ds.setMinimumIdle(10);

        // Maximum number of actual connection in the pool
        ds.setMaximumPoolSize(20);

        // Maximum time that a connection is allowed to sit idle in the pool
        ds.setIdleTimeout(300000);
        ds.setConnectionTimeout(20000);

        // Setting up a pool name for each tenant datasource
        String tenantId = masterTenant.getTenantId();
        String tenantConnectionPoolName = tenantId + "-connection-pool";
        ds.setPoolName(tenantConnectionPoolName);
        LOG.info("Configured datasource:" + masterTenant.getTenantId()
                + ". Connection poolname:" + tenantConnectionPoolName);
        return ds;
    }
}
