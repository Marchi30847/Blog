package org.example.blog;

import org.example.blog.controller.ArticleController;
import org.example.blog.service.ArticleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(
                BlogApplication.class, args
        );

        ArticleController articleService = context.getBean(ArticleController.class);
        articleService.start();
    }

}
