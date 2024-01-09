package com.metanet.finalproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
	// LoginController AOP -> After
	@After("execution(* com.metanet.finalproject..LoginController.*(..))")
	public void loginAfterLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-After Log]]-{}", methodName);
	}
		
	// LoginController AOP -> After Throwing
	@AfterThrowing(pointcut="execution(* com.metanet.finalproject..LoginController.*(..))", throwing="exception")
	public void loginAfterThrowing(JoinPoint joinPoint, Exception exception) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-Exception Log]]-{}", methodName, exception.getMessage());
	}
	
	// MemberController AOP -> After
	@After("execution(* com.metanet.finalproject..MemberController.*(..))")
	public void memberAfterLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-After Log]]-{}", methodName);
	}
	
	// MemberController AOP -> After Throwing
	@AfterThrowing(pointcut="execution(* com.metanet.finalproject..MemberController.*(..))", throwing="exception")
	public void memberAfterThrowing(JoinPoint joinPoint, Exception exception) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-Exception Log]]-{}", methodName, exception.getMessage());
	}
	
	// KakaoController AOP -> After
	@After("execution(* com.metanet.finalproject..KakaoController.*(..))")
	public void kakaoAfterLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-After Log]]-{}", methodName);
	}
		
	// KakaoController AOP -> After Throwing
	@AfterThrowing(pointcut="execution(* com.metanet.finalproject..KakaoController.*(..))", throwing="exception")
	public void kakaoAfterThrowing(JoinPoint joinPoint, Exception exception) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-Exception Log]]-{}", methodName, exception.getMessage());
	}
	
	// PayController AOP -> After
	@After("execution(* com.metanet.finalproject..PayController.*(..))")
	public void payAfterLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-After Log]]-{}", methodName);
	}
		
	// PayController AOP -> After Throwing
	@AfterThrowing(pointcut="execution(* com.metanet.finalproject..PayController.*(..))", throwing="exception")
	public void payAfterThrowing(JoinPoint joinPoint, Exception exception) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		log.info("[[AOP-Exception Log]]-{}", methodName, exception.getMessage());
	}
}
