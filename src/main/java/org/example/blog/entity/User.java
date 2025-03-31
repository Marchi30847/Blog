package org.example.blog.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Article> articles;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL)
    private Blog managedBlog;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
    }

    public User(String email, Set<Article> articles, Blog managedBlog, Set<Role> roles) {
        this.email = email;
        this.articles = articles;
        this.managedBlog = managedBlog;
        this.roles = roles;
    }

    public void removeFromArticles(Article article) {
        this.articles.remove(article);
        //article.setAuthor(null);
    }

    public void removeFromRoles(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public Long getId() {return id;}
    public String getEmail() {return email;}
    public Set<Article> getArticles() {return articles;}
    public Blog getManagedBlog() {return managedBlog;}
    public Set<Role> getRoles() {return roles;}

    public void setEmail(String email) {this.email = email;}
    public void setArticles(Set<Article> articles) {this.articles = articles;}
    public void setManagedBlog(Blog managedBlog) {this.managedBlog = managedBlog;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
}
