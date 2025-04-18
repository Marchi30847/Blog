package org.example.blog.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Article> articles;

    @OneToOne(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Blog managedBlog;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
    }

    public User(String email, Set<Role> roles) {
        this.email = email;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "id: " + id + ", " +
                "email: " + email + ", " +
                "roles: [" + roles.stream()
                .map((role) -> role.getId() + " - " + role.getName())
                .reduce((a, b) -> a + ", " + b)
                .orElse("") + "], " +
                "articles: [" + articles.stream()
                .map((article) -> article.getId() + " - " + article.getTitle())
                .reduce((a, b) -> a + ", " + b)
                .orElse("") + "], " +
                "managedBlog_id: " + (managedBlog != null ? managedBlog.getId() : " - ") + ", " +
                "managedBlog_name: " + (managedBlog != null ? managedBlog.getName() : " - ");
    }

    @PreRemove
    private void preRemove() {
        for (Role role : roles) {
            role.getUsers().remove(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Blog getManagedBlog() {
        return managedBlog;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public void setManagedBlog(Blog managedBlog) {
        this.managedBlog = managedBlog;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
