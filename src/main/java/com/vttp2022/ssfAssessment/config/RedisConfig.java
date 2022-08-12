package com.vttp2022.ssfAssessment.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.vttp2022.ssfAssessment.model.Article;


@Configuration
public class RedisConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private String redisHost = System.getenv("REDIS_HOST");
    String redisPortString = System.getenv("REDIS_PORT");
    private Integer redisPort = Integer.parseInt(redisPortString);
    private String redisPassword = System.getenv("REDIS_PASSWORD") ;

    @Bean(name = "fullListRedisConfig")
    @Scope("singleton")
    public RedisTemplate<String, Article> redisTemplate() {
        //set up the configuration into RedisStandaloneConfiguration object
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        //need to have .get() as it is an Optional<Integer>
        config.setPort(redisPort);
        config.setPassword(redisPassword);
        
        //Jedis is java client for redis, create client and factory
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        logger.info("redis host port > {redisHost} {redisPort}", redisHost, redisPort);
        
        //create template
        RedisTemplate<String, Article> template = new RedisTemplate<String, Article>();
        template.setConnectionFactory(jedisFac);

        //all the serializers
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(Article.class);
        template.setValueSerializer(jackson2JsonJsonSerializer);
        return template;
    }
    
}
