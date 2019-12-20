package se.yrgo.budgetplanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.yrgo.budgetplanner.exceptions.UserNotFoundException;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.UserRepository;
import se.yrgo.budgetplanner.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    final LocalDate DATE = LocalDate.now();
    User user, user1, user2;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(10L);
        user1 = new User("anders@abc.com", "password", DATE, DATE);
        user2 = new User("anders@abc.com", "password", DATE, DATE);

        List<User> allUsers = Arrays.asList(user1, user2, user);

        when(userRepository.findAll()).thenReturn(allUsers);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(
                user));
        when(userRepository.findByEmail(user1.getEmail()))
                .thenReturn(user1);
        when(userRepository.findByEmail("wrong_email"))
                .thenReturn(null);

    }

    @Test
    public void registerUser() {
        String email = "alexander@abc.com";
        String password = "password";
        User fakeNews = new User(email, password, DATE, DATE);
        User user = new User(email, password, DATE, DATE);

        when(userRepository.findByEmail(fakeNews.getEmail())).thenReturn(null);
        when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(fakeNews);

        User returnedUser = userService.registerUser(user);

        verifySaveIsCalledOnce();
        assertEquals(returnedUser, fakeNews);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        Long id = 10L;
        User returnedUser = userService.getUserById(id);
        verifyFindByIdIsCalledOnce();
        assertEquals(id, returnedUser.getId());
    }

    @Test(expected = UserNotFoundException.class)
    public void whenInValidId_thenUserShouldBeFound() {
        Long id = 100L;
        User returnedUser = userService.getUserById(id);
        verifyFindByIdIsCalledOnce();
        assertEquals(id, returnedUser.getId());
    }

    @Test
    public void whenValidEmail_thenUserShouldBeFound() {
        String email = "anders@abc.com";
        User foundUser = userService.getUserByEmail(email);
        assertThat(foundUser.getEmail()).isEqualTo(email);
        verifyFindByEmailIsCalledOnce(email);
    }


    @Test(expected = UserNotFoundException.class)
    public void whenInvalidEmail_thenUserShouldNotBeFound_andExceptionShouldBeThrown() {
        String email = "wrong_email";
        User returnedUser = userService.getUserByEmail(email);
        assertThat(returnedUser).isNull();
        verifyFindByEmailIsCalledOnce(email);
    }

    @Test
    public void given3Users_whenGetAll_thenReturnAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        assertThat(allUsers).hasSize(3).extracting(User::getEmail).contains(user.getEmail(), user1.getEmail(), user2.getEmail());
        verifyFindAllUsersIsCalledOnce();
    }

    @Test
    public void TestDeleteUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(new User()));
        userService.removeUserById(user.getId());
        verifyDeleteIsCalledOnce(user.getId());
    }


//    @Test
//    public void whenUpdatingUser_UpdatedUserShouldBeReturned() {
//        String email = "alexander@abc.com";
//        String password="password";
//        User fakeNews = new User(email,password,DATE,DATE);
//        User user = new User(email,password,DATE,DATE);
//
//        when(userRepository.findByEmail(fakeNews.getEmail())).thenReturn(null);
//        when(userRepository.save(Mockito.any(User.class)))
//                .thenReturn(fakeNews);
//
//        userService.updateProfile(user);
////        assertThat(updated_user1).isEqualTo()
//        assertEquals("new email", updated_user.getEmail());
//        verifyUpdate_SaveIsCalledOnce();
//    }


    private void verifyDeleteIsCalledOnce(Long id) {
        verify(userRepository, times(1)).deleteById(id);
        Mockito.reset(userRepository);
    }


    private void verifyFindByEmailIsCalledOnce(String email) {
        verify(userRepository, times(1)).findByEmail(email);
        Mockito.reset(userRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        verify(userRepository, times(1)).findById(Mockito.anyLong());
        Mockito.reset(userRepository);
    }

    private void verifyFindAllUsersIsCalledOnce() {
        verify(userRepository, times(1)).findAll();
        Mockito.reset(userRepository);
    }

    private void verifySaveIsCalledOnce() {
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        Mockito.reset(userRepository);
    }

    private void verifyUpdate_SaveIsCalledOnce() {
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        Mockito.reset(userRepository);
    }
}
