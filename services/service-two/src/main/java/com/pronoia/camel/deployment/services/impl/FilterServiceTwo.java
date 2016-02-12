package com.pronoia.camel.deployment.services.impl;

import com.pronoia.camel.deployment.interfaces.FilterServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterServiceTwo implements FilterServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String execute(String payload) {
        log.info( "Executing {} ...", this.hashCode());
        return payload;
    }
}
