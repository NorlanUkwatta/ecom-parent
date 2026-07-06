package com.techmart.core.monitoring;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TrackPerformance
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class PerformanceInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("techmart.metrics.ejb");

    @AroundInvoke
    public Object profileMethod(InvocationContext ic) throws Exception {
        long startTime = System.currentTimeMillis();
        try {
            return ic.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            logger.warn("[EJB METRIC] {}.{} executed in {} ms",
                    ic.getTarget().getClass().getSimpleName(),
                    ic.getMethod().getName(),
                    executionTime);

            if (executionTime > 500) {
                logger.warn("[WARNING] EJB Bottleneck! {}.{} took {} ms",
                        ic.getTarget().getClass().getSimpleName(),
                        ic.getMethod().getName(),
                        executionTime);
            }
        }
    }
}
