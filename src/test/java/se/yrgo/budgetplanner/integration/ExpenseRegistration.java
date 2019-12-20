package se.yrgo.budgetplanner.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseCategory;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.ExpenseRepository;
import se.yrgo.budgetplanner.security.jwt.config.JwtTokenUtil;
import se.yrgo.budgetplanner.security.jwt.model.JwtRequest;
import se.yrgo.budgetplanner.security.jwt.model.JwtResponse;
import se.yrgo.budgetplanner.security.jwt.service.JwtUserDetailsService;
import se.yrgo.budgetplanner.service.UserService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ExpenseRegistration {

    final BigDecimal AMOUNT = new BigDecimal("9876.54");
    final ExpenseCategory CATEGORY = ExpenseCategory.BILLS;
    final String NAME = "Rent";
    final LocalDate DATE = LocalDate.now();
    final LocalDate DUE_DATE = LocalDate.parse("2019-11-30");
    final ExpenseStatus STATUS = ExpenseStatus.PAID;

    @LocalServerPort
    private int port;

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUserDetailsService userDetailsService;
    @Autowired
    UserService userService;


    @Test
    public void addANewExpenseVerifyExpenseIsStored() throws Exception {

        // Register user for test
        User testUser = new User("testuser@mail.com", "password");
        HttpEntity<User> requestUser = new HttpEntity<>(testUser);
        (new RestTemplate()).postForEntity("http://localhost:" + port + "/users/register", requestUser, String.class);

        // Generate JWT for test
        JwtRequest authenticationRequest = new JwtRequest("testuser@mail.com", "password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(token);


        Expense testExpense = new Expense();
        testExpense.setAmount(AMOUNT);
        testExpense.setExpenseCategory(CATEGORY);
        testExpense.setName(NAME);
        testExpense.setDate(DATE);
        testExpense.setDueDate(DUE_DATE);
        testExpense.setExpenseStatus(STATUS);

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + jwtResponse.getToken());


        HttpEntity<Expense> request = new HttpEntity<>(testExpense, header);

        ResponseEntity<Expense> result = (new RestTemplate())
                .postForEntity("http://localhost:" + port + "/expense/add", request, Expense.class);

        Expense verifyThisExpense = expenseRepository.getOne(3L);

        assertEquals(verifyThisExpense.getAmount(), AMOUNT);
        assertEquals(verifyThisExpense.getExpenseCategory(), CATEGORY);
        assertEquals(verifyThisExpense.getName(), NAME);
        assertEquals(verifyThisExpense.getDate(), DATE);
        assertEquals(verifyThisExpense.getDueDate(), DUE_DATE);
        assertEquals(verifyThisExpense.getExpenseStatus(), STATUS);
        assertEquals(verifyThisExpense.getUser().getEmail(), testUser.getEmail());

    }

}
