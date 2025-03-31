package org.example.blog.service;

import org.example.blog.entity.Article;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.repository.SpringDataArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final SpringDataArticleRepository articleRepository;

    @Autowired
    public ArticleService(SpringDataArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article findById(Long id) throws NoSuchEntityException {
        return articleRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("Article Not Found"));
    }

    public List<Article> findByTitle(String title) throws NoSuchEntityException {
        List<Article> articles = articleRepository.findByTitle(title);
        if (articles.isEmpty()) {
            throw new NoSuchEntityException("Article Not Found");
        }
        return articles;
    }

    public List<Article> findByAuthorEmail(String authorEmail) throws NoSuchEntityException {
        List<Article> articles = articleRepository.findByAuthor(authorEmail);
        if (articles.isEmpty()) {
            throw new NoSuchEntityException("Article Not Found");
        }
        return articles;
    }

    public List<Article> findByBlogName(String blogName) throws NoSuchEntityException {
        List<Article> articles = articleRepository.findByBlog(blogName);
        if (articles.isEmpty()) {
            throw new NoSuchEntityException("Article Not Found");
        }
        return articles;
    }

    public List<Article> findAll() throws NoSuchEntityException {
        List<Article> articles = (List<Article>) articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new NoSuchEntityException("Article Not Found");
        }
        return articles;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public void delete(Long id) throws NoSuchEntityException {
        if (articleRepository.findById(id).isEmpty()) {
            throw new NoSuchEntityException("Article Not Found");
        }
        articleRepository.deleteById(id);
    }
}
