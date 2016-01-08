package com.pronoia.camel.deployment.affiliates.CP;

import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class CP_EPICadINGNMED_ipTest extends CamelBlueprintTestSupport {
    @EndpointInject( uri = "mock://log:complete")
    MockEndpoint complete;

    @Override
    public String isMockEndpoints() {
        return "log:*";
    }

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/CP_EPICadINGNMED_ip.xml";
    }

    @Test
    public void testComplete() throws Exception {
        complete.expectedMessageCount(1);

        // source.sendBody( "Hello");

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }
}
