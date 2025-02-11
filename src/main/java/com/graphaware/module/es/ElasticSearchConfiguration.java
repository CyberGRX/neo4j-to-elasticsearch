/*
 * Copyright (c) 2013-2019 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.graphaware.module.es;

import com.graphaware.common.policy.inclusion.InclusionPolicies;
import com.graphaware.common.policy.inclusion.none.IncludeNoRelationships;
import com.graphaware.module.es.mapping.DefaultMapping;
import com.graphaware.module.es.mapping.Mapping;
import com.graphaware.runtime.config.BaseTxDrivenModuleConfiguration;
import com.graphaware.runtime.policy.InclusionPoliciesFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link BaseTxDrivenModuleConfiguration} for {@link ElasticSearchModule}.
 */
public class ElasticSearchConfiguration extends BaseTxDrivenModuleConfiguration<ElasticSearchConfiguration> {

    private static final String DEFAULT_KEY_PROPERTY = "uuid";
    private static final boolean DEFAULT_RETRY_ON_ERROR = false;
    private static final boolean DEFAULT_EXECUTE_BULK = true;
    public static final String DEFAULT_PROTOCOL = "http";
    private static final InclusionPolicies DEFAULT_INCLUSION_POLICIES = InclusionPoliciesFactory.allBusiness().with(IncludeNoRelationships.getInstance());
    private static final Mapping DEFAULT_MAPPING = DefaultMapping.newInstance();
    private static final boolean DEFAULT_ASYNC_INDEXATION = false;
    public static final int DEFAULT_READ_TIMEOUT = 3000; //3s
    public static final int DEFAULT_CONNECTION_TIMEOUT = 3000; //3s
    public static final int DEFAULT_MAX_CONSECUTIVE_ERRORS = 5;

    static {
        DEFAULT_MAPPING.configure(new HashMap<>());
    }

    private static final int DEFAULT_QUEUE_CAPACITY = 10000;
    private static final int DEFAULT_REINDEX_BATCH_SIZE = 1000;
    private static final String DEFAULT_AUTH_USER = null;
    private static final String DEFAULT_AUTH_PASSWORD = null;

    private final String protocol;
    private final String uri;
    private final String port;
    private final String keyProperty;
    private final boolean retryOnError;
    private final int maxConsecutiveErrors;
    private final int queueCapacity;
    private final boolean executeBulk;
    private final String authUser;
    private final String authPassword;
    private final Mapping mapping;
    private final int reindexBatchSize;
    private final boolean asyncIndexation;
    private final int readTimeout;
    private final int connectionTimeout;


