package com.pronoia.camel.deployment.affiliates.EPICadSQLAB_ip;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.pronoia.camel.deployment.interfaces.FilterServiceInterface;
import com.pronoia.camel.deployment.services.stubs.FilterServiceStub;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;

public class NV_EPICadSQLAB_ipTest extends CamelBlueprintTestSupport {
    @EndpointInject( uri = "mock://log:complete")
    MockEndpoint complete;

    @Override
    public String isMockEndpoints() {
        return "log:*";
    }

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/NV_EPICadSQLAB_ip.xml";
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        Properties props = new Properties();
        props.setProperty("qualifier", "true");
        return props;
    }

    @Override
    protected void addServicesOnStartup(List<KeyValueHolder<String, KeyValueHolder<Object, Dictionary>>> services) {
        Dictionary<String, String> serviceOneProps = new Hashtable<>();
        serviceOneProps.put( "instance", "one" );

        Dictionary<String,String> serviceTwoProps = new Hashtable<>();
        serviceTwoProps.put( "instance", "two");

        services.add( asKeyValueService(FilterServiceInterface.class.getName(), new FilterServiceStub(), serviceOneProps));
        services.add( asKeyValueService(FilterServiceInterface.class.getName(), new FilterServiceStub(), serviceTwoProps));
    }

    @Test
    public void testComplete() throws Exception {
        complete.expectedMessageCount(1);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }
}
