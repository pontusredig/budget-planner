package se.yrgo.budgetplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.service.IncomeService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @PostMapping("/income/add")
    Income addIncome(@RequestBody Income income) {
        incomeService.saveIncome(income);
        return income;
    }

    @DeleteMapping("/income/delete/{id}")
    void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        System.out.println("Deleted income.");
    }

    @GetMapping("/income/getall")
    List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @PutMapping("/income/update/{id}")
    Income updateIncome(@RequestBody Income income, @PathVariable Long id) {
        incomeService.updateIncome(income, id);
        return income;
    }

    @GetMapping("/income/getbydate/{date}")
    List<Income> getIncomesByDate(@PathVariable LocalDate date) {
        return incomeService.getIncomesByDate(date);
    }

    @GetMapping("/income/getbetweendates/{start}/{end}")
    List<Income> getIncomesByDateBetween(@PathVariable LocalDate start, @PathVariable LocalDate end) {
        return incomeService.getIncomesBetweenDates(start, end);
    }




}
