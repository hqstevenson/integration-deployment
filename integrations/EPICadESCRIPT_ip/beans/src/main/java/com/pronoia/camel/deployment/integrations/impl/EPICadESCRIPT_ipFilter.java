package com.pronoia.camel.deployment.integrations.impl;

import com.pronoia.deployment.interfaces.Filter;
import com.pronoia.deployment.osgi.services.FilterServiceInterface;

import org.apache.camel.Body;

public class EPICadESCRIPT_ipFilter extends Filter {
    FilterServiceInterface service;

    @Override
    public boolean qualify(@Body String body) {
        log.info( "(Filter v1.0.0) Processing body: {}", body);
        if ( null != service ) {
            service.execute(body);
        } else {
            log.error( "NO SERVICE REFERENCE");
        }
        return true;
    }

    public FilterServiceInterface getService() {
        return service;
    }

    public void setService(FilterServiceInterface service) {
        this.service = service;
    }
}
