package com.sg.iss.nus.SSF_PYP_June_2022.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sg.iss.nus.SSF_PYP_June_2022.model.Article;
import com.sg.iss.nus.SSF_PYP_June_2022.service.NewsService;

@Controller
@RequestMapping("")
public class NewsController { // TASK 4

    @Autowired
    NewsService newsService;

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @GetMapping("") // TASK 2
    public String showLatestNews(Model model) {
        List<Article> articleList = newsService.getArticles();
        model.addAttribute("articles", articleList);

        return "news";
    }

    @GetMapping("/saved-news")
    public String getSavedArticles(Model model) {
        List<Article> savedArticles = newsService.getSavedArticles(); // Fetch from Redis
        model.addAttribute("savedArticles", savedArticles);
        return "saved-news";
    }

    // Save selected articles to Redis
    @PostMapping("/articles")
    public String saveArticle(
            @RequestParam("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("body") String body,
            @RequestParam("tags") String tags,
            @RequestParam("url") String url,
            @RequestParam("categories") String categories,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("publishedDate") Long publishedDate) {

        // Create an Article object from the form data
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setBody(body);
        article.setTags(tags);
        article.setUrl(url);
        article.setCategories(categories);
        article.setPublishedDate(publishedDate);
        article.setImageUrl(imageUrl);

        logger.info("Saving article with ID: {}", id);

        try {
            // Save the article
            newsService.saveArticles(List.of(article));
            logger.info("Article saved successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error saving article: {}", e.getMessage(), e);
        }

        return "redirect:/";
    }

}
