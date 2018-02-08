package ch.carve.microprofile.rest;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import io.opentracing.contrib.cdi.Traced;
import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature;

public class HelloService {

    private Client client = ClientBuilder.newBuilder().register(ClientTracingFeature.class).build();

    @Inject
    @ConfigProperty(name = "hello-uri", defaultValue = "http://localhost:8080/hello")
    private String uri;

    @Retry(maxRetries = 1)
    @Bulkhead(5)
    @Fallback(fallbackMethod = "fallback")
    @Traced
    public String getHello() {
        String response = null;
        response = client.target(uri).request().get(String.class);
        return response;
    }

    private String fallback() {
        return "FALLBACK";
    }
}
