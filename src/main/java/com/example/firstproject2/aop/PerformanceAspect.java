package com.example.firstproject2.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    // 특정 어노테이션을 대상 지정
    @Pointcut("@annotation(com.example.firstproject2.annotation.RunningTime)")
    private void enableRunningTime() {}

    // 기본 패키지의 모든 메서드 지정
    @Pointcut("execution(* com.example.firstproject2..*.*(..))")
    private void cut() {}

    @Around("cut() && enableRunningTime()") // 기본 패지키의 모든 메서드와 특정 어노테이션 대상 실행 전후로 부가 기능 삽입
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
                                // @Around 를 통할때 JoinPoint는 조금 특별. (ProceedingJoinPoint : 대상을 실행까지 할 수 있는 joinpoint)

        // 메소드 수행 전 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 메소드 수행
        Object returningOnj = joinPoint.proceed();

        // 메소드 수행 후 측정 종료 및 로깅
        stopWatch.stop();
        String methodName = joinPoint.getSignature()
                        .getName();
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());

    }

}
