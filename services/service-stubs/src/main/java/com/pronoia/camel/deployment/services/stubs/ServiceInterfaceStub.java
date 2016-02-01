package com.pronoia.camel.deployment.services.stubs;

import com.pronoia.camel.deployment.interfaces.ServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceInterfaceStub implements ServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String execute(String payload) {
        log.info( "Executing {} ...", this.hashCode());
        return payload;
    }
}
