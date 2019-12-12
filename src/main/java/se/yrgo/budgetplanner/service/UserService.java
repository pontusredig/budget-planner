package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.exceptions.UserExistsException;
import se.yrgo.budgetplanner.exceptions.UserNotFoundException;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) throws UserExistsException {
        User userSearch = userRepository.findByEmail(user.getEmail());
        if (userSearch == null) {
            user.setCreationDate(LocalDate.now());
            user.setLastModifiedDate(LocalDate.now());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userRepository.save(user);
            return newUser;
        } else {
            throw new UserExistsException();
        }
    }

    public void removeUserById(Long id) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    public User updateProfileById(Long id, User user) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(id);
        user.setId(id);
        return setFoundUser(foundUser, user);
    }

    public User updateProfile(User user) throws UserNotFoundException {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        return setFoundUser(foundUser, user);
    }

    public User changePassword(User user) throws UserNotFoundException {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if (foundUser.isPresent()) {
            foundUser.get().setLastModifiedDate(LocalDate.now());
            foundUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(foundUser.get());
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            return foundUser.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (foundUser.isPresent()) {
            return foundUser.get();
        }
       else {
            return null;
        }
    }

    private User setFoundUser(Optional<User> foundUser, User user) throws UserNotFoundException {
        if (foundUser.isPresent()) {
            foundUser.get().setLastModifiedDate(LocalDate.now());
            return userRepository.save(foundUser.get());
        } else {
            throw new UserNotFoundException();
        }
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String owner = authentication.getName();
        Optional<User> userOptional = Optional.ofNullable(getUserByEmail(owner));
        return userOptional.get();
    }
}
