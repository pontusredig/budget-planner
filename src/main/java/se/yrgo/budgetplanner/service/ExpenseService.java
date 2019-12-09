package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;
import se.yrgo.budgetplanner.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        expense.setAddedOn(LocalDate.now());
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
        expenseRepository.findById(id);
        expense.setId(id);
        expenseRepository.save(expense);
        return expense;
    }


    public Expense getExpenseById(Long id) {
        return expenseRepository.getOne(id);
    }

    public List<Expense> getExpensesByDate(LocalDate date) {
        return expenseRepository.findAllByDate(date);
    }

    public List<Expense> getExpensesBetweenDates(LocalDate start, LocalDate end) {
        return expenseRepository.findAllByDateBetween(start, end);
    }

    public List<Expense> getExpensesByStatus (ExpenseStatus status) {
        return expenseRepository.findAllByExpenseStatus(status);
    }



}
