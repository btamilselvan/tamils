package com.success.springboot.handlers;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class MyCustomCacheErrorHandler implements CacheErrorHandler {

  @Override
  public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {}

  @Override
  public void handleCachePutError(
      RuntimeException exception, Cache cache, Object key, Object value) {}

  @Override
  public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {}

  @Override
  public void handleCacheClearError(RuntimeException exception, Cache cache) {}
}
