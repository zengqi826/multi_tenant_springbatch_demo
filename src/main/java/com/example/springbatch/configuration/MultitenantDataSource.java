/**
 * 
 */
package com.example.springbatch.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author 86211
 *
 */
public class MultitenantDataSource extends AbstractRoutingDataSource {

    /** (non-Javadoc)
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }

}
