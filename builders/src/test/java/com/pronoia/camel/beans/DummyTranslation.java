package com.pronoia.camel.beans;

import com.pronoia.camel.deployment.interfaces.Translation;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyTranslation extends Translation {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public String translate( @Body String body ) {
        log.info( "Translating Body: {}", body);
        return body;
    }
}
