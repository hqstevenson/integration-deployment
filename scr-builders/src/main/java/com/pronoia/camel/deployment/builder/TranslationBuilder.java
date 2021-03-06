package com.pronoia.camel.deployment.builder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.camel.builder.PredicateBuilder.not;

public class TranslationBuilder extends RouteBuilder {

    String affiliate = "Unknown";

    String source = "direct://source";
    String target = "mock://complete";
    String body = "Default Body";

    String qualifier;
    String filterBeanId;
    String translationBeanId;

    @Override
    public void configure() throws Exception {

        from( source )
                .log( "(Builder - Affiliate " + affiliate +")" )
                .choice()
                    .when( body().isNull() )
                        .setBody().header( Exchange.TIMER_FIRED_TIME )
                        .endChoice()
                .end()
                .log( "Processing qualifier - " + qualifier)
                .filter( not( simple(qualifier)) )
                    .log( "Message disqualified")
                    .stop()
                .end()
                .log( "Qualifier Passed - calling filter")
                .filter( not(method(filterBeanId, "qualify")))
                    .log( "Message Filtered by bean id call")
                    .stop()
                .end()
                .log( "Filter Passed - Calling Translation bean")
                .toF( "bean://%s?method=translate", translationBeanId )
                .log( "Complete")
                .to( target )
        ;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getFilter() {
        return filterBeanId;
    }

    public void setFilter(String filterBeanId) {
        this.filterBeanId = filterBeanId;
    }

    public String getTranslation() {
        return translationBeanId;
    }

    public void setTranslation(String translationBeanId) {
        this.translationBeanId = translationBeanId;
    }
}
