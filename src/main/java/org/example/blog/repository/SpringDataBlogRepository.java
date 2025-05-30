package org.example.blog.repository;

import org.example.blog.entity.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataBlogRepository extends CrudRepository<Blog, Long> {
    List<Blog> findByName(String name);

    Optional<Blog> findByManager_Email(String managerEmail);

    @Query("SELECT b FROM Blog b JOIN b.articles a WHERE a.title = :articleTitle")
    Optional<Blog> findByArticles(String articleTitle);
}

