package com.pronoia.camel.deployment.integrations.impl;

import com.pronoia.camel.deployment.interfaces.Filter;

import org.apache.camel.Body;

public class EPICadINGNMED_ipFilter extends Filter {
    @Override
    public boolean qualify(@Body String body) {
        log.info( "(Filter v1.0.0) Processing body: {}", body);
        return true;
    }
}
