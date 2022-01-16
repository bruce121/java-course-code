package com.bruce121._11cache.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

/**
 * @ClassName: RedissonConfig
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 2022/1/16 9:47 PM
 * @Version 1.0
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {

        Config config = new Config();
        config.useSingleServer().setAddress(format("redis://%s:%s", redisProperties.getHost(), redisProperties.getPort()))
                .setDatabase(redisProperties.getDatabase())
                .setPassword(redisProperties.getPassword())
                .setClientName("REDISSON_CLIENT_NAME");

        return Redisson.create(config);
    }
}
