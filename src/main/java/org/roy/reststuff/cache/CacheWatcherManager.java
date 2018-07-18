package org.roy.reststuff.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;

@Singleton
public class CacheWatcherManager {
  private  ExecutorService threadExecutor = Executors.newCachedThreadPool();

  public void addCacheWatcher(CacheWatcher watcher) {
    threadExecutor.execute(watcher);
  }
}
