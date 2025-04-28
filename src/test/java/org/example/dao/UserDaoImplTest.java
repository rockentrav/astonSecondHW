package org.example.dao;

import org.example.model.User;
import org.example.util.HibernateFactoryUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOImplTest {

    private static UserDAO userDAO;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @BeforeAll
    static void setUp() {
        postgres.start();

        // Переопределяем параметры подключения
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        SessionFactory sessionFactory = HibernateFactoryUtil.getSessionFactory();
        userDAO = new UserDAOImpl(sessionFactory);
    }

    @AfterAll
    static void tearDown() {
        postgres.stop();
    }

    @Test
    @Order(1)
    void testCreateUser() {
        User user = new User();
        user.setName("Test Name");
        user.setEmail("test@example.com");
        user.setAge(30);

        userDAO.create(user);

        List<User> users = userDAO.getAll();
        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("test@example.com")));
    }

    @Test
    @Order(2)
    void testGetAllUsers() {
        List<User> users = userDAO.getAll();
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(3)
    void testGetUserById() {
        List<User> users = userDAO.getAll();
        User firstUser = users.get(0);

        User foundUser = userDAO.getById(firstUser.getId());
        assertNotNull(foundUser);
        assertEquals(firstUser.getName(), foundUser.getName());
    }

    @Test
    @Order(4)
    void testUpdateUser() {
        List<User> users = userDAO.getAll();
        User user = users.get(0);
        user.setName("Updated Name");

        userDAO.update(user);

        User updatedUser = userDAO.getById(user.getId());
        assertEquals("Updated Name", updatedUser.getName());
    }

    @Test
    @Order(5)
    void testDeleteUser() {
        List<User> users = userDAO.getAll();
        User user = users.get(0);

        userDAO.delete(user.getId());

        User deletedUser = userDAO.getById(user.getId());
        assertNull(deletedUser);
    }
}