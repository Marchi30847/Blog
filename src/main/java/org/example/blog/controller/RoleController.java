package org.example.blog.controller;

import org.example.blog.entity.Role;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.exception.UniquenessValidationException;
import org.example.blog.service.RoleService;
import org.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class RoleController {
    private final UserService userService;
    private final RoleService roleService;
    private final Scanner scanner;

    @Autowired
    public RoleController(UserService userService, RoleService roleService, Scanner scanner) {
        this.userService = userService;
        this.roleService = roleService;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("Choose an operation: ");
            System.out.println("1. Add Role");
            System.out.println("2. Delete Role");
            System.out.println("3. Find Role");
            System.out.println("4. List Role");
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
        System.out.println("\n" + "ADD A NEW ROLE");
        displayBreakLine();
        System.out.println("Enter a name");
        displayBreakLine();

        String name = scanner.nextLine();


        Role role = new Role(name);
        try {
            roleService.save(role);
        } catch (UniquenessValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("A new role added");
        displayBreakLine();
    }

    private void delete() {
        System.out.println("\n" + "DELETE AN EXISTING ROLE");
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
        System.out.println("2. Find by name");
        System.out.println("3. Find by user");
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
                System.out.println("Enter a user email");
                displayBreakLine();

                String email = scanner.nextLine();

                List<Role> roles;
                try {
                    roles = roleService.findByUserEmail(email);
                } catch (NoSuchEntityException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                for (Role role : roles) {
                    System.out.println(role);
                }

                displayBreakLine();
            }
            default -> System.out.println("Invalid input, please follow the format");
        }
    }

    private void list() {
        System.out.println("\n" + "LIST OF ALL ROLES");
        displayBreakLine();

        for (Role role : roleService.findAll()) {
            System.out.println(role);
        }

        displayBreakLine();
    }

    private void displayBreakLine() {
        System.out.println("-------------------------------------------------------------------------");
    }
}
