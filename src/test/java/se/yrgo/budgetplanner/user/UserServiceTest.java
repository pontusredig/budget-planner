package se.yrgo.budgetplanner.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.yrgo.budgetplanner.service.UserService;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void test(){

        userService.getUserById();
    }

}
