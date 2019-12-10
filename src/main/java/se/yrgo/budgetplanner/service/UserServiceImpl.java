package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) throws UserExistsException {
        User userSearch = userRepository.findByEmail(user.getEmail());

        if (userSearch == null) {
            user.setCreationDate(LocalDate.now());
            user.setLastModifiedDate(LocalDate.now());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
            foundUser.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
        return userRepository.findByEmail(email);
    }

    private User setFoundUser(Optional<User> foundUser, User user) throws UserNotFoundException {

        if (foundUser.isPresent()) {
            foundUser.get().setLastModifiedDate(LocalDate.now());
            return userRepository.save(foundUser.get());
        } else {
            throw new UserNotFoundException();
        }
    }

}
