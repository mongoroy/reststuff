package org.roy.reststuff.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheWithChangeStreamInterceptor implements MethodInterceptor {
  final Logger logger = LoggerFactory.getLogger(CacheWithChangeStreamInterceptor.class);

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    logger.info("sup");
    return methodInvocation.proceed();
  }


}
