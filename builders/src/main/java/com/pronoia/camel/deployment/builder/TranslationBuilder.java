package com.pronoia.camel.deployment.builder;

import com.pronoia.camel.deployment.interfaces.Filter;
import com.pronoia.camel.deployment.interfaces.Translation;

import org.apache.camel.builder.RouteBuilder;

public class TranslationBuilder extends RouteBuilder {

    String qualifier;
    Filter filter;
    Translation translation;

    @Override
    public void configure() throws Exception {

        from( "direct://source" )
                .choice()
                    .when().simple( qualifier )
                        .bean( translation, "translate" )
                    .otherwise()
                        .log( "Message Filtered")
                .endChoice()


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
