package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

}
