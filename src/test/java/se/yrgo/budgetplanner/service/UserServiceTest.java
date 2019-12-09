package se.yrgo.budgetplanner.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    final LocalDate DATE = LocalDate.now();

    @MockBean
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        user.setId(1L);
        User user1 = new User("Anders","Andersson","anders@abc.com", "password", DATE,DATE);
        User user2 = new User("Alex","Andersson","anders@abc.com", "password", DATE,DATE);

        List<User> allUsers = Arrays.asList(user1,user2, user);
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(userRepository.findAll()).thenReturn(allUsers);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(
                user));
        when(userRepository.findByEmail(user1.getEmail()))
                .thenReturn(user1);
        when(userRepository.findByEmail("wrong_email"))
                .thenReturn(null);
    }

    @Test
    public void registerUser() throws UserExistsException {
        User aMockUser = new User();
        aMockUser.setFirstName("Alexander");
//        when(userRepository.save(any(User.class))).thenReturn(aMockUser);
        User newUser = userService.registerUser(null);
        assertEquals("Alexander", newUser.getFirstName());
    }


    @Test
    public void removeUserById() {


    }

    @Test
    public void update() {
    }

    @Test
    public void getAllUsers() {

//        when(userRepository.findAll()).thenReturn(Stream.of(
//                new User("Alexander","Teklemariam","abc@hotmail.com","password",DATE),
//                new User("Test","Testsson","abc@hotmail.com","password",DATE))
//                        .collect(Collectors.toList()));
        assertEquals(3,userService.getAllUsers().size());
    }

    @Test
    public void getUserById() throws UserNotFoundException {
        User test = new User();
        Long id = 1L;
        test.setId(id);
//        when(userRepository.findById(1L)).thenReturn(Optional.of(
//               test));

        User newUser = userService.getUserById(id);
        assertEquals(test, newUser);
    }

    @Test
    public void getUserByEmail() throws UserNotFoundException {
        String email="anders@abc.com";
//        when(userRepository.findByEmail(email))
//                .thenReturn(test);
       User foundUser= userService.getUserByEmail(email);
        assertThat(foundUser.getEmail()).isEqualTo(email);
    }


    @Test(expected = UserNotFoundException.class)
    public void TestFindingNonExistentUserByEmail() throws UserNotFoundException {
        String email="wrong_email";
//        when(userRepository.findByEmail(email))
//                .thenReturn(test);
        User foundUser= userService.getUserByEmail(email);
//        assertThat(foundUser.getEmail()).isEqualTo(email);jag
    }


    @Test(expected = UserNotFoundException.class)
    public void whenInValidName_thenUserShouldNotBeFound() throws UserNotFoundException {
        User fromDb = userService.getUserByEmail("wrong_email");
        verifyFindByEmailIsCalledOnce("wrong_name");
    }

    private void  verifyFindByEmailIsCalledOnce(String email) {
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByEmail(email);
        Mockito.reset(userRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(userRepository);
    }

    private void verifyFindAllUsersIsCalledOnce() {
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(userRepository);
    }
}
