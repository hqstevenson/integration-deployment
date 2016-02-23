package com.pronoia.camel.deployment.affiliates.itest.ip;


import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import com.pronoia.camel.deployment.affiliates.itest.support.FuseTestSupport;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.karaf.features.FeaturesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.util.Filter;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.OptionUtils.combine;

@RunWith(PaxExam.class)
//@ExamReactorStrategy(ActiveMQPerClass.class)
public class CP_EPICadESCRIPT_ipTest extends FuseTestSupport {

    static final String TEST_MESSAGE =
            "MSH|^~\\&|EPICCARE|WB^WBLU|EGATE||20160216103717|S11576|ADT^A31|222073|P|2.3|||||||||\r"
                    + "EVN|A31|20160216103716||REG_UPDATE_VISIT_CHANGE|S11576^SPARROW^KAREN^^^^^^WB^^^^^WBLU||\r"
                    + "PID|1||18138^^^^EPI~50001177^^^^SHEPTMRN||ZZTEST^MIDASTWOONE||19671009|M||Guamanian or Chamorro|876 WILLOW WAY^^OAKLAND^CA^94601^USA^P^^ALAMEDA|ALAMEDA|(888)555-2222^P^7^^^888^5552222||AFRIKAANS|OTHER|AFRICAN METH||000-00-0009|||OTHER HISPANIC/LATINO/SPANISH ORIGIN||||||||N||\r"
                    + "PD1||||||||||||||||||\r" + "CON|1|Ins Card|||||||||||||\r"
                    + "CON|2|Adv Dir|||||||||NOT RECV||||\r"
                    + "CON|3|WBRNBRHIPAA|||||||||RECEIVED||20160216103600||\r"
                    + "NK1|1|TEST^WIFE^^|Wife|^^^^^USA|||EC|||||||||||||||||||||||||||\r"
                    + "NK1|2||||(888)555-2222^^^^^888^5552222||EMP||||||||||||||||||||||||||none|UNKNOWN\r"
                    + "PV1|1|||||||||||||||||||COMM||||||||||||||||||||||||||||||||||\r"
                    + "PV2||||||||||||||||||||||N|||||||||||||||||||||||||||\r"
                    + "AL1|1||45501^ALLERGY NOT ON FILE^||||||\r"
                    + "GT1|1|17826|ZZTEST^MIDASTWOONE^^||876 WILLOW WAY^^OAKLAND^CA^94601^USA^^^ALAMEDA|(888)555-2222^^^^^888^5552222||19671009|M|P/F|SLF|000-00-0009||||||(888)555-2222^^^^^888^5552222||UNKNOWN|||||||||||||||||||||||||||||\r"
                    + "IN1|1|27012^BAY AREA DELIVERY DRIVERS|13003|BLUE CROSS|PO BOX 60007^^LOS ANGELES^CA^90060^|||31654||||||||ZZTEST^MIDASTWOONE^^|Self|19671009|876 WILLOW WAY^^OAKLAND^CA^94601^USA^^^ALAMEDA|||1*|||YES||||||||||90415|1654||||||9^Unknown|M||NeedsReview||BOTH||\r"
                    + "IN2||000-00-0009|||Payor Plan||||||||||||||||||||||||||||||||||||||||||||||||||||||||1654||(888)555-2222^^^^^888^5552222|(888)555-2222^^^^^888^5552222||||||\r";

