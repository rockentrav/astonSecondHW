package org.example.dao;


import org.example.model.User;

import java.util.List;

public interface UserDAO {

    void create(User user);

    User getById(int id);

    List<User> getAll();

    void update(User user);

    void delete(int id);

}