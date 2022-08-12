package com.vttp2022.ssfAssessment.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class FullList {
    private static final Logger logger = LoggerFactory.getLogger(FullList.class);

    private List<Article> fullList = new ArrayList<>();


    public List<Article> getFullList() {
        return fullList;
    }


    public void setFullList(List<Article> fullList) {
        this.fullList = fullList;
    }


    public static List<Article> getArticleList(String json) throws IOException {

        logger.info("get article list");
        FullList fullList = new FullList();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();
            // logger.info("JsonObject >>>>>> " + o);
            JsonArray dataArr = o.getJsonArray("Data");
            List<Article> articleList = new ArrayList<>();
            for (JsonValue data : dataArr){
                JsonObject dataObj = (JsonObject)data;
                Article article = Article.getArticle(dataObj);
                articleList.add(article);
            }
            fullList.setFullList(articleList);
            return fullList.getFullList();
            }
        }
    
}
