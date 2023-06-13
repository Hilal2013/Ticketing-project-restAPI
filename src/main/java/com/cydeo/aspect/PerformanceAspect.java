package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
// I want to see the performance of my methods
    @Pointcut("@annotation(com.cydeo.annotation.ExecutionTime)")//create some custom annotation
    public void executionTimePC() {}
//Lets create an advice Around advice
    @Around("executionTimePC()")
    public Object aroundAnyExecutionTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) {

        long beforeTime = System.currentTimeMillis();
        Object result = null;
        log.info("Execution starts:");

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long afterTime = System.currentTimeMillis();

        log.info("Time taken to execute: {} ms - Method: {}"
                , (afterTime - beforeTime), proceedingJoinPoint.getSignature().toShortString());

        return result;

    }

}
