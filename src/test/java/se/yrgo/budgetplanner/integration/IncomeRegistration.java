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
import se.yrgo.budgetplanner.model.balance.Balance;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeCategory;
import se.yrgo.budgetplanner.model.income.IncomeStatus;
import se.yrgo.budgetplanner.model.user.User;
import se.yrgo.budgetplanner.repository.BalanceRepository;
import se.yrgo.budgetplanner.repository.IncomeRepository;
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
public class IncomeRegistration {

    final BigDecimal AMOUNT = new BigDecimal("8765.43");
    final IncomeCategory CATEGORY = IncomeCategory.SALARY;
    final String NAME = "Overtime";
    final LocalDate DATE = LocalDate.now();
    final IncomeStatus STATUS = IncomeStatus.EXPENDABLE;

    @LocalServerPort
    private int port;

    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    BalanceRepository balanceRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUserDetailsService userDetailsService;
    @Autowired
    UserService userService;

    @Test
    public void addANewIncomeVerifyIncomeIsStored() throws Exception {

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


        Income testIncome = new Income();
        testIncome.setAmount(AMOUNT);
        testIncome.setIncomeCategory(CATEGORY);
        testIncome.setName(NAME);
        testIncome.setDate(DATE);
        testIncome.setIncomeStatus(STATUS);

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + jwtResponse.getToken());

        HttpEntity<Income> request = new HttpEntity<>(testIncome, header);

        ResponseEntity<Income> result = (new RestTemplate())
                .postForEntity("http://localhost:" + port + "/income/add", request, Income.class);

        Income verifyThisIncome = incomeRepository.getOne(3L);
        Balance verifyThisBalance = balanceRepository.getOne(2L);

        // Verify Income object has been stored correctly
        assertEquals(verifyThisIncome.getAmount(), AMOUNT);
        assertEquals(verifyThisIncome.getIncomeCategory(), CATEGORY);
        assertEquals(verifyThisIncome.getName(), NAME);
        assertEquals(verifyThisIncome.getDate(), DATE);
        assertEquals(verifyThisIncome.getIncomeStatus(), STATUS);
        assertEquals(verifyThisIncome.getUser().getEmail(), testUser.getEmail());

        // Verify that storing expandable Income updates Balance accordingly
        assertEquals(verifyThisIncome.getAmount(), verifyThisBalance.getAmount());
        assertEquals(verifyThisIncome.getDate(), verifyThisBalance.getDate());
        assertEquals(verifyThisIncome.getUser().getEmail(), verifyThisBalance.getUser().getEmail());

    }

}
