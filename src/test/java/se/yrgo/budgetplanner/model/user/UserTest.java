package se.yrgo.budgetplanner.model.user;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserTest {
    final LocalDate DATE = LocalDate.now();

    @Test
    public void TestIfUsersWithTheSameMailAndPasswordAreConsideredTheSame(){
       User user1 = new User("Anders","Andersson","anders@abc.com", "password", DATE,DATE);
       User user2 = new User("Anders","Andersson","anders@abc.com", "password", DATE,DATE);
       assertTrue("",user1.equals(user2));
    }
}