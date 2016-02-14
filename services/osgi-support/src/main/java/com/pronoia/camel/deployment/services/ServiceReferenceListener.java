package com.pronoia.camel.deployment.services;

import java.util.Collection;

import org.apache.camel.CamelContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.reflect.ReferenceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceReferenceListener {
    Logger log = LoggerFactory.getLogger(this.getClass());

    Bundle bundle;
    BlueprintContainer blueprintContainer;

    public void onBind( ServiceReference serviceReference) {
        log.info( "onBind[{}]: {}", this.hashCode(), serviceReference);

        startCamelContexts();
    }

    public void onUnbind( ServiceReference serviceReference) {
        log.info( "onUnbind[{}]: {}", this.hashCode(), serviceReference);

        // This is being done in the BlueprintEventListener right now - could be moved here
        stopCamelContexts();
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public BlueprintContainer getBlueprintContainer() {
        return blueprintContainer;
    }

    public void setBlueprintContainer(BlueprintContainer blueprintContainer) {
        this.blueprintContainer = blueprintContainer;
    }

    private void startCamelContexts() {
        if ( null != blueprintContainer ) {
            for (String componentId: blueprintContainer.getComponentIds()) {
                Object component = blueprintContainer.getComponentInstance( componentId );
                if ( component instanceof CamelContext) {
                    log.info( "Starting CamelContext: {}", componentId );
                    try {
                        ((CamelContext)component).start();
                    } catch (Exception e) {
                        log.error( "Exception encountered starting CamelContext: {}", componentId);
                    }
                }
            }
        }
    }

    private void stopCamelContexts() {
        if ( null != blueprintContainer ) {
            for (String componentId: blueprintContainer.getComponentIds()) {
                Object component = blueprintContainer.getComponentInstance( componentId );
                if ( component instanceof CamelContext) {
                    log.info( "Starting CamelContext: {}", componentId );
                    try {
                        ((CamelContext)component).stop();
                    } catch (Exception e) {
                        log.error( "Exception encountered starting CamelContext: {}", componentId);
                    }
                }
            }
        }
    }
}
