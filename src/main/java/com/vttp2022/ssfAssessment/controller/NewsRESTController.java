package com.vttp2022.ssfAssessment.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vttp2022.ssfAssessment.model.Article;
import com.vttp2022.ssfAssessment.service.NewsRedisService;
import com.vttp2022.ssfAssessment.service.NewsService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class NewsRESTController {
    private static final Logger logger = LoggerFactory.getLogger(NewsRESTController.class);
    
    @Autowired
    NewsService service;

    @Autowired
    NewsRedisService redisService;

    @Autowired
    @Qualifier("fullListRedisConfig")
    RedisTemplate<String, Article> redisTemplate;


    
    @GetMapping (path="/news/{id}", consumes =MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<?> getArticlebyId(@PathVariable String id){
     
        logger.info("get" + id);
            Optional<Article> optArticle = redisService.findById(id);

            if(optArticle.isEmpty()){
            JsonObject errJson = Json.createObjectBuilder()
            .add("error","Cannot find news article " + id)
            .build();
            return ResponseEntity.status(404).body(errJson.toString());
            
        }
        
        return ResponseEntity.ok(optArticle.get());
    }


    @GetMapping ("/savelist")
    public ResponseEntity<String> saveArticle(){
        List<Article> articleList = service.getArticles().get();
        redisService.save(articleList);
        return ResponseEntity.ok(articleList.toString());
    }
}
