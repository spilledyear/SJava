package com.zto.sxy.spring.aop2;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP 的 helloWorld
 * 1. 加入 jar 包
 * com.springsource.net.sf.cglib-2.2.0.jar
 * com.springsource.org.aopalliance-1.0.0.jar
 * com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
 * spring-aspects-4.0.0.RELEASE.jar
 *
 * 2. 在 Spring 的配置文件中加入 aop2 的命名空间。
 *
 * 3. 基于注解的方式来使用 AOP
 * 3.1 在配置文件中配置自动扫描的包: <context:component-scan base-package="com.xt.beans.aop2"></context:component-scan>
 * 3.2 加入使 AspjectJ 注解起作用的配置: <aop2:aspectj-autoproxy></aop2:aspectj-autoproxy>
 * 为匹配的类自动生成动态代理对象.
 *
 * 4. 编写切面类:
 * 4.1 一个一般的 Java 类
 * 4.2 在其中添加要额外实现的功能.
 *
 * 5. 配置切面
 * 5.1 切面必须是 IOC 中的 bean: 实际添加了 @Component 注解
 * 5.2 声明是一个切面: 添加 @Aspect
 * 5.3 声明通知: 即额外加入功能对应的方法.
 * 5.3.1 前置通知: @Before("execution(public int com.xt.beans.aop2.ArithmeticCalculator.*(int, int))")
 * @Before 表示在目标方法执行之前执行 @Before 标记的方法的方法体.
 * @Before 里面的是切入点表达式:
 *
 * 6. 在通知中访问连接细节: 可以在通知方法中添加 JoinPoint 类型的参数, 从中可以访问到方法的签名和方法的参数.
 *
 * 7. @Before:前置通知，在方法执行之前执行
 * 	  @After 表示后置通知: 在方法执行之后执行的代码
 * 						是在连接点完成之后执行的，即连接点返回结果或者抛出异常的时候
 * 	  @AfterRunning:返回通知，在方法返回结果之后执行
 * 					无论连接点是否正常返回还是抛出异常，后置通知都会执行
 * 					如果只想在连接点返回的时候记录日志，应使用返回通知代替后置通知
 * 	  @AfterThrowing:异常通知，在方法抛出异常之后
 * 	  @Around:环绕通知，围绕着方法执行
 *
 */


//通过添加 @Aspect 注解声明一个 bean 是一个切面!
@Aspect
@Component
public class LoggingAspect {

	@Before("execution(public int com.zto.sxy.spring.aop2.ArithmeticCalculator.*(int, int))")
	public void beforMethod(JoinPoint joinPoint){
		System.out.println("当前执行的方法："+joinPoint.getSignature().getName()
				+"，执行参数："+Arrays.toString(joinPoint.getArgs()));
	}

	//拦截所有的类，方法，参数
	@After("execution(* *.*(..))")
	public void afterMethod(JoinPoint joinPoint){
		System.out.println("日志信息记录值：1.执行方法："+joinPoint.getSignature().getName()
				+",2.执行参数："+Arrays.toString(joinPoint.getArgs())
				+",3.执行时间："+new SimpleDateFormat("yyyy年-MM年-dd日 hh:mm:ss").format(new Date()));
	}

	@Pointcut("execution(* *.*(..))")
	public void pointCut(){}

	//切入点表达式可以通过操作符 && || ! 结合起来
	@AfterReturning("pointCut()")
	public void afterRunningMethod(JoinPoint joinPoint){
		System.out.println("这个是在方法返回结果之后执行");
	}

//	@AfterThrowing("pointCut()")
//	public void afterThrowing(JoinPoint joinPoint,Exception e){
//		System.out.println("一个错误"+e+"在执行方法"+joinPoint.getSignature().getName()+"()时候抛出！");
//	}



}
