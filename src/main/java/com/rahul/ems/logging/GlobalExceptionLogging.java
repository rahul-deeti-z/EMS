package com.rahul.ems.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class GlobalExceptionLogging {
    @AfterThrowing(pointcut = "execution(* com.rahul.ems..*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("Exception in {}: {}", methodName, exception.getMessage());
    }
}
