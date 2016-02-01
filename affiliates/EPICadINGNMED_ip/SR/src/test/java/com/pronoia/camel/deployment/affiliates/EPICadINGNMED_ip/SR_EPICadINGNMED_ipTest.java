package com.pronoia.camel.deployment.affiliates.EPICadINGNMED_ip;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.pronoia.camel.deployment.interfaces.ServiceInterface;
import com.pronoia.camel.deployment.services.stubs.ServiceInterfaceStub;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;

public class SR_EPICadINGNMED_ipTest extends CamelBlueprintTestSupport {
    @EndpointInject( uri = "mock://log:complete")
    MockEndpoint complete;

    @Override
    public String isMockEndpoints() {
        return "log:*";
    }

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/EPICadINGNMED_ip.xml,/OSGI-INF/blueprint/SR_EPICadINGNMED_ip.xml";
    }

    @Override
    protected void addServicesOnStartup(List<KeyValueHolder<String, KeyValueHolder<Object, Dictionary>>> services) {
        Dictionary<String, String> serviceOneProps = new Hashtable<>();
        serviceOneProps.put( "instance", "one" );

        Dictionary<String,String> serviceTwoProps = new Hashtable<>();
        serviceTwoProps.put( "instance", "two");

        services.add( asKeyValueService(ServiceInterface.class.getName(), new ServiceInterfaceStub(), serviceOneProps));
        services.add( asKeyValueService(ServiceInterface.class.getName(), new ServiceInterfaceStub(), serviceTwoProps));
    }

    @Test
    public void testComplete() throws Exception {
        complete.expectedMessageCount(1);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }
}
