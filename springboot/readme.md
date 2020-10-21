# Enable caching using redis
* Just adding @EnableCaching will enable default built-in cache manager which is ConcurrentMapCacheManager
* Adding the "spring-boot-starter-data-redis" dependency will add support to redis cache manager
* Make sure to start the redis server before using redis cache manager (as a docker container)
* By default, redis will listen to 6379 port and that will be the default port the application will connect to. This can be changed using custom configuration
* Jedis – a Redis client implementation
* spring-boot-starter-data-redis gives Lettuce dependency by default instead of Jedis. To use Jedis configuration, exclude Lettuce and add Jedis dependency
* redis connection password can be set/changed using redis.conf file. That password should be used in the application to connect to redis. redis.conf can be used to enable more security features
* CacheErrorHandler can be configured to handle the cache server (redis) outage. Using this, spring will allow the requests go thru the usual cycle without caching. When the cache server is back online, spring will automatically start using the cache
* spring will try to use the CacheManager to get the cache entry, which will fail when the cache is down. The CacheErrorHandler will intercept this error, and one of the handleCache****Errors would be invoked. If you don't take any action in these methods, then the application will go ahead and serve the request without failing or throwing an exception
* To load common properties from config-server, using git -> add application.properties file. using local directory -> add a file something like common.properties in config directory and use this name in the config-client's bootstrap.yml config server configuration, name: ${spring.application.name},common
* Add actuator dependency to enable actuator and use @RefreshScope to refresh config changes. Make sure to enable refresh end-point using management.endpoints.web.exposure.include=*