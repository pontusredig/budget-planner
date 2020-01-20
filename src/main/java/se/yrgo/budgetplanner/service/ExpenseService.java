package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.exceptions.EntityNotFoundException;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseCategory;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;
import se.yrgo.budgetplanner.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
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

        if (expense.getExpenseStatus() == ExpenseStatus.PAID) {
            balanceService.subtractExpenseFromBalance(expense);
        }

        expenseRepository.save(expense);
        return expense;
    }


    public void deleteExpense(Long id) {
        Expense deleteThisExpense = expenseRepository.findByIdAndUser(id);

        if (deleteThisExpense == null) {
            throw new EntityNotFoundException();
        }


        balanceService.deleteExpenseFromBalance(deleteThisExpense);
        expenseRepository.delete(deleteThisExpense);
    }

    public List<Expense> getAllExpenses() {

        List<Expense> allExpenses = expenseRepository.findAll();

        if (allExpenses.isEmpty()) {
            throw new EntityNotFoundException("No expenses added yet!");
        }

        return allExpenses;
    }

    public Expense updateExpense(Expense expense, Long id) {
        Expense updateThisExpense = expenseRepository.findByIdAndUser(id);

        if (updateThisExpense == null) {
            throw new EntityNotFoundException();
        }


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
        Expense foundExpense = expenseRepository.findByIdAndUser(id);

        if (foundExpense == null) {
            throw new EntityNotFoundException();
        }

        return foundExpense;
    }


    public List<Expense> getExpensesBetweenDates(LocalDate start, LocalDate end) {
        List<Expense> expensesBetweenDates = expenseRepository.findAllByDateBetween(start, end);

        if (expensesBetweenDates.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return expensesBetweenDates;
    }

    public List<Expense> getExpensesByStatus(ExpenseStatus status) {
        List<Expense> expensesByStatus = expenseRepository.findAllByExpenseStatus(status);

        if (expensesByStatus.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return expensesByStatus;

    }

    public List<Expense> getExpensesByCategory(ExpenseCategory category) {
        List<Expense> expensesByCategory = expenseRepository.findAllByExpenseCategory(category);

        if (expensesByCategory.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return expensesByCategory;
    }

    public Long getTotal() {

        Long totalAmount = expenseRepository.totalAmount();

        if (totalAmount == null) {
            throw new EntityNotFoundException();
        }

        return totalAmount;
    }

    public Long getTotalByStatus(ExpenseStatus status) {

        Long totalByStatus = expenseRepository.totalAmountByStatus(status);

        if (totalByStatus == null) {
            throw new EntityNotFoundException();
        }

        return totalByStatus;
    }

    public Long getTotalByCategory(ExpenseCategory category) {

        Long totalByCategory = expenseRepository.totalAmountByCategory(category);

        if (totalByCategory == null) {
            throw new EntityNotFoundException();
        }

        return totalByCategory;

    }

    public List<ExpenseCategory> getAllCategories() {

        List<ExpenseCategory> categories = new ArrayList<>();

        for (ExpenseCategory ctg : ExpenseCategory.values()) {
            categories.add(ctg);
        }

        return categories;
    }
}



