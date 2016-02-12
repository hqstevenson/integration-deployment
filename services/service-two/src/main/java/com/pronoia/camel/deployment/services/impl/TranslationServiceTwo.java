package com.pronoia.camel.deployment.services.impl;

import com.pronoia.camel.deployment.interfaces.TranslationServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranslationServiceTwo implements TranslationServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String execute(String payload) {
        log.info( "Executing {} ...", this.hashCode());
        return payload;
    }
}