    static final String RESULT_MESSAGE =
            "MSH|^~\\&|EPICCARE|CP|SUNQUEST||20160216103717|S11576|ADT^A28|222073|P|2.3|||||||||\r"
                    + "EVN|A31|20160216103716||REG_UPDATE_VISIT_CHANGE|S11576^SPARROW^KAREN^^^^^^WB^^^^^WBLU||\r"
                    + "PID|1||50001177^^^^CP||ZZTEST^MIDASTWOONE||19671009|M||Guamanian or Chamorro|876 WILLOW WAY^-^OAKLAND^CA^94601^USA^P^^ALAMEDA|ALAMEDA|(888)555-2222||AFRIKAANS|OTHER|AFRICAN METH||000-00-0009|||OTHER HISPANIC/LATINO/SPANISH ORIGIN||||||||N||\r"
                    + "PV1|1|WBLUPRE|PREADM|||||||||||||||||||||||||||||||||||||||||||||||||||\r"
                    + "GT1|1|17826|ZZTEST^MIDASTWOONE^^||876 WILLOW WAY^^OAKLAND^CA^94601^USA^^^ALAMEDA|(888)555-2222^^^^^888^5552222||19671009|M|P/F|SLF|000-00-0009||||||(888)555-2222^^^^^888^5552222||UNKNOWN\r"
                    + "IN1|1|27012^BAY AREA DELIVERY DRIVERS|13003|BLUE CROSS|PO BOX 60007^^LOS ANGELES^CA^90060^|||31654||||||||ZZTEST^MIDASTWOONE^^|Self|19671009|876 WILLOW WAY^^OAKLAND^CA^94601^USA^^^ALAMEDA|||1*|||YES||||||||||90415|1654||||||9^Unknown|M||NeedsReview||BOTH\r"
                    + "IN2||000-00-0009|||Payor Plan||||||||||||||||||||||||||||||||||||||||||||||||||||||||1654||(888)555-2222^^^^^888^5552222|(888)555-2222^^^^^888^5552222\r";

    // This only seems to work if the route is setup that way to begin with - TestSupport mocking doesn't work
    @EndpointInject(uri = "mock://complete")
    MockEndpoint complete;

    /*
    @EndpointInject(uri = "mock://log:complete?level=INFO")
    MockEndpoint logComplete;
    */

    @Inject
    FeaturesService featuresService;

    @Inject
    @Filter("camel.context.name=CP_EPICadESCRIPT_ip")
    protected CamelContext paxCamelContext;

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return paxCamelContext;
    }

    @Override
    public String isMockEndpoints() {
        return "*";
    }

    @Configuration
    public Option[] config() {
        return combine(
                baseContainer(),
                mavenBundle().groupId("com.pronoia.deployment.interfaces").artifactId("osgi-services").version("2.0.0"),
                mavenBundle().groupId("com.pronoia.deployment.interfaces").artifactId("builder-support").version("2.0.0"),
                mavenBundle().groupId("com.pronoia.deployment.osgi").artifactId("service-one").version("2.0.0-SNAPSHOT"),
                mavenBundle().groupId("com.pronoia.deployment.osgi").artifactId("service-two").version("2.0.0-SNAPSHOT"),
                mavenBundle().groupId("com.pronoia.deployment.camel").artifactId("builders").version("2.0.0-SNAPSHOT"),
                mavenBundle().groupId("com.pronoia.deployment.camel.integrations.EPICadESCRIPT_ip").artifactId("beans").version("2.0.0-SNAPSHOT"),
                mavenBundle().groupId("com.pronoia.deployment.camel.affiliate").artifactId("CP_EPICadESCRIPT_ip").version("2.0.0-SNAPSHOT")
        );
        // return container("org.sutterhealth.eis.integration.ad.EPICadSQLAB_ip", "CP_EPICadSQLAB_ip");
        //return baseContainer();
    }

    @Test
    public void testProvisioning() throws Exception {
        // MockEndpoint complete = (MockEndpoint) paxCamelContext.getEndpoint("mock://complete");
        complete.expectedMessageCount(1);
        assertTrue(featuresService.isInstalled(featuresService.getFeature("camel-blueprint")));

        assertNotNull(paxCamelContext);
        String contextList = executeCommand("camel:context-list");
        assertTrue(contextList.contains("CP_EPICadESCRIPT_ip"));

        // complete.assertIsSatisfied(15, TimeUnit.SECONDS);
        assertMockEndpointsSatisfied(15, TimeUnit.SECONDS);
    }

    //TODO: figure out how to test the route queue to queue
}