package org.appa.planning.util;

import flex.messaging.FlexSession;

public class MyFlexSessionListener implements flex.messaging.FlexSessionListener {

	public void sessionCreated(FlexSession session) {
		session.addSessionDestroyedListener(this);
	}

	public void sessionDestroyed(FlexSession session) {
		FlexClientScope.removeCurrentUserContext();
	}

}
