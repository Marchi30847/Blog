package org.example.blog.controller;

import org.example.blog.entity.Article;
import org.example.blog.entity.Blog;
import org.example.blog.entity.User;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.service.ArticleService;
import org.example.blog.service.BlogService;
import org.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final BlogService blogService;
    private final Scanner scanner;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService, BlogService blogService, Scanner scanner) {
        this.articleService = articleService;
        this.userService = userService;
        this.blogService = blogService;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("Choose an operation: ");
            System.out.println("1. Add Article");
            System.out.println("2. Delete Article");
            System.out.println("3. Find Article");
            System.out.println("4. List Articles");
            System.out.println("5. Return to Main Menu");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input, please follow the format");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> add();
                case 2 -> delete();
                case 3 -> find();
                case 4 -> list();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input, please follow the format");
            }
        }
    }

    private void add() {
        System.out.println("\n" + "ADD A NEW ARTICLE");
        displayBreakLine();
        System.out.println("Enter a title");
        displayBreakLine();

        String title = scanner.nextLine();

        displayBreakLine();
        System.out.println("Enter an author id");
        displayBreakLine();

        if (!scanner.hasNextLong()) {
            System.out.println("Invalid input, please follow the format");
            scanner.nextLine();
            return;
        }

        Long authorId = scanner.nextLong();
        scanner.nextLine();

        User author;
        try {
            author = userService.findById(authorId);
        } catch (NoSuchEntityException e) {
            System.out.println("No such author");
            return;
        }

        displayBreakLine();
        System.out.println("Enter a blog id");
        displayBreakLine();

        if (!scanner.hasNextLong()) {
            System.out.println("Invalid input, please follow the format");
            scanner.nextLine();
            return;
        }

        Long blogId = scanner.nextLong();
        scanner.nextLine();

        Blog blog;
        try {
            blog = blogService.findById(blogId);
        } catch (NoSuchEntityException e) {
            System.out.println("No such blog");
            return;
        }

        Article article = new Article(title, author, blog);
        articleService.save(article);

        System.out.println("A new article added");
        displayBreakLine();
    }

    private void delete() {
        System.out.println("\n" + "DELETE AN EXISTING ARTICLE");
        displayBreakLine();
        System.out.println("Enter an id of the article you want to delete");
        displayBreakLine();

        if (!scanner.hasNextLong()) {
            System.out.println("Invalid input, please follow the format");
            scanner.nextLine();
            return;
        }

        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            articleService.delete(id);
        } catch (NoSuchEntityException e) {
            System.out.println("No such article");
            return;
        }

        System.out.println("Your article is deleted");
        displayBreakLine();
    }

    private void find() {
        System.out.println("\n" + "CHOOSE A SEARCHING OPTION");
        displayBreakLine();
        System.out.println("1. Find by id");
        System.out.println("2. Find by title");
        System.out.println("3. Find by author");
        System.out.println("4. Find by blog");
        displayBreakLine();

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input, please follow the format");
            scanner.nextLine();
            return;
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.println("Enter an id");
                displayBreakLine();

                if (!scanner.hasNextLong()) {
                    System.out.println("Invalid input, please follow the format");
                    scanner.nextLine();
                    return;
                }

                Long id = scanner.nextLong();
                scanner.nextLine();

                Article article;
                try {
                    article = articleService.findById(id);
                } catch (NoSuchEntityException e) {
                    System.out.println("No such article");
                    return;
                }

                System.out.println(article);

                displayBreakLine();
            }
            case 2 -> {
                System.out.println("Enter an title");
                displayBreakLine();

                String title = scanner.nextLine();

                List<Article> articles;
                try {
                    articles = articleService.findByTitle(title);
                } catch (NoSuchEntityException e) {
                    System.out.println("No such article");
                    return;
                }

                for (Article article : articles) {
                    System.out.println(article);
                }

                displayBreakLine();
            }
            case 3 -> {
                System.out.println("Enter an author email");
                displayBreakLine();

                String email = scanner.nextLine();

                List<Article> articles;
                try {
                    articles = articleService.findByAuthorEmail(email);
                } catch (NoSuchEntityException e) {
                    System.out.println("No such article");
                    return;
                }

                for (Article article : articles) {
                    System.out.println(article);
                }

                displayBreakLine();
            }
            case 4 -> {
                System.out.println("Enter a blog name");
                displayBreakLine();

                String blogName = scanner.nextLine();

                List<Article> articles;
                try {
                    articles = articleService.findByBlogName(blogName);
                } catch (NoSuchEntityException e) {
                    System.out.println("No such article");
                    return;
                }

                for (Article article : articles) {
                    System.out.println(article);
                }

                displayBreakLine();
            }
            default -> System.out.println("Invalid input, please follow the format");
        }
    }

    private void list() {
        System.out.println("\n" + "LIST OF ALL ARTICLES");
        displayBreakLine();

        for (Article article : articleService.findAll()) {
            System.out.println(article);
        }

        displayBreakLine();
    }

    private void displayBreakLine() {
        System.out.println("-------------------------------------------------------------------------");
    }
}
