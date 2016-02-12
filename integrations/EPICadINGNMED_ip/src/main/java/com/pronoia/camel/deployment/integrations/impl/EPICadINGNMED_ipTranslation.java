package com.pronoia.camel.deployment.integrations.impl;

import com.pronoia.camel.deployment.interfaces.FilterServiceInterface;
import com.pronoia.camel.deployment.interfaces.Translation;
import com.pronoia.camel.deployment.interfaces.TranslationServiceInterface;

import org.apache.camel.Body;

public class EPICadINGNMED_ipTranslation extends Translation {
    TranslationServiceInterface service;

    @Override
    public String translate(@Body String body) {
        log.info( "(Translation v1.0.0) Processing body: {}", body);
        if ( null != service ) {
            service.execute(body);
        } else {
            log.error( "NO SERVICE REFERENCE");
        }
        return body;
    }

    public TranslationServiceInterface getService() {
        return service;
    }

    public void setService(TranslationServiceInterface service) {
        this.service = service;
    }
}
