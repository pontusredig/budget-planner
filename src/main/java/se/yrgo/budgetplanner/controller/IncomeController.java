package se.yrgo.budgetplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.service.IncomeService;

import java.util.List;

@RestController
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @PostMapping("/addincome")
    Income addIncome(@RequestBody Income income) {
        incomeService.saveIncome(income);
        return income;
    }

    @DeleteMapping("/deleteincome/{id}")
    void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        System.out.println("Deleted income.");
    }

    @GetMapping("/getallincomes")
    List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @PutMapping("/updateincome/{id}")
    Income updateIncome(@RequestBody Income income, @PathVariable Long id) {
        incomeService.updateIncome(income, id);
        return income;
    }

}