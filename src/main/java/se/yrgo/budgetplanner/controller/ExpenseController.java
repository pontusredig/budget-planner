package se.yrgo.budgetplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.service.ExpenseService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/addexpense")
    Expense addExpense(@RequestBody Expense expense) {
        expenseService.saveExpense(expense);
        return expense;
    }

    @DeleteMapping("/deleteexpense/{id}")
    void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        System.out.println("Deleted expense.");
    }

    @GetMapping("/getallexpenses")
    List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PutMapping("/updateexpense/{id}")
    Expense updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
        expenseService.updateExpense(expense, id);
        return expense;
    }

    @GetMapping("/getbydate/{date}")
    List<Expense> getExpenseByDate(@PathVariable LocalDate date) {
        return expenseService.getExpensesByDate(date);
    }

    @GetMapping("/getbetweendates/{start}/{end}")
    List<Expense> getExpenseByDateBetween(@PathVariable LocalDate start, @PathVariable LocalDate end) {
        return expenseService.getExpensesBetweenDates(start, end);
    }


}
