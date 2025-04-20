package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDao;

    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(User user) {
        if (user.getName() == null || user.getEmail() == null || user.getAge() == 0) {
            throw new IllegalArgumentException("Имя, Email и Age обязательны!");
        }
        user.setCreatedAt(LocalDateTime.now());
        userDao.create(user);
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }
}
