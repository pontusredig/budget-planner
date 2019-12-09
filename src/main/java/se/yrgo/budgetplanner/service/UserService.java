package se.yrgo.budgetplanner.service;

import se.yrgo.budgetplanner.model.user.User;

import java.util.List;

public interface UserService {
    User registerUser(User user) throws UserExistsException;

    void removeUserById(Long id) throws UserNotFoundException;

    User updateProfileById(Long id, User user) throws UserNotFoundException;
    User updateProfile(User user) throws UserNotFoundException;

    List<User> getAllUsers();
    User getUserById(Long id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    }

