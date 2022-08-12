package com.vttp2022.ssfAssessment.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2022.ssfAssessment.model.Article;
import com.vttp2022.ssfAssessment.model.FullList;

@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Value("${crypto.compare.apikey}")
    String apiKey = System.getenv("CRYPTO_COMPARE_APIKEY");

    String NEWS_ARTICLE_URL ="https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    public Optional <List<Article>> getArticles(){
       
        logger.info("get articles");

        String url = UriComponentsBuilder.fromUriString(NEWS_ARTICLE_URL)
                                .toUriString();
        
        logger.info(url);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("ApiKey", apiKey);
            HttpEntity request = new HttpEntity<>(headers);
            resp = template.exchange(url, HttpMethod.GET, request, String.class);
            logger.info(resp.getBody());
            List<Article> articleList = FullList.getArticleList(resp.getBody());
            return Optional.of(articleList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } return Optional.empty();
    }
}
