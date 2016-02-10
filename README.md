# integration-deployment

install -s mvn:org.apache.commons/commons-lang3/3.4
install -s mvn:com.pronoia.camel.deployment/interfaces/2.0.0
install -s mvn:com.pronoia.camel.deployment.service/service-one/2.0.0-SNAPSHOT mvn:com.pronoia.camel.deployment.service/service-two/2.0.0-SNAPSHOT

install -s mvn:com.pronoia.camel.deployment.service/blueprint-event-listener/2.0.0-SNAPSHOT

install -s mvn:com.pronoia.camel.deployment/builders/2.0.0-SNAPSHOT
install mvn:com.pronoia.camel.deployment.integrations/EPICadESCRIPT_ip/2.0.0-SNAPSHOT mvn:com.pronoia.camel.deployment.affiliate/CP_EPICadESCRIPT_ip/2.0.0-SNAPSHOT
 
install mvn:com.pronoia.camel.deployment.affiliate/LS_EPICadESCRIPT_ip/2.0.0-SNAPSHOT mvn:com.pronoia.camel.deployment.affiliate/NV_EPICadESCRIPT_ip/2.0.0-SNAPSHOT mvn:com.pronoia.camel.deployment.affiliate/SR_EPICadESCRIPT_ip/2.0.0-SNAPSHOT
install mvn:com.pronoia.camel.deployment.affiliates.XX/XX_EPICadINGNMED_ip/2.0.0-SNAPSHOT