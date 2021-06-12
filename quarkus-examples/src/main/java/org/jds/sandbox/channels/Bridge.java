package org.jds.sandbox.channels;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

@ApplicationScoped
public class Bridge {

	private static final Logger LOGGER = Logger.getLogger(Bridge.class.getName());

	@Outgoing("texto-sent")
	@Incoming("texto")
	public String receive(String str) {
		LOGGER.infof("Received " + str);
		return "Received " + str;
	}

}