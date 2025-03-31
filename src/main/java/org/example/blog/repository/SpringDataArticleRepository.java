package org.example.blog.repository;

import org.example.blog.entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByTitle(String title);

    @Query("SELECT a FROM Article a JOIN a.author u WHERE u.email = :authorEmail")
    List<Article> findByAuthor(String authorEmail);

    @Query("SELECT a FROM Article a JOIN a.blog b WHERE b.name = :blogName")
    List<Article> findByBlog(String blogName);
}
