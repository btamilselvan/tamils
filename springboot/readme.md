# Enable caching using redis
* Just adding @EnableCaching will enable default in built cache manager which is ConcurrentMapCacheManager
* Adding the "spring-boot-starter-data-redis" dependency will add support to redis cache manager
* Make sure to start the redis server before using redis cache manager (as a docker container)
* By default, redis will start in 6379 port and that will be the default port the application will connect to. This can be changed using custom configuration
* Jedis – a simple and powerful Redis client implementation
* spring-boot-starter-data-redis gives Lettuce dependency by default instead of Jedis. To use Jedis configuration, exclude Lettuce and add Jedis dependency
* redis server password can be set/changed using redis.conf file. That password should be used in the application to connect to redis. redis.conf can be used to enable more security features