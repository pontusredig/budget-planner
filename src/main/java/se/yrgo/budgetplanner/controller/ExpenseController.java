package se.yrgo.budgetplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.service.ExpenseService;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/addexpense")
    Expense addExpense (@RequestBody Expense expense) {
        expenseService.saveExpenses(expense);
        return expense;
    }


}
