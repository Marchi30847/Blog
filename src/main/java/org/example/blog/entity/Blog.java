package org.example.blog.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Article> articles;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public Blog() {
    }

    public Blog(String name, User manager) {
        this.name = name;
        this.manager = manager;
    }

    public void removeFromArticles(Article article) {
        this.articles.remove(article);
        article.setBlog(null);
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public User getManager() {return manager;}
    public Set<Article> getArticles() {return articles;}

    public void setName(String name) {this.name = name;}
    public void setManager(User manager) {this.manager = manager;}
    public void setArticles(Set<Article> articles) {this.articles = articles;}
}
