package org.example.blog;

import org.example.blog.controller.ArticleController;
import org.example.blog.controller.BlogController;
import org.example.blog.controller.RoleController;
import org.example.blog.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(
                BlogApplication.class, args
        );

        ArticleController articleService = context.getBean(ArticleController.class);
        BlogController blogService = context.getBean(BlogController.class);
        RoleController roleService = context.getBean(RoleController.class);
        UserController userService = context.getBean(UserController.class);
        Scanner scanner = context.getBean(Scanner.class);

        while (true) {
            System.out.println("Choose an operation: ");
            System.out.println("1. Article");
            System.out.println("2. Blog");
            System.out.println("3. Role");
            System.out.println("4. User");
            System.out.println("5. Exit");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input, please follow the format");
                scanner.nextLine();
                continue;
            }

            int operation = scanner.nextInt();
            scanner.nextLine();

            switch (operation) {
                case 1 -> articleService.start();
                case 2 -> blogService.start();
                case 3 -> roleService.start();
                case 4 -> userService.start();
                case 5 -> System.exit(0);
            }

        }
    }

}
