# integration-deployment

install -s mvn:org.apache.commons/commons-lang3/3.4
install -s mvn:com.pronoia.camel.deployment/interfaces/1.0.0-SNAPSHOT mvn:com.pronoia.camel.deployment.service/service-one/1.0.0-SNAPSHOT mvn:com.pronoia.camel.deployment.service/service-two/1.0.0-SNAPSHOT

install -s mvn:com.pronoia.camel.deployment.service/blueprint-event-listener/1.0.0-SNAPSHOT

install -s mvn:com.pronoia.camel.deployment/builders/1.0.0-SNAPSHOT
install mvn:com.pronoia.camel.deployment.integrations/EPICadINGNMED_ip/1.0.0-SNAPSHOT mvn:com.pronoia.camel.deployment.affiliates.CP/CP_EPICadINGNMED_ip/1.0.0-SNAPSHOT
install mvn:com.pronoia.camel.deployment.affiliates.XX/XX_EPICadINGNMED_ip/1.0.0-SNAPSHOT