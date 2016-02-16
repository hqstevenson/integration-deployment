package com.pronoia.camel.deployment.integrations.impl;

import com.pronoia.deployment.interfaces.Translation;
import com.pronoia.deployment.osgi.services.TranslationServiceInterface;

import org.apache.camel.Body;

public class EPICadESCRIPT_ipTranslation extends Translation {
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
