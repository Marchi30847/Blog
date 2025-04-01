package org.example.blog.service;

import org.example.blog.entity.Blog;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.exception.UniquenessValidationException;
import org.example.blog.repository.SpringDataBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final SpringDataBlogRepository blogRepository;

    @Autowired
    public BlogService(SpringDataBlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog findById(Long id) throws NoSuchEntityException {
        return blogRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("Blog Not Found"));
    }

    public List<Blog> findByName(String blogName) throws NoSuchEntityException {
        List<Blog> blogs = blogRepository.findByName(blogName);
        if (blogs.isEmpty()) {
            throw new NoSuchEntityException("Blog Not Found");
        }
        return blogs;
    }

    public Blog findByBlogManagerEmail(String managerEmail) throws NoSuchEntityException {
        return blogRepository.findByManager_Email(managerEmail).orElseThrow(() -> new NoSuchEntityException("Blog Not Found"));
    }

    public Blog findByArticleTitle(String articleTitle) throws NoSuchEntityException {
        return blogRepository.findByArticles(articleTitle).orElseThrow(() -> new NoSuchEntityException("Blog Not Found"));
    }

    public List<Blog> findAll() {
        return (List<Blog>) blogRepository.findAll();
    }

    public void save(Blog blog) throws UniquenessValidationException {
        try {
            findByBlogManagerEmail(blog.getManager().getEmail());
            throw new UniquenessValidationException("Blog Manager is already busy");
        } catch (NoSuchEntityException e) {
            blogRepository.save(blog);
        }
    }

    public void delete(Long id) throws NoSuchEntityException {
        if (blogRepository.findById(id).isEmpty()) {
            throw new NoSuchEntityException("Blog Not Found");
        }
        blogRepository.deleteById(id);
    }
}
