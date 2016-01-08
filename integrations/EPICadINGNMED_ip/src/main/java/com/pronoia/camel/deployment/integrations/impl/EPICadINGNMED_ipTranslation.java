package com.pronoia.camel.deployment.integrations.impl;

import com.pronoia.camel.deployment.interfaces.Translation;

import org.apache.camel.Body;

public class EPICadINGNMED_ipTranslation extends Translation {
    @Override
    public String translate(@Body String body) {
        log.info( "(Translation v2.0.0) Processing body: {}", body);
        return body;
    }
}
