package se.yrgo.budgetplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.exceptions.EntityNotFoundException;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseCategory;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;
import se.yrgo.budgetplanner.service.ExpenseService;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/add")
    Expense addExpense(@RequestBody Expense expense) {
        expenseService.saveExpense(expense);
        return expense;
    }

    @DeleteMapping("/delete/{id}")
    void deleteExpense(@PathVariable Long id) throws EntityNotFoundException {
        expenseService.deleteExpense(id);
        System.out.println("Deleted expense.");
    }


    @GetMapping("/getall")
    List<Expense> getAllExpenses() throws EntityNotFoundException {
        return expenseService.getAllExpenses();
    }

    @PutMapping("/update/{id}")
    Expense updateExpense(@RequestBody Expense expense, @PathVariable Long id) throws EntityNotFoundException {
        expenseService.updateExpense(expense, id);
        return expense;
    }


//    @GetMapping("/getbetweendates/{start}/{end}")
//    List<Expense> getExpensesBetweenDates(@PathVariable LocalDate start, @PathVariable LocalDate end) {
//        return expenseService.getExpensesBetweenDates(start, end);
//    }
//
//    @GetMapping("/getbystatus/{status}")
//    List<Expense> getExpensesByUsernameAndStatus(@PathVariable ExpenseStatus status) {
//
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//
//        return expenseService.getExpensesByStatus(status);
//    }
//
//
//
//    @GetMapping("/getbycategory/{category}")
//    List<Expense> getExpensesByCategory(@PathVariable ExpenseCategory category) {
//        return expenseService.getExpensesByCategory(category);
//    }

    @GetMapping("/gettotal")
    Long getTotal() {
        return expenseService.getTotal();
    }

    @GetMapping("/gettotalbystatus/{status}")
    Long getTotalByStatus(@PathVariable ExpenseStatus status) {
        return expenseService.getTotalByStatus(status);
    }

    @GetMapping("/gettotalbycategory/{category}")
    Long getTotalByCategory(@PathVariable ExpenseCategory category) {
        return expenseService.getTotalByCategory(category);
    }


}
