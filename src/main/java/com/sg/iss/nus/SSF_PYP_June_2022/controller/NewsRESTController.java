package com.sg.iss.nus.SSF_PYP_June_2022.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.iss.nus.SSF_PYP_June_2022.model.Article;
import com.sg.iss.nus.SSF_PYP_June_2022.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsRESTController {

    @Autowired
    NewsService newsService;

    private static final Logger logger = LoggerFactory.getLogger(NewsRESTController.class);

    @GetMapping("")
    public ResponseEntity<List<Article>> getAllArticles() {

        List<Article> articleList = newsService.getArticles();

        return ResponseEntity.ok().body(articleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable("id") String articleId) {
        // Save articles to Redis
        Article article = newsService.getArticleById(articleId);

        // If the board game is not found, return 404 status with an error message
        if (article == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Cannot find news article " + articleId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(article);
    }

}
