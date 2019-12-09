package se.yrgo.budgetplanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeCategory;
import se.yrgo.budgetplanner.model.income.IncomeStatus;
import se.yrgo.budgetplanner.service.IncomeService;

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
    IncomeService incomeService;

    @Test
    public void addANewIncomeVerifyIncomeIsStored() throws Exception {

        Income testIncome = new Income();
        testIncome.setAmount(AMOUNT);
        testIncome.setIncomeCategory(CATEGORY);
        testIncome.setName(NAME);
        testIncome.setDate(DATE);
        testIncome.setIncomeStatus(STATUS);

        HttpEntity<Income> request = new HttpEntity<>(testIncome);

        ResponseEntity<Income> result = (new RestTemplate())
                .postForEntity("http://localhost:" + port + "/income/add", request, Income.class);

        Income verifyThisIncome = incomeService.getIncomeById(1L);

        assertEquals(verifyThisIncome.getAmount(), AMOUNT);
        assertEquals(verifyThisIncome.getIncomeCategory(), CATEGORY);
        assertEquals(verifyThisIncome.getName(), NAME);
        assertEquals(verifyThisIncome.getDate(), DATE);
        assertEquals(verifyThisIncome.getIncomeStatus(), STATUS);

    }

}
