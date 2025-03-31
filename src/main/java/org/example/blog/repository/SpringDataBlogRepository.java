package org.example.blog.repository;

import org.example.blog.entity.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataBlogRepository extends CrudRepository<Blog, Long> {
    List<Blog> findByName(String name);

    @Query("SELECT b FROM Blog b JOIN User u ON b.manager = u WHERE u.email = :managerEmail")
    List<Blog> findByManager(String managerEmail);
}

