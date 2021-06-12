package org.jds.sandbox.channels;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SlowApplication {
	
	private static final Logger LOGGER = Logger.getLogger(SlowApplication.class.getName());
	
	@Inject @Channel("texto") public Emitter<String> emitter;
	
	public Integer slowProcess(int num) {
		for (int n=0;n<num;n++) {
			LOGGER.info("Start Num: "+n);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			emitter.send("Number is: "+n);
			LOGGER.info("End Num: "+n);
		}
		return num;
	}
	
}
