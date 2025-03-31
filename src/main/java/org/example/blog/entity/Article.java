package org.example.blog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    public Article() {
    }

    public Article(String title, User author, Blog blog) {
        this.title = title;
        this.author = author;
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "id: " + id + ", " +
                "title: " + title + ", " +
                "author_id: " + author.getId() + ", " +
                "author_email: " + author.getEmail() + ", " +
                "blog_id: " + blog.getId() + ", " +
                "blog_name: " + blog.getName();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}