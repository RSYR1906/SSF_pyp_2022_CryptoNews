package com.sg.iss.nus.SSF_PYP_June_2022.model;

import java.util.Date;

public class Article {

    private Integer id;

    private Long publishedDate;

    private String title;

    private String url;

    private String imageUrl;

    private String body;

    private String tags;

    private String categories;

    public Article() {
    }

    public Article(Integer id, Long publishedDate, String title, String url, String imageUrl, String body,
            String tags, String categories) {
        this.id = id;
        this.publishedDate = publishedDate;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.body = body;
        this.tags = tags;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Long publishedDate) {
        this.publishedDate = publishedDate;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        return id + "," + publishedDate + "," + title + "," + url
                + "," + imageUrl + "," + body + "," + tags + "," + categories;
    }

}
