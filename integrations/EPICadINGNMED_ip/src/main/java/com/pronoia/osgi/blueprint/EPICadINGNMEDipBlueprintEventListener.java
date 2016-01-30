package com.pronoia.osgi.blueprint;

import org.apache.aries.blueprint.ExtendedBeanMetadata;
import org.apache.camel.CamelContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.container.BlueprintEvent;
import org.osgi.service.blueprint.container.BlueprintListener;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EPICadINGNMEDipBlueprintEventListener implements BlueprintListener {
    Logger log = LoggerFactory.getLogger(this.getClass());

    Bundle bundle;
    BundleContext bundleContext;
    BlueprintContainer blueprintContainer;

    @Override
    public void blueprintEvent(BlueprintEvent blueprintEvent) {
        if ( null == bundle  ||  bundle.getBundleId() == blueprintEvent.getBundle().getBundleId() ) {
            log.info("Event Fired {} - injected bundle id {}, event bundle id {}, event bundle symbolic name {}",
                    getType(blueprintEvent), bundle.getBundleId(),
                    blueprintEvent.getBundle().getBundleId(), blueprintEvent.getBundle().getSymbolicName());
            switch (blueprintEvent.getType()) {
                case BlueprintEvent.CREATING:
                    break;
                case BlueprintEvent.CREATED:
                    break;
                case BlueprintEvent.DESTROYING:
                    break;
                case BlueprintEvent.DESTROYED:
                    break;
                case BlueprintEvent.FAILURE:
                    log.info("FAILURE Cause: {}", blueprintEvent.getCause());
                    break;
                case BlueprintEvent.GRACE_PERIOD:
                    log.info("GRACE_PERIOD dependencies: {}", blueprintEvent.getDependencies());
                    break;
                case BlueprintEvent.WAITING:
                    log.info("WAITING dependencies: {}", blueprintEvent.getDependencies());
                    handleWaiting( blueprintEvent);
                    break;
                default:
                    log.error("Unknown Event Type");
            }
        }
    }

    private void handleWaiting(BlueprintEvent blueprintEvent) {
        for (String componentId: blueprintContainer.getComponentIds()) {
            Object component = blueprintContainer.getComponentInstance( componentId );
            if ( component instanceof CamelContext ) {
                log.info( "Stopping CamelContext: {}", componentId );
                try {
                    ((CamelContext)component).stop();
                } catch (Exception e) {
                    log.error( "Exception encountered stopping CamelContext: {}", componentId);
                }
            }
        }
    }

    static String getType(BlueprintEvent blueprintEvent) {
        String eventTypeString;

        switch ( blueprintEvent.getType() ) {
            case BlueprintEvent.CREATING:
                eventTypeString = "CREATING";
                break;
            case BlueprintEvent.CREATED:
                eventTypeString = "CREATED";
                break;
            case BlueprintEvent.DESTROYING:
                eventTypeString = "DESTROYING";
                break;
            case BlueprintEvent.DESTROYED:
                eventTypeString = "DESTROYED";
                break;
            case BlueprintEvent.FAILURE:
                eventTypeString = "FAILURE";
                break;
            case BlueprintEvent.GRACE_PERIOD:
                eventTypeString = "GRACE_PERIOD";
                break;
            case BlueprintEvent.WAITING:
                eventTypeString = "WAITING";
                break;
            default:
                eventTypeString = "UNKNOWN";
        }

        return eventTypeString;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public BlueprintContainer getBlueprintContainer() {
        return blueprintContainer;
    }

    public void setBlueprintContainer(BlueprintContainer blueprintContainer) {
        this.blueprintContainer = blueprintContainer;
    }
}
