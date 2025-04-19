package org.example;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImplementation;
import org.example.model.User;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImplementation();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Создать пользователя");
            System.out.println("2. Показать всех пользователей");
            System.out.println("3. Найти по ID");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка \n

            switch (choice) {
                case 1 -> {
                    System.out.print("Имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Возраст: ");
                    int age = scanner.nextInt();
                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setAge(age);
                    userDao.create(user);
                }
                case 2 -> {
                    List<User> users = userDao.getAll();
                    users.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    System.out.println(userDao.getById(id));
                }
                case 4 -> {
                    System.out.print("ID пользователя: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // очистка \n
                    User user = userDao.getById(id);
                    if (user != null) {
                        System.out.print("Новое имя: ");
                        user.setName(scanner.nextLine());
                        System.out.print("Новый email: ");
                        user.setEmail(scanner.nextLine());
                        System.out.print("Новый возраст: ");
                        user.setAge(scanner.nextInt());
                        userDao.update(user);
                    } else {
                        System.out.println("Пользователь не найден.");
                    }
                }
                case 5 -> {
                    System.out.print("ID пользователя для удаления: ");
                    int id = scanner.nextInt();
                    userDao.delete(id);
                }
                case 0 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный ввод");
            }
        }
    }
}