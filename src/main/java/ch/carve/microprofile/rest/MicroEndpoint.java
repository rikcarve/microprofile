package ch.carve.microprofile.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/micro")
public class MicroEndpoint {

    @Inject
    @ConfigProperty(name = "hello-suffix", defaultValue = "world")
    private String suffix;

    @Inject
    private HelloServiceGateway hello;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        return Response.ok(hello.getHello() + suffix).build();
    }
}
