package se.yrgo.budgetplanner.expenses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
