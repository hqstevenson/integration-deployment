package com.pronoia.osgi.blueprint;

import java.util.Collection;

import org.apache.camel.CamelContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.reflect.ReferenceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EPICadINGNMEDipServiceReferenceListener {
    Logger log = LoggerFactory.getLogger(this.getClass());

    Bundle bundle;
    BundleContext bundleContext;
    BlueprintContainer blueprintContainer;

    public void onBind( ServiceReference serviceReference) {
        log.info( "onBind: {}", serviceReference);
        if ( null != blueprintContainer ) {
            Collection<ReferenceMetadata> metadataCollection = blueprintContainer.getMetadata(ReferenceMetadata.class);
            if ( null != metadataCollection  &&  metadataCollection.size() > 0 ) {
                for (ReferenceMetadata metadata : metadataCollection) {
                    String id = metadata.getId();
                    if (null != id && !id.startsWith(".camelBlueprint.")) {
                        log.info("MetaData for {} - {}: interface {}, filter {}, id {}", metadata.getComponentName(), metadata.getAvailability(),
                                metadata.getInterface(), metadata.getFilter(), id);
                    }
                }
            }
        }

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

    public void onUnbind( ServiceReference serviceReference) {
        log.info( "onUnbind: {}", serviceReference);

        if ( null != blueprintContainer ) {
            Collection<ReferenceMetadata> metadataCollection = blueprintContainer.getMetadata(ReferenceMetadata.class);
            if ( null != metadataCollection  &&  metadataCollection.size() > 0 ) {
                for (ReferenceMetadata metadata : metadataCollection) {
                    String id = metadata.getId();
                    if (null != id && !id.startsWith(".camelBlueprint.")) {
                        log.info("MetaData for {} - {}: interface {}, filter {}, id {}", metadata.getComponentName(), metadata.getAvailability(),
                                metadata.getInterface(), metadata.getFilter(), id);
                    }
                }
            }
        }
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
