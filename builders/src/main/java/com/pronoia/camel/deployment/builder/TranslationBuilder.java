package com.pronoia.camel.deployment.builder;

import com.pronoia.camel.deployment.interfaces.Filter;
import com.pronoia.camel.deployment.interfaces.Translation;

import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.builder.PredicateBuilder.not;

public class TranslationBuilder extends RouteBuilder {

    String affiliate = "Unknown";

    String source = "direct://source";
    String target = "mock://complete";
    String body = "Default Body";

    String qualifier;
    Filter filter;
    Translation translation;

    @Override
    public void configure() throws Exception {

        from( source )
                .log( "(Builder v1.0.0 - Affiliate " + affiliate +") Processing body " )
                .filter( not( simple(qualifier)) )
                    .log( "Message disqualified")
                    .stop()
                .end()
                .log( "Qualifier Passed - calling filter")
                .filter( not(method(filter, "qualify")) )
                    .log( "Message Filtered")
                    .stop()
                .end()
                .log( "Filter Passed - Calling Translation bean")
                .bean( translation, "translate" )
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

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }
}
