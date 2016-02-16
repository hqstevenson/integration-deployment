# integration-deployment

# Setup a new Karaf instance
karaf@root> features:chooseurl camel 2.15.5
karaf@root> features:install camel

# Install interfaces
install -s mvn:com.pronoia.deployment.interfaces/osgi-services/2.0.0 mvn:com.pronoia.deployment.interfaces/builder-support/2.0.0

# Install supporting services
install -s mvn:com.pronoia.deployment.osgi/service-one/2.0.0-SNAPSHOT mvn:com.pronoia.deployment.osgi/service-two/2.0.0-SNAPSHOT

# Install common builders
install -s mvn:com.pronoia.deployment.camel/builders/2.0.0-SNAPSHOT

# Install an integration - don't start it until the fragments have been installed
install mvn:com.pronoia.deployment.camel.integrations.EPICadESCRIPT_ip/beans/2.0.0-SNAPSHOT 

# Install a fragment - then the integration can be started
install mvn:com.pronoia.deployment.camel.affiliate/CP_EPICadESCRIPT_ip/2.0.0-SNAPSHOT

# Alternate - install the host and the fragement at the same time
install mvn:com.pronoia.deployment.camel.integrations/EPICadESCRIPT_ip/2.0.0-SNAPSHOT mvn:com.pronoia.deployment.camel.affiliate/CP_EPICadESCRIPT_ip/2.0.0-SNAPSHOT
 
install mvn:com.pronoia.deployment.camel.affiliate/LS_EPICadESCRIPT_ip/2.0.0-SNAPSHOT mvn:com.pronoia.deployment.camel.affiliate/NV_EPICadESCRIPT_ip/2.0.0-SNAPSHOT mvn:com.pronoia.deployment.camel.affiliate/SR_EPICadESCRIPT_ip/2.0.0-SNAPSHOT
install mvn:com.pronoia.deployment.camel.affiliates.XX/XX_EPICadINGNMED_ip/2.0.0-SNAPSHOT

TODO:  
 - add dummy data format
 - add JMS source and destination
 - add tests for failures of each