package org.roy.reststuff.interceptors;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.client.MongoClient;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.bson.types.ObjectId;
import org.roy.reststuff.annotations.CacheWithChangeStream;
import org.roy.reststuff.cache.CacheIdentifierKey;
import org.roy.reststuff.cache.CacheWatcher;
import org.roy.reststuff.cache.CacheWatcherManager;
import org.roy.reststuff.cache.DocumentIdentifierKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheWithChangeStreamInterceptor implements MethodInterceptor {
  final Logger logger = LoggerFactory.getLogger(CacheWithChangeStreamInterceptor.class);

  private static Map<CacheIdentifierKey, Cache<DocumentIdentifierKey, Object>> cacheMap = new HashMap<>();

  @Inject
  private MongoClient mongoClient;

  private CacheWatcherManager cacheWatcherManager = new CacheWatcherManager();

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    Class pojoClass = methodInvocation.getMethod().getReturnType();
    CacheWithChangeStream annotation = getCacheWithChangeStreamAnnotation(methodInvocation);
    String db = getDatabase(annotation);
    String col = getCollection(annotation);
    CacheIdentifierKey cacheKey = new CacheIdentifierKey(db + "." + col);
    Cache<DocumentIdentifierKey, Object> cache = cacheMap.computeIfAbsent(cacheKey, k -> {
      logger.info("Creating new cache: {}", cacheKey);
      Cache<DocumentIdentifierKey, Object> c = CacheBuilder.newBuilder()
          .maximumSize(1000)
          .<DocumentIdentifierKey, Object>build();
      cacheWatcherManager.addCacheWatcher(new CacheWatcher(mongoClient, db, col, pojoClass, c));
      return c;
    });
    DocumentIdentifierKey key = getDocumentIdentifierKey(methodInvocation);

    Object object = cache.getIfPresent(key);
    if (object != null) {
      logger.info("Found {} from cache: {}", pojoClass, object);
      return object;
    }

    object = methodInvocation.proceed();
    cache.put(key, object);
    return object;
  }

  DocumentIdentifierKey getDocumentIdentifierKey(MethodInvocation methodInvocation) {
    return new DocumentIdentifierKey((ObjectId)methodInvocation.getArguments()[0]);
  }

  String getDatabase(CacheWithChangeStream cacheWithChangeStream) {
    return cacheWithChangeStream.database();
  }

  String getCollection(CacheWithChangeStream cacheWithChangeStream) {
    return cacheWithChangeStream.collection();
  }

  private CacheWithChangeStream getCacheWithChangeStreamAnnotation(MethodInvocation methodInvocation) {
    return methodInvocation.getMethod().getAnnotation(CacheWithChangeStream.class);
  }


}
