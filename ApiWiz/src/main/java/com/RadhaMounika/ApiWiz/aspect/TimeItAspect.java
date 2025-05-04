package com.RadhaMounika.ApiWiz.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeItAspect {
    @Around("@annotation(LogTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long initTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - initTime;
        System.out.println("============================================================================================================");
        System.out.println("Method Signature is : " + joinPoint.getSignature());
        System.out.println("Method executed in : " + executionTime + "ms");
        return proceed;
    }
}
