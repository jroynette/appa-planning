package org.appa.planning.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.core.MessageInterceptor;
import org.springframework.flex.core.MessageProcessingContext;
import org.springframework.security.core.context.SecurityContextHolder;

import flex.messaging.FlexContext;
import flex.messaging.messages.Message;

public class MyMessageInterceptor implements MessageInterceptor{

	@Autowired
	private UserContext userContext;

	public Message postProcess(MessageProcessingContext context, Message inputMessage, Message outputMessage) {
		return outputMessage;
	}

	public Message preProcess(MessageProcessingContext context, Message inputMessage) {

		if(FlexContext.getFlexClient().getId() != null && SecurityContextHolder.getContext().getAuthentication() == null && userContext.getAuthentication() != null){
			SecurityContextHolder.getContext().setAuthentication(userContext.getAuthentication());
		}
		return inputMessage;
	}

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}
}
