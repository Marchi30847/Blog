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

    @OneToMany(mappedBy = "blog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
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

    @Override
    public String toString() {
        return "id: " + id + ", " +
                "name: " + name + ", " +
                "manager_id: " + manager.getId() + ", " +
                "manager_email: " + manager.getEmail() + ", " +
                "articles: [" + articles.stream()
                .map(article -> article.getId() + " - " + article.getTitle())
                .reduce((a, b) -> a + ", " + b)
                .orElse("") + "]";
    }

    @PreRemove
    private void preRemove() {
        if (manager != null) {
            manager.setManagedBlog(null);
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public User getManager() {return manager;}
    public Set<Article> getArticles() {return articles;}

    public void setName(String name) {this.name = name;}
    public void setManager(User manager) {this.manager = manager;}
    public void setArticles(Set<Article> articles) {this.articles = articles;}
}
