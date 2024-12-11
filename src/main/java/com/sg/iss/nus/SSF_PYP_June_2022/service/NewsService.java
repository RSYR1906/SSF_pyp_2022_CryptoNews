package com.sg.iss.nus.SSF_PYP_June_2022.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sg.iss.nus.SSF_PYP_June_2022.constant.Url;
import com.sg.iss.nus.SSF_PYP_June_2022.model.Article;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    private RedisTemplate<String, Object> template;

    RestTemplate restTemplate = new RestTemplate();

    public List<Article> getArticles() { // TASK 1

        String articleData = restTemplate.getForObject(Url.newsUrl, String.class);  // get entire Json Data as a String
        JsonReader jReader = Json.createReader(new StringReader(articleData));  // wraps the String for reading
        JsonObject jObject = jReader.readObject();                  // convert the JSON data into a JsonObject

        JsonArray jArray = jObject.getJsonArray("Data");    // accessing the Data JsonArray

        List<Article> articleList = new ArrayList<>();

        for (JsonValue value : jArray) {
            Article a = new Article();
            JsonObject articleJson = value.asJsonObject();

            // Set basic article fields
            a.setId(articleJson.getInt("ID"));
            a.setPublishedDate(articleJson.getJsonNumber("PUBLISHED_ON").longValue());
            a.setImageUrl(articleJson.getString("IMAGE_URL"));
            a.setTitle(articleJson.getString("TITLE"));
            a.setUrl(articleJson.getString("URL"));
            a.setBody(articleJson.getString("BODY"));
            a.setTags(articleJson.getString("KEYWORDS"));

            JsonArray categoryData = articleJson.getJsonArray("CATEGORY_DATA");
            List<String> categories = new ArrayList<>();
            if (categoryData != null) {
                for (JsonValue category : categoryData) {
                    categories.add(category.asJsonObject().getString("CATEGORY"));
                }
            }
            a.setCategories(String.join(", ", categories)); // Join categories as a comma-separated string

            // Add to article list
            articleList.add(a);
        }
        return articleList;
    }

    public void saveArticles(List<Article> articles) {      // TASK 3

        for (Article article : articles) {
            String redisKey = "article:" + article.getId().toString();
            logger.info("Saving article with key: {}", redisKey);
            template.opsForValue().set(redisKey, article);
        }
    }

    public Article getArticleById(String articleId) {
        // Generate the Redis key for the board game
        String redisKey = "article:" + articleId;

        // Retrieve the object from Redis
        Object articleObj = template.opsForValue().get(redisKey);

        // Return null if not found
        if (articleObj == null) {
            return null;
        }

        // Cast and return the object as BoardGame
        return (Article) articleObj;
    }

    public List<Article> getSavedArticles() {
        List<Article> savedArticles = new ArrayList<>();
        Set<String> keys = template.keys("article:*"); // Assuming keys are prefixed with "article:"
        if (keys != null) {
            for (String key : keys) {
                Object articleObj = template.opsForValue().get(key);
                if (articleObj instanceof Article) {
                    savedArticles.add((Article) articleObj);
                }
            }
        }
        return savedArticles;
    }
}
