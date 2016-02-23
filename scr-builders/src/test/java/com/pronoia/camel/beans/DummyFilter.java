package com.pronoia.camel.beans;


import com.pronoia.deployment.interfaces.Filter;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyFilter extends Filter {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean qualify( @Body String body ) {
        log.info( "Filter Processing body: {}", body);

        if ( body.contains("filter")) {
            return false;
        }

        return true;
    }
}
