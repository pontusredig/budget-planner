package se.yrgo.budgetplanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseCategory;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;
import se.yrgo.budgetplanner.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExpenseRepoTest {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ExpenseRepository expenseRepository;

    final BigDecimal AMOUNT = new BigDecimal("9876.54");
    final ExpenseCategory CATEGORY = ExpenseCategory.BILLS;
    final String NAME = "Rent";
    final LocalDate DATE = LocalDate.now();
    final LocalDate DUE_DATE = LocalDate.parse("2019-11-30");
    final ExpenseStatus STATUS = ExpenseStatus.PAID;

    @Before
    public void setUp() {

        Expense testExpense = new Expense();
        testExpense.setAmount(AMOUNT);
        testExpense.setExpenseCategory(CATEGORY);
        testExpense.setName(NAME);
        testExpense.setDate(DATE);
        testExpense.setDueDate(DUE_DATE);
        testExpense.setExpenseStatus(STATUS);
        testEntityManager.persist(testExpense);

    }


    @Test
    public void findExpenseThenVerifyData() {

        Expense foundExpense = expenseRepository.getOne(1L);


        assertThat(foundExpense.getAmount()).isEqualTo(AMOUNT);
        assertThat(foundExpense.getExpenseCategory()).isEqualTo(CATEGORY);
        assertThat(foundExpense.getName()).isEqualTo(NAME);
        assertThat(foundExpense.getDate()).isEqualTo(DATE);
        assertThat(foundExpense.getDueDate()).isEqualTo(DUE_DATE);
        assertThat(foundExpense.getExpenseStatus()).isEqualTo(STATUS);

    }
}