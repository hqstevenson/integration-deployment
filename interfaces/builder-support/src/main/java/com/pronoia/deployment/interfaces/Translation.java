package com.pronoia.deployment.interfaces;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Translation {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean isSplitter(){
        return false;
    }

    public abstract String translate(@Body String body);
}
