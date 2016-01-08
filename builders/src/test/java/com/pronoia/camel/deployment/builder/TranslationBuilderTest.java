package com.pronoia.camel.deployment.builder;

import java.util.concurrent.TimeUnit;

import com.pronoia.camel.beans.DummyFilter;
import com.pronoia.camel.beans.DummyTranslation;
import com.pronoia.camel.deployment.interfaces.Filter;
import com.pronoia.camel.deployment.interfaces.Translation;

import org.apache.camel.EndpointInject;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class TranslationBuilderTest extends CamelTestSupport{

    @EndpointInject( uri = "direct://source")
    ProducerTemplate source;

    @EndpointInject( uri = "mock://complete")
    MockEndpoint complete;

    Filter filter;
    Translation translation;

    @org.junit.Before
    public void setUp() throws Exception {
        filter = new DummyFilter();
        translation = new DummyTranslation();
        super.setUp();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        TranslationBuilder builder = new TranslationBuilder();

        builder.setQualifier( "${body} not contains 'disqualify'" );
        builder.setFilter( filter );
        builder.setTranslation( translation );

        return builder;
    }

    @Test
    public void testComplete() throws Exception {
        complete.expectedMessageCount(1);

        source.sendBody( "Hello");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testFiltered() throws Exception {
        complete.expectedMessageCount(0);

        source.sendBody( "filter Hello");

        assertMockEndpointsSatisfied(5, TimeUnit.SECONDS);
    }

    @Test
    public void testQualified() throws Exception {
        complete.expectedMessageCount(0);

        source.sendBody( "disqualify Hello");

        assertMockEndpointsSatisfied(5, TimeUnit.SECONDS);
    }
}