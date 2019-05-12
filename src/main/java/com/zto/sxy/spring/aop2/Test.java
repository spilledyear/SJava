package com.zto.sxy.spring.aop2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

		ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculatorImpl");

		System.out.println(arithmeticCalculator.getClass().getName());

		int result = arithmeticCalculator.add(11, 12);
		System.out.println("result:" + result);

		result = arithmeticCalculator.div(21, 3);
		System.out.println("result:" + result);

		result = arithmeticCalculator.addThree(15, 3, 9);
		System.out.println("result:" + result);

		/**
		 * com.sun.proxy.$Proxy8
		 当前执行的方法：add，执行参数：[11, 12]
		 日志信息记录值：1.执行方法：add,2.执行参数：[11, 12],3.执行时间：2016年-03年-18日 10:32:20
		 这个是在方法返回结果之后执行
		 result:23

		 当前执行的方法：div，执行参数：[21, 3]
		 日志信息记录值：1.执行方法：div,2.执行参数：[21, 3],3.执行时间：2016年-03年-18日 10:32:20
		 这个是在方法返回结果之后执行
		 result:7

		 日志信息记录值：1.执行方法：addThree,2.执行参数：[15, 3, 9],3.执行时间：2016年-03年-18日 10:32:20
		 这个是在方法返回结果之后执行
		 result:27
		 */
	}

}
