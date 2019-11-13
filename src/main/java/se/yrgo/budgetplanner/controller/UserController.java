package se.yrgo.budgetplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/adduser")
    public void addUser(@RequestBody User user){
        userService.saveUser(user);
    }

}