    /**
     * Construct a new configuration.
     *
     * @param inclusionPolicies specifying which nodes and node properties to index in Elasticsearch. Must not be <code>null</code>.
     * @param initializeUntil   until what time in ms since epoch it is ok to re-index the entire database in case the configuration has changed since
     *                          the last time the module was started, or if it is the first time the module was registered.
     *                          0 for never. The purpose of this is not to re-index all the time if the user is unaware.
     * @param protocol          Elasticsearch connection protocol. Default is <code>http</code>
     * @param uri               Elasticsearch URI. Must not be <code>null</code>.
     * @param port              Elasticsearch port. Must not be <code>null</code>.
     * @param keyProperty       name of the node property that serves as the key, under which the node will be indexed in Elasticsearch. Must not be <code>null</code> or empty.
     * @param retryOnError      whether to retry an index update after a failure (<code>true</code>) or throw the update away (<code>false</code>).
     * @param maxConsecutiveErrors How many consecutive errors to allow in the Elasticsearch writer to allow before giving up on the retried elements (Default is <code>5</code>).
     * @param queueCapacity     capacity of the queue holding operations to be written to Elasticsearch. Must be positive.
     * @param executeBulk       whether or not to execute updates against Elasticsearch in bulk. It is recommended to set this to <code>true</code>.*
     * @param mapping           name of the mapping class to use to convert Neo4j node/relationships to ElasticSearch documents.
     * @param asyncIndexation   whether indexation should be asynchronous (meaning that the plugin will be responsive even though indexation is not finished)
     * @param readTimeout       es client connection read timeout
     * @param connectionTimeout es client connection timeout
     */
    private ElasticSearchConfiguration(InclusionPolicies inclusionPolicies, long initializeUntil, String protocol, String uri, String port, String keyProperty, boolean retryOnError, int maxConsecutiveErrors, int queueCapacity, int reindexBatchSize, boolean executeBulk, String authUser, String authPassword, Mapping mapping, boolean asyncIndexation, int readTimeout, int connectionTimeout) {
        super(inclusionPolicies, initializeUntil);
        this.protocol = protocol;
        this.uri = uri;
        this.port = port;
        this.keyProperty = keyProperty;
        this.retryOnError = retryOnError;
        this.maxConsecutiveErrors = maxConsecutiveErrors;
        this.queueCapacity = queueCapacity;
        this.reindexBatchSize = reindexBatchSize;
        this.executeBulk = executeBulk;
        this.authUser = authUser;
        this.authPassword = authPassword;
        this.mapping = mapping;
        this.asyncIndexation = asyncIndexation;
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ElasticSearchConfiguration newInstance(InclusionPolicies inclusionPolicies, long initializeUntil) {
        return new ElasticSearchConfiguration(inclusionPolicies, initializeUntil, getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), DEFAULT_AUTH_USER, DEFAULT_AUTH_PASSWORD, getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public static ElasticSearchConfiguration defaultConfiguration() {
        return new ElasticSearchConfiguration(DEFAULT_INCLUSION_POLICIES, NEVER, DEFAULT_PROTOCOL, null, null, DEFAULT_KEY_PROPERTY, DEFAULT_RETRY_ON_ERROR, DEFAULT_MAX_CONSECUTIVE_ERRORS, DEFAULT_QUEUE_CAPACITY, DEFAULT_REINDEX_BATCH_SIZE, DEFAULT_EXECUTE_BULK, DEFAULT_AUTH_USER, DEFAULT_AUTH_PASSWORD, DEFAULT_MAPPING, DEFAULT_ASYNC_INDEXATION, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
    }

    public ElasticSearchConfiguration withProtocol(String protocol) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), protocol, getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withUri(String uri) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), uri, getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withPort(String port) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), port, getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withKeyProperty(String keyProperty) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), keyProperty, isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withRetryOnError(boolean retryOnError) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), retryOnError, getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withQueueCapacity(int queueCapacity) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(),  getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), queueCapacity, getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withReindexBatchSize(int reindexBatchSize) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), reindexBatchSize, isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withExecuteBulk(boolean executeBulk) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), executeBulk, getAuthUser(), getAuthPassword(), getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }
    
    public ElasticSearchConfiguration withAuthCredentials(String authUser, String authPassword) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), authUser, authPassword, getMapping(), isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withReadTimeout(int readTimeout) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), authUser, authPassword, getMapping(), isAsyncIndexation(), readTimeout, getConnectionTimeout());
    }

    public ElasticSearchConfiguration withConnectionTimeout(int connectionTimeout) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), authUser, authPassword, getMapping(), isAsyncIndexation(), getReadTimeout(), connectionTimeout);
    }

    public ElasticSearchConfiguration withMapping(Mapping mapping, Map<String, String> mappingConfig) {
        // prevents mappings from being started without configure() from being called
        mapping.configure(mappingConfig);
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), mapping, isAsyncIndexation(), getReadTimeout(), getConnectionTimeout());
    }

    public ElasticSearchConfiguration withAsyncIndexation(boolean asyncIndexation) {
        return new ElasticSearchConfiguration(getInclusionPolicies(), initializeUntil(), getProtocol(), getUri(), getPort(), getKeyProperty(), isRetryOnError(), getMaxConsecutiveErrors(), getQueueCapacity(), getReindexBatchSize(), isExecuteBulk(), getAuthUser(), getAuthPassword(), getMapping(), asyncIndexation, getReadTimeout(), getConnectionTimeout());
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUri() {
        return uri;
    }

    public String getPort() {
        return port;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public boolean isRetryOnError() {
        return retryOnError;
    }

    public int getMaxConsecutiveErrors() {
        return maxConsecutiveErrors;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public int getReindexBatchSize() {
        return reindexBatchSize;
    }

    public boolean isExecuteBulk() {
        return executeBulk;
    }

    public boolean isAsyncIndexation() {
        return asyncIndexation;
    }

    public String getAuthUser() {
        return authUser;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public Mapping getMapping() {
        return mapping;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ElasticSearchConfiguration that = (ElasticSearchConfiguration) o;

        if (initializeUntil() != that.initializeUntil()) { //a bit of a hack to force initialization if this changes
            return false;
        }
        if (retryOnError != that.retryOnError) {
            return false;
        }
        if (maxConsecutiveErrors != that.maxConsecutiveErrors) {
            return false;
        }
        if (queueCapacity != that.queueCapacity) {
            return false;
        }

        if (reindexBatchSize != that.reindexBatchSize) {
            return false;
        }

        if (executeBulk != that.executeBulk) {
            return false;
        }
        if (!protocol.equals(that.protocol)) {
            return false;
        }
        if (!uri.equals(that.uri)) {
            return false;
        }
        if (!port.equals(that.port)) {
            return false;
        }
        if (asyncIndexation != that.asyncIndexation) {
            return false;
        }
        if (readTimeout != that.readTimeout) {
            return false;
        }
        if (connectionTimeout != that.connectionTimeout) {
            return false;
        }
        return keyProperty.equals(that.keyProperty);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (initializeUntil() ^ (initializeUntil() >>> 32));
        result = 31 * result + protocol.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + port.hashCode();
        result = 31 * result + keyProperty.hashCode();
        result = 31 * result + (retryOnError ? 1 : 0);
        result = 31 * result + maxConsecutiveErrors;
        result = 31 * result + queueCapacity;
        result = 31 * result + reindexBatchSize;
        result = 31 * result + (executeBulk ? 1 : 0);
        result = 31 * result + (asyncIndexation ? 1 : 0);
        result = 31 * result + readTimeout;
        result = 31 * result + connectionTimeout;
        return result;
    }
}