package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseCategory;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;
import se.yrgo.budgetplanner.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    BalanceService balanceService;

    @Autowired
    UserService userService;

    public Expense saveExpense(Expense expense) {


        expense.setUser(userService.getLoggedInUser());
        expense.setDate(LocalDate.now());

        if (expense.getExpenseStatus() == ExpenseStatus.PAID) {
            balanceService.subtractExpenseFromBalance(expense);
        }

        expenseRepository.save(expense);
        return expense;
    }


    public void deleteExpense(Long id) {
        Expense deleteThisExpense = expenseRepository.getOne(id);
        expenseRepository.delete(deleteThisExpense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense updateExpense(Expense expense, Long id) {
        Expense updateThisExpense = expenseRepository.findByIdAndUser(id);


        // If expense is changed from UNPAID to PAID, subtract amount from balance
        if (updateThisExpense.getExpenseStatus() == ExpenseStatus.UNPAID
                && expense.getExpenseStatus() == ExpenseStatus.PAID) {
            balanceService.subtractExpenseFromBalance(expense);
        }

        expense.setUser(userService.getLoggedInUser());
        expense.setId(id);
        expenseRepository.save(expense);
        return expense;
    }


    public Expense getExpenseById(Long id) {
        return expenseRepository.getOne(id);
    }


    public List<Expense> getExpensesBetweenDates(LocalDate start, LocalDate end) {
        return expenseRepository.findAllByDateBetween(start, end);
    }

    public List<Expense> getExpensesByStatus(ExpenseStatus status) {
        return expenseRepository.findAllByExpenseStatus(status);
    }

    public List<Expense> getExpensesByCategory(ExpenseCategory category) {
        return expenseRepository.findAllByExpenseCategory(category);
    }

    public Long getTotal() {
        return expenseRepository.totalAmount();
    }

    public Long getTotalByStatus(ExpenseStatus status) {
        return expenseRepository.totalAmountByStatus(status);
    }

    public Long getTotalByCategory(ExpenseCategory category) {
        return expenseRepository.totalAmountByCategory(category);
    }


}
