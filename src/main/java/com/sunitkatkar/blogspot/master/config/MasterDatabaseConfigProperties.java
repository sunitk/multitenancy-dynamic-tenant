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
package com.sunitkatkar.blogspot.master.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Master database configuration properties which are read from the
 * application.yml file
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@Configuration
@ConfigurationProperties("multitenancy.mtapp.master.datasource")
public class MasterDatabaseConfigProperties {

    /** database url */
    private String url;

    /** database username */
    private String username;

    /** database password */
    private String password;

    /** database driver */
    private String driverClassName;

    // Following are for setting the Hikari Connection Pool properties. Spring
    // Boot uses Hikari CP by default.

    /**
     * Maximum number of milliseconds that a client will wait for a connection
     * from the pool. If this time is exceeded without a connection becoming
     * available, a SQLException will be thrown from
     * javax.sql.DataSource.getConnection().
     */
    private long connectionTimeout;

    /**
     * The property controls the maximum size that the pool is allowed to reach,
     * including both idle and in-use connections. Basically this value will
     * determine the maximum number of actual connections to the database
     * backend.
     * 
     * When the pool reaches this size, and no idle connections are available,
     * calls to getConnection() will block for up to connectionTimeout
     * milliseconds before timing out.
     */
    private int maxPoolSize;

    /**
     * This property controls the maximum amount of time (in milliseconds) that
     * a connection is allowed to sit idle in the pool. Whether a connection is
     * retired as idle or not is subject to a maximum variation of +30 seconds,
     * and average variation of +15 seconds. A connection will never be retired
     * as idle before this timeout. A value of 0 means that idle connections are
     * never removed from the pool.
     */
    private long idleTimeout;

    /**
     * The property controls the minimum number of idle connections that
     * HikariCP tries to maintain in the pool, including both idle and in-use
     * connections. If the idle connections dip below this value, HikariCP will
     * make a best effort to restore them quickly and efficiently.
     */
    private int minIdle;

    /**
     * The name for the master database connection pool
     */
    private String poolName;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MasterDatabaseConfigProperties [url=");
        builder.append(url);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", driverClassName=");
        builder.append(driverClassName);
        builder.append(", connectionTimeout=");
        builder.append(connectionTimeout);
        builder.append(", maxPoolSize=");
        builder.append(maxPoolSize);
        builder.append(", idleTimeout=");
        builder.append(idleTimeout);
        builder.append(", minIdle=");
        builder.append(minIdle);
        builder.append(", poolName=");
        builder.append(poolName);
        builder.append("]");
        return builder.toString();
    }

    // Getters and Setters
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the driverClassName
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * @param driverClassName
     *            the driverClassName to set
     */
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * @return the connectionTimeout
     */
    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * @param connectionTimeout
     *            the connectionTimeout to set
     */
    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * @return the maxPoolSize
     */
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     * @param maxPoolSize
     *            the maxPoolSize to set
     */
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    /**
     * @return the idleTimeout
     */
    public long getIdleTimeout() {
        return idleTimeout;
    }

    /**
     * @param idleTimeout
     *            the idleTimeout to set
     */
    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    /**
     * @return the minIdle
     */
    public int getMinIdle() {
        return minIdle;
    }

    /**
     * @param minIdle
     *            the minIdle to set
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * @return the poolName
     */
    public String getPoolName() {
        return poolName;
    }

    /**
     * @param poolName
     *            the poolName to set
     */
    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

}
