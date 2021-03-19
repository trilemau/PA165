package cz.muni.fi.pa165.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import javax.inject.Named;
import java.util.Arrays;

@Aspect
@Named
public class LoggingAspect {
    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        System.out.println("Calling method " + pjp.getSignature().getName() + " with parameters " + Arrays.asList(pjp.getArgs()));

        stopWatch.start();
        Object returnValue = pjp.proceed();
        stopWatch.stop();

        System.out.println("Calling method " + pjp.getSignature().getName() + " finished in " + stopWatch.getTotalTimeSeconds() + " seconds");

        return returnValue;
    }
}
