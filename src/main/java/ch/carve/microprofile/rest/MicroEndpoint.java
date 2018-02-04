package ch.carve.microprofile.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/micro")
public class MicroEndpoint {

    @Inject
    private HelloService hello;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        return Response.ok(hello.getHello()).build();
    }
}