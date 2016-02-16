package com.pronoia.camel.deployment.builder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.pronoia.camel.beans.DummyFilter;
import com.pronoia.camel.beans.DummyTranslation;
import com.pronoia.deployment.interfaces.Filter;
import com.pronoia.deployment.interfaces.Translation;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class TranslationBuilderTest extends CamelTestSupport{

    final String filterBeanId = "filter-bean";
    final String translationBeanId = "translation-bean";

    @EndpointInject( uri = "direct://source")
    ProducerTemplate source;

    @EndpointInject( uri = "mock://complete")
    MockEndpoint complete;

    Filter filter;
    Translation translation;

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();

        registry.bind( filterBeanId, new DummyFilter());
        registry.bind( translationBeanId, new DummyTranslation());

        return registry;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        TranslationBuilder builder = new TranslationBuilder();

        builder.setQualifier( "${body} not contains 'disqualify'" );
        builder.setFilter( filterBeanId );
        builder.setTranslation( translationBeanId );

        return builder;
    }

    @Test
    public void testComplete() throws Exception {
        complete.expectedMessageCount(1);

        source.sendBodyAndHeader( "Hello", Exchange.TIMER_FIRED_TIME, new Date() );

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testFiltered() throws Exception {
        complete.expectedMessageCount(0);

        source.sendBodyAndHeader( "filter Hello", Exchange.TIMER_FIRED_TIME, new Date() );

        assertMockEndpointsSatisfied(5, TimeUnit.SECONDS);
    }

    @Test
    public void testQualified() throws Exception {
        complete.expectedMessageCount(0);

        source.sendBodyAndHeader( "disqualify Hello", Exchange.TIMER_FIRED_TIME, new Date() );

        assertMockEndpointsSatisfied(5, TimeUnit.SECONDS);
    }
}