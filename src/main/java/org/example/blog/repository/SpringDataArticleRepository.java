package org.example.blog.repository;

import org.example.blog.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByTitle(String title);

    List<Article> findByAuthor_Email(String authorEmail);

    List<Article> findByBlog_Name(String blogName);
}
