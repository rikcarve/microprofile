package ch.carve.microprofile.rest;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.uber.jaeger.Configuration;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

@WebListener
public class TracingContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        GlobalTracer.register(Configuration.fromEnv().getTracer());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Produces
    @Singleton
    public Tracer jaegerTracer() {
        return GlobalTracer.get();
    }
}
