package com.spring.hibernate.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {
	
	//setup logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	//setup pointcut declarations
	@Pointcut("execution(* com.spring.hibernate.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.spring.hibernate.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.spring.hibernate.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	//@Before advice - execute before target method execution
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		//display method being called
		String method = joinPoint.getSignature().toShortString();
		logger.info("====> @Before: " + "calling method: " + method);
		
		//get args
		Object[] args = joinPoint.getArgs();
		
		//loop through and display args
		for(Object arg: args) {
			logger.info("====> @Before: " + "argument: " + arg);
		}
	}
	
	
	//@AfterReturning advice - executes after successful target method execution
	@AfterReturning(pointcut="forAppFlow()", returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		//display method being called
		String method = joinPoint.getSignature().toShortString();
		logger.info("====> @AfterReturning: " + "calling method: " + method);
		
		//display data returned
		logger.info("====> @AfterReturning: " + "data returned: " + result);
			
	}

}
