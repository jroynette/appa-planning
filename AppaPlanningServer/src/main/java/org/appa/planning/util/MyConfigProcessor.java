package org.appa.planning.util;

import org.springframework.flex.config.MessageBrokerConfigProcessor;

import flex.messaging.MessageBroker;
import flex.messaging.client.FlexClient;

public class MyConfigProcessor implements MessageBrokerConfigProcessor{

	public MessageBroker processAfterStartup(MessageBroker broker) {
		FlexClient.addClientCreatedListener(new MyFlexClientListener());
		return broker;
	}

	public MessageBroker processBeforeStartup(MessageBroker broker) {
		// TODO Auto-generated method stub
		return broker;
	}

}
