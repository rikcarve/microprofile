package ch.carve.microprofile.rest;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import io.opentracing.ActiveSpan;
import io.opentracing.Tracer;
import io.opentracing.Tracer.SpanBuilder;

@Traced
@Interceptor
public class MyOpenTracingInterceptor {

    @Inject
    private Tracer tracer;

    @AroundInvoke
    public Object wrap(InvocationContext ctx) throws Exception {
        System.out.println("Tracer interceptor: " + tracer);
        // Tracer tracer = GlobalTracer.get();
        SpanBuilder spanBuilder = tracer.buildSpan(ctx.getMethod().getName());
        try (ActiveSpan span = spanBuilder.startActive()) {
            return ctx.proceed();
        }
    }
}
