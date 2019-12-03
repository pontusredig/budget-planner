package se.yrgo.budgetplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;
import se.yrgo.budgetplanner.service.ExpenseService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/expense/add")
    Expense addExpense(@RequestBody Expense expense) {
        expenseService.saveExpense(expense);
        return expense;
    }

    @DeleteMapping("/expense/delete/{id}")
    void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        System.out.println("Deleted expense.");
    }

    @GetMapping("/expense/getall")
    List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PutMapping("/expense/update/{id}")
    Expense updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
        expenseService.updateExpense(expense, id);
        return expense;
    }

    @GetMapping("/expense/getbydate/{date}")
    List<Expense> getExpensesByDate(@PathVariable LocalDate date) {
        return expenseService.getExpensesByDate(date);
    }

    @GetMapping("/expense/getbetweendates/{start}/{end}")
    List<Expense> getExpensesBetweenDates(@PathVariable LocalDate start, @PathVariable LocalDate end) {
        return expenseService.getExpensesBetweenDates(start, end);
    }

    @GetMapping("/expense/getbystatus/{status}")
    List<Expense> getExpensesByStatus(@PathVariable ExpenseStatus status) {
        return expenseService.getExpensesByStatus(status);
    }


}
