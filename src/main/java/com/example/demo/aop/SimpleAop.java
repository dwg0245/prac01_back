package com.example.demo.aop;



import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

// 테스트 할때만 적용, 운영 환경에서는 제거를 하고 배포


@Aspect // 관점 : 흩어진 관심사를 하나로 묶은 것
@Component  // 객체가 만들어져서 실행
@RequiredArgsConstructor
public class SimpleAop {
    private final Tracer tracer;
    // 언제 실행이 될때
    // 실행될 위치나 시점을 지정
    //      * 리턴 타입
    //      com.example.demo.board.. 해당 패키지 및 하위 모든 패키지
    //      *.*(..) 모든 클래스의 모든 메소드 및 모든 매개변수, (.하고 메소드 실행 문법이라고 생각)
    @Pointcut("execution( * com.example.demo.board..*.*(..))") // 위치 지정
    // 위치 지정을 한게 너무 길어서 cut() 이렇게 쓰려고 만든거임
    private void cut(){ // 포인트 컷을 적용할 이름을 실행
//        System.out.println("컷"); // 아무 의미 없음
    }

    // 시작 할때 끝날때 전부 포함
    @Around("cut()")
    public Object traceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // 실행되는 메서드 이름과 클래스 이름을 가져옵니다.
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();

        // 1. 새로운 Span 생성 (클래스명.메서드명 형식)
        Span span = tracer.spanBuilder(className + "." + methodName).startSpan();

        // 2. 해당 Span을 현재 실행 컨텍스트로 설정 (try-with-resources 사용으로 자동 close)
        try (Scope scope = span.makeCurrent()) {
            // 속성(Attribute) 추가: Jaeger UI에서 검색 조건으로 활용 가능
            span.setAttribute("method.name", methodName);

            // 실제 비즈니스 로직 실행
            return joinPoint.proceed();
        } catch (Exception e) {
            // 3. 에러 발생 시 Span에 예외 정보 기록
            span.recordException(e);
            throw e;
        } finally {
            // 4. 반드시 Span을 종료해야 Jaeger로 데이터가 전송됩니다.
            span.end();
        }
    }

    // @Before("execution( * com.example.demo.board..*.*(..))") // PointCut을 안 만들어두면 이렇게 설정해야 됨
    // 실행 전
    @Before("cut()") // 포인트 컷에서 지정한 위치의 클래스의 메소드가 실행된 후에 현재 메소드 실행
    public void before(JoinPoint joinPoint){ // JoinPoint : 위치나 시점, 특정 클래스, 특정 메소드라는 정보
        // 메소드 이름 출력
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();

        System.out.println(joinPoint.getSignature());
        System.out.println(method.getName()+"메소드 실행 전");

    }

    // 실행 후
    @After("cut()")
    public void after(JoinPoint joinPoint){ // JoinPoint : 위치나 시점, 특정 클래스, 특정 메소드라는 정보
        // 메소드 이름 출력
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();

        System.out.println(joinPoint.getSignature());
        System.out.println(method.getName()+"메소드 실행 후");

    }
}
