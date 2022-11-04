package com.example.firstproject2.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect     // AOP Class 선언 : 부가 기능을 주입하는 클래스
@Component      // IoC 컨테이너가 해당 객체를 생성/관리
@Slf4j
public class DebuggingAspect {

    // 대상 메소드 선택 : CommentService#create() >> CommentService의 create() 메서드 지칭
//    @Pointcut("execution(* com.example.firstproject2.service.CommentService.create(..))")
    @Pointcut("execution(* com.example.firstproject2..*.*(..))")
    private void cut() {} // 경로와 메서드를 위와 같이 지정. create(..) 안에 '..' 은 파라미터가 무엇이든 적용하겠다는 뜻.
                          // + 맨 앞에 '*' 는 public 과 return 타입을 주는건데 일단 * 로 시작하자.

    // 실행 시점 설정. "@Pointcut의 대상(cut())이 지정한 메소드(create())가 실행되기 전에 부가 기능을 수행하겠다는 어노테이션."
    @Before("cut()")
    public void loggingArgs(JoinPoint joinPoint) {
                        // 기본 파라미터 (JoinPoint) : cut()의 대상 메소드 "정확히는 메소드를 둘러싼 결합 지점인데, 일단 저렇게 알아두자"

        // 입력값 가져오기
        Object[] args = joinPoint.getArgs();     // 대상 메서드 부근에서 Arguments를 가져온다. >> Object의 배열을 Return하게 됨

        // 클래스명     joinPoint 내 메서드인 getTarget을 지정하여 Class를 가져오고 그 이름을 가져오겠다.
        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature()
                .getName();


        // 입력값 로깅하기 >> 위에서 getArgs() 로 가져온 값들을 반복문을 돌려보자
        // CommentService#create()의 입력값 => 5
        // CommentService#create()의 입력값 => CommentDto(id=null, ...)
        for (Object obj : args) {
            log.info("{}#{}의 입력값 => {}", className, methodName, obj);
        }
    }


    // 실행 시점 설정 : cut()에 지정된 대상 호출 성공 후 아래 AOP를 실행하겠다.
    @AfterReturning(value="cut()", returning = "returnObj")
    public void loggingReturnValue(JoinPoint joinPoint,     // cut()의 대상 메서드 (정도로 이해)
                                   Object returnObj) {      // Return 값 >> 대상 메서드가 수행되고나면 리턴값 나오잖아, 그 리턴값을 여기 AOP로 받아올 수 있다!
                                                            // + 위 @ 에 그 리턴값을 받기 위해 이름을 지정해줘야함 (returning="")

        // 클래스명
        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature()
                .getName();


        // 반환값 로깅
        // CommentService#create()의 반환값 => CommentDto(id=5, ...)
        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj);

    }
}
