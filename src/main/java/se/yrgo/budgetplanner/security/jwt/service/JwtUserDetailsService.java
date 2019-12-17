package se.yrgo.budgetplanner.security.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<se.yrgo.budgetplanner.model.user.User> userOptional = Optional.ofNullable(userService.getUserByEmail(username));
        if (userOptional.isPresent()) {
            se.yrgo.budgetplanner.model.user.User user = userOptional.get();
            return new User(user.getEmail(), user.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
    }
}

