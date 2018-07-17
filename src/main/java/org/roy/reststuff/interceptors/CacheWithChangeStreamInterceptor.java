package org.roy.reststuff.interceptors;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.roy.reststuff.annotations.CacheWithChangeStream;
import org.roy.reststuff.cache.CacheIdentifierKey;
import org.roy.reststuff.cache.DocumentIdentifierKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheWithChangeStreamInterceptor implements MethodInterceptor {
  final Logger logger = LoggerFactory.getLogger(CacheWithChangeStreamInterceptor.class);

  private static Map<CacheIdentifierKey, Cache<DocumentIdentifierKey, Object>> cacheMap = new HashMap<>();

  @Inject
  private MongoClient mongoClient;

  private static ExecutorService threadExecutor = null;

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    if (threadExecutor == null) {
      threadExecutor = Executors.newSingleThreadExecutor();
      threadExecutor.submit(() -> {
        mongoClient.watch().forEach((Block<ChangeStreamDocument<Document>>) b -> {
          System.out.println(b);
        });
      });
    }
    logger.info("CacheMap size: " + cacheMap.size());

    CacheWithChangeStream annotation = getCacheWithChangeStreamAnnotation(methodInvocation);
    CacheIdentifierKey cacheKey = new CacheIdentifierKey(getDatabase(annotation) + "." + getCollection(annotation));
    logger.info("Cache Identifier key: " + cacheKey);
    Cache<DocumentIdentifierKey, Object> cache = cacheMap.computeIfAbsent(cacheKey, k -> {
      return CacheBuilder.newBuilder()
          .maximumSize(1000)
          .<DocumentIdentifierKey, Object>build();
    });
    logger.info("Cache size: " + cache.size());
    DocumentIdentifierKey key = getDocumentIdentifierKey(methodInvocation);
    logger.info("Document key: " + key);
    Object object = cache.getIfPresent(key);
    if (object != null) {
      logger.info("Found object from cache: " + object);
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
