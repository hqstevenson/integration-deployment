package com.pronoia.camel.deployment.affiliates.itest.support;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import org.ops4j.pax.exam.ConfigurationManager;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

public class FuseTestSupport extends CamelTestSupport {

    static final Long COMMAND_TIMEOUT = 10000L;
    static final Long SERVICE_TIMEOUT = 30000L;

    // can further enhance this to auto discover the ports at run time
    // so they don't conflict with other tests/running versions of Fuse
    public static final String RMI_SERVER_PORT = "44445";
    public static final String HTTP_PORT = "9081";
    public static final String RMI_REG_PORT = "1100";

    ExecutorService executor = Executors.newCachedThreadPool();

    @Inject
    protected BundleContext bundleContext;

    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*,org.apache.felix.service.*;status=provisional");
        return probe;
    }

    public static Option[] baseContainer() {
        MavenArtifactUrlReference karafUrl = maven()
                .groupId("org.apache.karaf")
                .artifactId("apache-karaf")
                .version(karafVersion())
                .type("zip");

        return options(
                karafDistributionConfiguration()
                        .frameworkUrl(karafUrl)
                        .karafVersion(karafVersion())
                        .useDeployFolder(false)
                        .name("JBoss Fuse")
                        .unpackDirectory(new File("target/paxexam/unpack"))
                        // .runEmbedded(true)
                ,
                configureConsole().ignoreLocalConsole(),
                /*  From Byron
                replaceConfigurationFile("etc/org.ops4j.pax.url.mvn.cfg", new File("src/test/resources/org/sutterhealth/itest/karaf/org.ops4j.pax.url.mvn.cfg")),
                editConfigurationFilePut("etc/users.properties", "amq", "amq,admin"),
                */
//        editConfigurationFilePut("etc/config.properties", "karaf.startup.message", "Loading Fabric from: ${karaf.home}"),
//        editConfigurationFilePut("etc/org.ops4j.pax.web.cfg", "org.osgi.service.http.port", HTTP_PORT),
//        editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiRegistryPort", RMI_REG_PORT),
//        editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiServerPort", RMI_SERVER_PORT),


                // this is the key... we can install features, bundles, etc. using these pax-exam options
                features(maven().groupId("org.apache.camel.karaf").artifactId("apache-camel").type("xml").classifier("features").version("2.15.5"), "camel-blueprint"),
                features(maven().groupId("org.apache.camel.karaf").artifactId("apache-camel").type("xml").classifier("features").version("2.15.5"), "camel-test"),
                features(maven().groupId("org.apache.activemq").artifactId("activemq-karaf").type("xml").classifier("features").versionAsInProject(), "activemq-broker"),
                logLevel(LogLevelOption.LogLevel.INFO),

                // enable this if you want to keep the exploded directories of fuse after the tests are run
                keepRuntimeFolder()

        );

    }

    public static Option[] container(String groupId, String feature) {
        return combine(
                baseContainer(),
                features(maven().groupId(groupId).artifactId("features").type("xml").classifier("features").versionAsInProject(), feature)
        );
    }

    ;

    /**
     * Executes a shell command and returns output as a String.
     * Commands have a default timeout of 10 seconds.
     *
     * @param command
     * @return
     */
    protected String executeCommand(final String command) {
        return executeCommand(command, COMMAND_TIMEOUT, false);
    }

    /**
     * Executes a shell command and returns output as a String.
     * Commands have a default timeout of 10 seconds.
     *
     * @param command The command to execute.
     * @param timeout The amount of time in millis to wait for the command to execute.
     * @param silent  Specifies if the command should be displayed in the screen.
     * @return
     */
    protected String executeCommand(final String command, final Long timeout, final Boolean silent) {
        String response;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(byteArrayOutputStream);
        final CommandProcessor commandProcessor = getOsgiService(CommandProcessor.class);
        final CommandSession
                commandSession = commandProcessor.createSession(System.in, printStream, System.err);
        FutureTask<String> commandFuture = new FutureTask<String>(
                new Callable<String>() {
                    public String call() {
                        try {
                            if (!silent) {
                                System.err.println(command);
                            }
                            commandSession.execute(command);
                        } catch (Exception e) {
                            e.printStackTrace(System.err);
                        }
                        printStream.flush();
                        return byteArrayOutputStream.toString();
                    }
                });

        try {
            executor.submit(commandFuture);
            response = commandFuture.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            response = "SHELL COMMAND TIMED OUT: ";
        }

        return response;
    }

    protected <T> T getOsgiService(Class<T> type, long timeout) {
        return getOsgiService(type, null, timeout);
    }

    protected <T> T getOsgiService(Class<T> type) {
        return getOsgiService(type, null, SERVICE_TIMEOUT);
    }

    protected <T> T getOsgiService(Class<T> type, String filter, long timeout) {
        ServiceTracker tracker = null;
        try {
            String flt;
            if (filter != null) {
                if (filter.startsWith("(")) {
                    flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")" + filter + ")";
                } else {
                    flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")(" + filter + "))";
                }
            } else {
                flt = "(" + Constants.OBJECTCLASS + "=" + type.getName() + ")";
            }
            Filter osgiFilter = FrameworkUtil.createFilter(flt);
            tracker = new ServiceTracker(bundleContext, osgiFilter, null);
            tracker.open(true);
            // Note that the tracker is not closed to keep the reference
            // This is buggy, as the service reference may change i think
            Object svc = type.cast(tracker.waitForService(timeout));
            if (svc == null) {
                Dictionary dic = bundleContext.getBundle().getHeaders();
                System.err.println("Test bundle headers: " + explode(dic));

                for (ServiceReference ref : asCollection(bundleContext.getAllServiceReferences(null, null))) {
                    System.err.println("ServiceReference: " + ref);
                }

                for (ServiceReference ref : asCollection(bundleContext.getAllServiceReferences(null, flt))) {
                    System.err.println("Filtered ServiceReference: " + ref);
                }

                throw new RuntimeException("Gave up waiting for service " + flt);
            }
            return type.cast(svc);
        } catch (InvalidSyntaxException e) {
            throw new IllegalArgumentException("Invalid filter", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
  * Explode the dictionary into a ,-delimited list of key=value pairs
  */
    private static String explode(Dictionary dictionary) {
        Enumeration keys = dictionary.keys();
        StringBuffer result = new StringBuffer();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            result.append(String.format("%s=%s", key, dictionary.get(key)));
            if (keys.hasMoreElements()) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    /**
     * Provides an iterable collection of references, even if the original array is null
     */
    private static Collection<ServiceReference> asCollection(ServiceReference[] references) {
        return references != null ? Arrays.asList(references) : Collections.<ServiceReference>emptyList();
    }

    public static String karafVersion() {
        ConfigurationManager cm = new ConfigurationManager();
        String karafVersion = cm.getProperty("pax.exam.karaf.version", "2.4.0");
        return karafVersion;
    }
}

