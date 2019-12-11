package se.yrgo.budgetplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.exceptions.UserExistsException;
import se.yrgo.budgetplanner.exceptions.UserNotFoundException;
import se.yrgo.budgetplanner.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public User addUser(@RequestBody User user) throws UserExistsException{
        return userService.registerUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) throws UserNotFoundException {
        userService.removeUserById(id);
    }

    @PutMapping("/update/{id}")
    public User updateUserById(@RequestBody User user, @PathVariable Long id ) throws UserNotFoundException {

        return userService.updateProfileById(id,user);
    }
    @PutMapping("/update")
    public User updateUser(@RequestBody User user) throws UserNotFoundException {
        return userService.updateProfile(user);
    }
    @PutMapping("/update/password")
    public User updateUserPassword(@RequestBody User user) throws UserNotFoundException {
        return userService.changePassword(user);
    }

    @GetMapping("/all")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable Long id) throws UserNotFoundException{
            return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }
}
