package com.vttp2022.ssfAssessment.model;

import java.io.IOException;


import jakarta.json.JsonObject;

public class Article {

    private String id;
    private String title;
    private String body;
    private String published_on;
    private String url;
    private String imageurl;
    private String tags;
    private String categories;
    private Boolean save=false;

    public Boolean getSave() {
        return save;
    }
    public void setSave(Boolean save) {
        this.save = save;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublished_on() {
        return published_on;
    }
    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    public static Article getArticle(JsonObject o ) throws IOException {

        Article article = new Article();
        article.setId(o.getString("id"));
        article.setPublished_on(o.getJsonNumber("published_on").toString());
        article.setTitle(o.getString("title"));
        article.setUrl(o.getString("url"));
        article.setImageurl(o.getString("imageurl"));
        article.setBody(o.getString("body"));
        article.setTags(o.getString("tags"));
        article.setCategories(o.getString("categories"));
    
        return article;
            
        }
        
    

}
