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
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseCategory;
import se.yrgo.budgetplanner.service.ExpenseService;

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

    @LocalServerPort
    private int port;

    @Autowired
    ExpenseService expenseService;

    @Test
    public void addANewExpenseVerifyExpenseIsStored() throws Exception {

        Expense testExpense = new Expense();
        testExpense.setAmount(AMOUNT);
        testExpense.setExpenseCategory(CATEGORY);
        testExpense.setName(NAME);
        testExpense.setDate(DATE);

        HttpEntity<Expense> request = new HttpEntity<>(testExpense);

        ResponseEntity<Expense> result = (new RestTemplate())
                .postForEntity("http://localhost:" + port + "/addexpense", request, Expense.class);

        Expense verifyThisExpense = expenseService.getExpenseById(1L);

        assertEquals(verifyThisExpense.getAmount(), AMOUNT);
        assertEquals(verifyThisExpense.getExpenseCategory(), CATEGORY);
        assertEquals(verifyThisExpense.getName(), NAME);
        assertEquals(verifyThisExpense.getDate(), DATE);


    }

}
