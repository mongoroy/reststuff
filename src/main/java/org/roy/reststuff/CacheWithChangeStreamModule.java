package org.roy.reststuff;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.roy.reststuff.annotations.CacheWithChangeStream;
import org.roy.reststuff.interceptors.CacheWithChangeStreamInterceptor;

public class CacheWithChangeStreamModule extends AbstractModule {

  @Override
  protected void configure() {
    CacheWithChangeStreamInterceptor interceptor = new CacheWithChangeStreamInterceptor();
    requestInjection(interceptor);
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(CacheWithChangeStream.class), interceptor);
  }

}
