package com.pronoia.camel.deployment.integrations.impl;

import com.pronoia.camel.deployment.interfaces.Filter;
import com.pronoia.camel.deployment.interfaces.ServiceInterface;

import org.apache.camel.Body;

public class EPICadESCRIPT_ipFilter extends Filter {
    ServiceInterface service;

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

    public ServiceInterface getService() {
        return service;
    }

    public void setService(ServiceInterface service) {
        this.service = service;
    }
}
