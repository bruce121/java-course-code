package com.bruce121._11cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @ClassName: RedisPubSubConfig
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 2022/1/16 9:55 PM
 * @Version 1.0
 */
@Configuration
public class RedisPubSubConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {

        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(redisConnectionFactory);

        return listenerContainer;
    }
}
