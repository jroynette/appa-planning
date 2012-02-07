package org.appa.planning.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import flex.messaging.FlexContext;

public class FlexClientScope implements Scope{

	private static Map<String,Object> objects = new HashMap<String,Object>();

	public String getConversationId() {
		return FlexContext.getFlexClient().getId();
	}

	public Object get(String key, ObjectFactory<?> objectFactory) {

		if(FlexContext.getFlexClient() == null){
			return null;
		}

		Object bean = objects.get(getConversationId() + "-" +  key);
		if (bean == null) {
			bean = objectFactory.getObject();
			objects.put(getConversationId() + "-" +  key, bean);
		}
		return bean;
	}

	public Object resolveContextualObject(String key) {
		return objects.get(getConversationId() + "-" +  key);
	}

	public void registerDestructionCallback(String key, Runnable callback) {
		//TODO à voir

	}

	public Object remove(String key) {
		return objects.remove(getConversationId() + "-" +  key);
	}

	public static void removeCurrentUserContext() {
		objects.remove(FlexContext.getFlexClient().getId() + "-scopedTarget.currentUserContext");
	}

	public static void removeCurrentUserContext(String flexClientId) {
		objects.remove(flexClientId + "-scopedTarget.currentUserContext");
	}
}
