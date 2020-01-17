package se.yrgo.budgetplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

//    @PostMapping("/register")
//    public User registerUser(@RequestBody User user){
//        return userService.registerUser(user);
//    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void removeUserById(@PathVariable Long id)  {
        userService.removeUserById(id);
    }

    @PutMapping("/update/{id}")
    public User updateUserById(@RequestBody User user, @PathVariable Long id )  {
        return userService.updateProfileById(id,user);
    }

    @PutMapping("/update")
    public User updateProfile(@RequestBody User user)  {
        return userService.updateProfile(user);
    }
    @PutMapping("/update/password")
    public User changePassword(@RequestBody User user)  {
        return userService.changePassword(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable Long id) {
            return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }
}
