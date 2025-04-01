package org.example.blog.controller;

import org.example.blog.entity.Blog;
import org.example.blog.entity.User;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.exception.UniquenessValidationException;
import org.example.blog.service.BlogService;
import org.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class BlogController {
    private final UserService userService;
    private final BlogService blogService;
    private final Scanner scanner;

    @Autowired
    public BlogController(UserService userService, BlogService blogService, Scanner scanner) {
        this.userService = userService;
        this.blogService = blogService;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("Choose an operation: ");
            System.out.println("1. Add Blog");
            System.out.println("2. Delete Blog");
            System.out.println("3. Find Blog");
            System.out.println("4. List Blog");
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
        System.out.println("\n" + "ADD A NEW BLOG");
        displayBreakLine();
        System.out.println("Enter a name");
        displayBreakLine();

        String name = scanner.nextLine();

        displayBreakLine();
        System.out.println("Enter a manager id");
        displayBreakLine();

        if (!scanner.hasNextLong()) {
            System.out.println("Invalid input, please follow the format");
            scanner.nextLine();
            return;
        }

        Long managerId = scanner.nextLong();
        scanner.nextLine();

        User manager;
        try {
            manager = userService.findById(managerId);
        } catch (NoSuchEntityException e) {
            System.out.println(e.getMessage());
            return;
        }

        Blog blog = new Blog(name, manager);
        try {
            blogService.save(blog);
        } catch (UniquenessValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("A new blog added");
        displayBreakLine();
    }

    private void delete() {
        System.out.println("\n" + "DELETE AN EXISTING BLOG");
        displayBreakLine();
        System.out.println("Enter an id of the blog you want to delete");
        displayBreakLine();

        if (!scanner.hasNextLong()) {
            System.out.println("Invalid input, please follow the format");
            scanner.nextLine();
            return;
        }

        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            blogService.delete(id);
        } catch (NoSuchEntityException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("The blog is deleted");
        displayBreakLine();
    }

    private void find() {
        System.out.println("\n" + "CHOOSE A SEARCHING OPTION");
        displayBreakLine();
        System.out.println("1. Find by id");
        System.out.println("2. Find by email");
        System.out.println("3. Find by role");
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

                User user;
                try {
                    user = userService.findById(id);
                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                System.out.println(user);

                displayBreakLine();
            }
            case 2 -> {
                System.out.println("Enter an email");
                displayBreakLine();

                String email = scanner.nextLine();

                User user;
                try {
                    user = userService.findByEmail(email);
                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                System.out.println(user);

                displayBreakLine();
            }
            case 3 -> {
                System.out.println("Enter a role name");
                displayBreakLine();

                String roleName = scanner.nextLine();

                List<User> users;
                try {
                    users = userService.findByUserRoleName(roleName);
                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                for (User user : users) {
                    System.out.println(user);
                }

                displayBreakLine();
            }
            default -> System.out.println("Invalid input, please follow the format");
        }
    }

    private void list() {
        System.out.println("\n" + "LIST OF ALL BLOGS");
        displayBreakLine();

        for (Blog blog : blogService.findAll()) {
            System.out.println(blog);
        }

        displayBreakLine();
    }

    private void displayBreakLine() {
        System.out.println("-------------------------------------------------------------------------");
    }
}
