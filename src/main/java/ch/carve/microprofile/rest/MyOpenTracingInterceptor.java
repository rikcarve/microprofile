package ch.carve.microprofile.rest;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.cdi.Traced;

@Traced
@Interceptor
public class MyOpenTracingInterceptor {

    @Inject
    private Tracer tracer;

    @AroundInvoke
    public Object wrap(InvocationContext ctx) throws Exception {
        System.out.println("Tracer interceptor: " + tracer);
        // try (Scope scope = tracer.buildSpan(ctx.getMethod().getName()).startActive(true)) {
        // return ctx.proceed();
        // }
        Span span = tracer.buildSpan(ctx.getMethod().getName()).start();
        Scope scope = tracer.scopeManager().activate(span, false);
        Object result = null;
        try {
            result = ctx.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            span.finish();
            scope.close();
        }
        return result;
    }
}
