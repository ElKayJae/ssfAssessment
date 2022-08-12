package com.vttp2022.ssfAssessment.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.vttp2022.ssfAssessment.model.Article;

@Service
public class NewsRedisService {
    private static final Logger logger = LoggerFactory.getLogger(NewsRedisService.class);

    @Autowired
    @Qualifier("fullListRedisConfig")
    RedisTemplate<String, Article> redisTemplate;


    public void save(final List<Article> articleList) {
        logger.info("saving...");
        for(Article article:articleList){
            redisTemplate.opsForValue().set(article.getId(),article);
        }
    }

    public Optional<Article> findById(final String id) {
        logger.info("find OrderList by id> " + id);
        try{Article result = (Article) redisTemplate.opsForValue().get(id);
            return Optional.of(result);
        }catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
