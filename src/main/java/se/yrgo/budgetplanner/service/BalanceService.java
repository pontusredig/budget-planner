package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.balance.Balance;
import se.yrgo.budgetplanner.model.balance.BalanceCategory;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeStatus;
import se.yrgo.budgetplanner.repository.BalanceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BalanceService {

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    UserService userService;

    public Balance saveBalance(Balance balance) {

        balance.setUser(userService.getLoggedInUser());
        balance.setDate(LocalDate.now());
        balanceRepository.save(balance);
        return balance;
    }

    public Balance addIncomeToBalance(Income income) {
        Balance newBalance = new Balance();


        if (income.getIncomeStatus() == IncomeStatus.EXPENDABLE) {
            newBalance.setBalanceCategory(BalanceCategory.EXPENDABLE);
            newBalance.setAmount(getCurrentBalance(BalanceCategory.EXPENDABLE).add(income.getAmount()));
        } else {
            newBalance.setBalanceCategory(BalanceCategory.SAVINGS);
            newBalance.setAmount(getCurrentBalance(BalanceCategory.SAVINGS).add(income.getAmount()));
        }

        saveBalance(newBalance);
        return newBalance;

    }

    public Balance subtractExpenseFromBalance(Expense expense) {
        Balance newBalance = new Balance();
        newBalance.setBalanceCategory(BalanceCategory.EXPENDABLE);
        newBalance.setAmount(getCurrentBalance(BalanceCategory.EXPENDABLE).subtract(expense.getAmount()));
        saveBalance(newBalance);
        return newBalance;
    }

    public List<Balance> getAll() {
        return balanceRepository.findAll();

    }

    public BigDecimal getCurrentBalance(BalanceCategory balanceCategory) {
        Pageable pageable = PageRequest.of(0, 1);
        List<BigDecimal> currentBalance = balanceRepository.getCurrentBalance(pageable, balanceCategory).getContent();
        if (balanceRepository.getCurrentBalance(pageable, balanceCategory).getContent().isEmpty()) {
            return new BigDecimal("0");
        } else {
            return currentBalance.get(0);
        }

    }


}
