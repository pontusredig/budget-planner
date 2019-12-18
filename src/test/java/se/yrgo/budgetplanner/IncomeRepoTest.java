package se.yrgo.budgetplanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeCategory;
import se.yrgo.budgetplanner.model.income.IncomeStatus;
import se.yrgo.budgetplanner.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IncomeRepoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private IncomeRepository incomeRepository;

    final BigDecimal AMOUNT = new BigDecimal("2515.74");
    final IncomeCategory CATEGORY = IncomeCategory.SALARY;
    final String NAME = "Overtime";
    final LocalDate DATE = LocalDate.now();
    final IncomeStatus STATUS = IncomeStatus.EXPENDABLE;

    @Before
    public void setUp() {

        Income testIncome = new Income();
        testIncome.setAmount(AMOUNT);
        testIncome.setIncomeCategory(CATEGORY);
        testIncome.setName(NAME);
        testIncome.setDate(DATE);
        testIncome.setIncomeStatus(STATUS);
        testEntityManager.persist(testIncome);
    }

    @Test
    public void findExpenseThenVerifyAmount() {

        Income foundIncome = incomeRepository.getOne(1L);

        assertThat(foundIncome.getAmount()).isEqualTo(AMOUNT);
        assertThat(foundIncome.getIncomeCategory()).isEqualTo(CATEGORY);
        assertThat(foundIncome.getName()).isEqualTo(NAME);
        assertThat(foundIncome.getDate()).isEqualTo(DATE);
        assertThat(foundIncome.getIncomeStatus()).isEqualTo(STATUS);


    }


}
