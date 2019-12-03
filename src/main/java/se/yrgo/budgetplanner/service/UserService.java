package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) {

       User newUser= userRepository.save(user);
        return newUser;
    }

    public void removeUserById(Long id){
        userRepository.deleteById(id);
    }

    public void update(Long id, User user){
        Optional<User> foundUser = userRepository.findById(id);
        user.setId(id);
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    public User getUserByEmail(String email) {
       User user = userRepository.findByEmail(email);
//        List<User> users = userRepository.findByEmail(email);
        return user;
    }
}
