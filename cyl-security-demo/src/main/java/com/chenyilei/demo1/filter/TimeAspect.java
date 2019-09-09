package com.chenyilei.demo1.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/28- 14:25
 */
@Aspect
@Component
public class TimeAspect {

    @Pointcut("execution(* com.chenyilei.demo1.controller.UserController..*(..) )")
    public void pointCut1(){}

    @Around("pointCut1()")
    public Object TimeAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            System.out.println(joinPoint.getSignature().getName());
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            throw throwable;
        }finally {
            System.err.println("时间: "+(System.currentTimeMillis() - start) );
        }
    }

}
