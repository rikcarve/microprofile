package ch.carve.microprofile.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.opentracing.ActiveSpan;
import io.opentracing.util.GlobalTracer;

@RequestScoped
public class HelloService {

    private Client client = ClientBuilder.newClient();

    @Inject
    @ConfigProperty(name = "hello-uri", defaultValue = "http://localhost:8080/hello")
    private String uri;

    // @Retry(maxRetries = 1)
    // @Bulkhead(5)
    // @Fallback(fallbackMethod = "fallback")
    public String getHello() {
        String response = null;
        try (ActiveSpan span = GlobalTracer.get().buildSpan("hello").startActive()) {
            response = client.target(uri).request().get(String.class);
        }
        return response;
    }

    private String fallback() {
        return "FALLBACK";
    }
}
