package org.appa.planning.util;

import flex.messaging.client.FlexClient;
import flex.messaging.client.FlexClientListener;

public class MyFlexClientListener implements FlexClientListener{

	public void clientCreated(FlexClient client) {
		client.addClientDestroyedListener(this);
	}

	public void clientDestroyed(FlexClient client) {
		//FlexClientScope.removeCurrentUserContext(client.getId());
	}

}
