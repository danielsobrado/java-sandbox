package org.jds.sandbox.channels;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/test")
@ApplicationScoped
public class MainWeb {

	@Inject
	SlowApplication app;
	
	@Inject
	Bridge bridge;
	
	@Inject @Channel("texto-sent") public Multi<String> textos;
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String hello() {
		return "hello";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/custom")
	public Uni<String> custom() {
		return Uni.createFrom().item(app.slowProcess(4)).onItem().transform(n -> String.format("Finished %s", n));
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/stream")
	public Multi<String> stream() {
		return textos;
	}

}