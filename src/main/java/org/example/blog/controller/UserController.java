package org.example.blog.controller;

import org.example.blog.entity.Role;
import org.example.blog.entity.User;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.exception.UniquenessValidationException;
import org.example.blog.service.RoleService;
import org.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final Scanner scanner;

    @Autowired
    public UserController(UserService userService, RoleService roleService, Scanner scanner) {
        this.userService = userService;
        this.roleService = roleService;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("Choose an operation: ");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Find User");
            System.out.println("4. List User");
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
        System.out.println("\nADD A NEW USER");
        displayBreakLine();
        System.out.println("Enter an email:");
        displayBreakLine();

        String email = scanner.nextLine();
        Set<Role> roles = new HashSet<>();

        while (true) {
            displayBreakLine();
            System.out.println("Choose an operation: ");
            System.out.println("1. Add Role");
            System.out.println("2. Save User");
            displayBreakLine();

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input, please enter a number (1 or 2)");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter role id:");
                displayBreakLine();

                if (!scanner.hasNextLong()) {
                    System.out.println("Invalid input, please enter a valid role ID (number)");
                    scanner.nextLine();
                    continue;
                }

                Long id = scanner.nextLong();
                scanner.nextLine();

                try {
                    Role role = roleService.findById(id);
                    roles.add(role);

                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                }

            } else if (choice == 2) {
                break;
            } else {
                System.out.println("Invalid choice, please enter 1 or 2");
            }
        }

        User user = new User(email, roles);
        try {
            userService.save(user);
        } catch (UniquenessValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("A new user has been added");
        displayBreakLine();
    }

    private void delete() {
        System.out.println("\n" + "DELETE AN EXISTING USER");
        displayBreakLine();
        System.out.println("Enter an id of the role you want to delete");
        displayBreakLine();

        if (!scanner.hasNextLong()) {
            System.out.println("Invalid input, please follow the format");
            scanner.nextLine();
            return;
        }

        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            roleService.delete(id);
        } catch (NoSuchEntityException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("The role is deleted");
        displayBreakLine();
    }

    private void find() {
        System.out.println("\n" + "CHOOSE A SEARCHING OPTION");
        displayBreakLine();
        System.out.println("1. Find by id");
        System.out.println("2. Find by email");
        System.out.println("3. Find by role");
        System.out.println("3. Find by blog");
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

                Role role;
                try {
                    role = roleService.findById(id);
                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                System.out.println(role);

                displayBreakLine();
            }
            case 2 -> {
                System.out.println("Enter a name");
                displayBreakLine();

                String name = scanner.nextLine();

                Role role;
                try {
                    role = roleService.findByName(name);
                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                System.out.println(role);

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
            case 4 -> {
                System.out.println("Enter a blog name");
                displayBreakLine();

                String blogName = scanner.nextLine();

                User user;
                try {
                    user = userService.findByManagedBlogName(blogName);
                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                System.out.println(user);

                displayBreakLine();
            }
            default -> System.out.println("Invalid input, please follow the format");
        }
    }

    private void list() {
        System.out.println("\n" + "LIST OF ALL ROLES");
        displayBreakLine();

        for (User user : userService.findAll()) {
            System.out.println(user);
        }

        displayBreakLine();
    }

    private void displayBreakLine() {
        System.out.println("-------------------------------------------------------------------------");
    }
}
