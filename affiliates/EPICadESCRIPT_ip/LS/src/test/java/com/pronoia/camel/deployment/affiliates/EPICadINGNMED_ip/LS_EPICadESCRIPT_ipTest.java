package com.pronoia.camel.deployment.affiliates.EPICadINGNMED_ip;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.pronoia.camel.deployment.interfaces.FilterServiceInterface;
import com.pronoia.camel.deployment.interfaces.TranslationServiceInterface;
import com.pronoia.camel.deployment.services.stubs.FilterServiceStub;
import com.pronoia.camel.deployment.services.stubs.TranslationServiceStub;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;

public class LS_EPICadESCRIPT_ipTest extends CamelBlueprintTestSupport {
    @EndpointInject( uri = "mock://log:complete")
    MockEndpoint complete;

    @Override
    public String isMockEndpoints() {
        return "log:*";
    }

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/LS_EPICadESCRIPT_ip.xml";
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        Properties props = new Properties();
        props.setProperty("qualifier", "true");
        return props;
    }

    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        services.put( FilterServiceInterface.class.getName(), asService(new FilterServiceStub(), "instance", "one"));
        services.put( TranslationServiceInterface.class.getName(), asService(new TranslationServiceStub(), "instance", "one"));
    }

    @Test
    public void testComplete() throws Exception {
        complete.expectedMessageCount(1);

        assertMockEndpointsSatisfied(10, TimeUnit.SECONDS);
    }
}
