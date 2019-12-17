package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        User userSearch = userRepository.findByEmail(user.getEmail());
        if (userSearch != null) {
            throw new UserExistsException();
        }

        user.setCreationDate(LocalDate.now());
        user.setLastModifiedDate(LocalDate.now());
//        user.setPassword(passwordEncoder.encode(test));
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public void removeUserById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public User updateProfileById(Long id, User user) {
        Optional<User> foundUser = userRepository.findById(id);
        user.setId(id);
        return setFoundUser(foundUser, user);
    }

    public User updateProfile(User user)  {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        return setFoundUser(foundUser, user);
    }

    public User changePassword(User user)  {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }

        foundUser.get().setLastModifiedDate(LocalDate.now());
        foundUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(foundUser.get());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id '" + id + "' does no exist"));
    }

    public User getUserByEmail(String email) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }
        return foundUser.get();
    }

    private User setFoundUser(Optional<User> foundUser, User user){
        System.out.println("Print"+foundUser.get());
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }

        foundUser.get().setLastModifiedDate(LocalDate.now());
        return userRepository.save(foundUser.get());
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String owner = authentication.getName();
        Optional<User> userOptional = Optional.ofNullable(getUserByEmail(owner));
        return userOptional.get();
    }

}
