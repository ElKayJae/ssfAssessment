package com.vttp2022.ssfAssessment.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.vttp2022.ssfAssessment.model.Article;
import com.vttp2022.ssfAssessment.model.FullList;
import com.vttp2022.ssfAssessment.service.NewsRedisService;
import com.vttp2022.ssfAssessment.service.NewsService;


@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService service;

    @Autowired
    NewsRedisService redisService;

    @GetMapping("/")
    public String showIndexPage(Model model){
        List<Article> articleList = service.getArticles().get();
        FullList fullList = new FullList();
        fullList.setFullList(articleList);
        model.addAttribute("articleList",fullList);
        return "index";
    }

    @PostMapping("/articles")
    public String saveArticle(@ModelAttribute FullList articleList, Model model){
        logger.info("save articles");
        logger.info(articleList.getFullList().toString());
        List<Article> savedList = new ArrayList<>();
        for (Article item: articleList.getFullList()){
            logger.info("running");
            if (item.getSave()){
                savedList.add(item);
            }
        }
        logger.info("Saved List >>>>>> " + savedList);
        redisService.save(savedList);

        return "index";
    }
    
    
}
