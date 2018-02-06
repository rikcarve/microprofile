package ch.carve.microprofile.rest;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.opentracing.ActiveSpan;
import io.opentracing.Tracer;
import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature;
import io.opentracing.util.GlobalTracer;

public class HelloService {

    private Client client = ClientBuilder.newBuilder().register(ClientTracingFeature.class).build();

    @Inject
    @ConfigProperty(name = "hello-uri", defaultValue = "http://localhost:8080/hello")
    private String uri;

    // @Retry(maxRetries = 1)
    // @Bulkhead(5)
    // @Fallback(fallbackMethod = "fallback")
    @Traced
    public String getHello() {
        Tracer tracer = GlobalTracer.get();
        try (ActiveSpan scope = tracer.buildSpan("hello2").startActive()) {
            return client.target(uri).request().get(String.class);
        }
    }

    private String fallback() {
        return "FALLBACK";
    }
}
