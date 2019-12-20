package se.yrgo.budgetplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.budgetplanner.exceptions.EntityNotFoundException;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeCategory;
import se.yrgo.budgetplanner.model.income.IncomeStatus;
import se.yrgo.budgetplanner.service.IncomeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @PostMapping("/add")
    Income addIncome(@RequestBody Income income) {
        incomeService.saveIncome(income);
        return income;
    }

    @DeleteMapping("/delete/{id}")
    void deleteIncome(@PathVariable Long id) throws EntityNotFoundException {
        incomeService.deleteIncome(id);
        System.out.println("Deleted income.");
    }

    @GetMapping("/getall")
    List<Income> getAllIncomes() throws EntityNotFoundException {
        return incomeService.getAllIncomes();
    }


    @GetMapping("/getbyid/{id}")
    Income getIncomeById(@PathVariable Long id) throws EntityNotFoundException {
        return incomeService.getIncomeById(id);
    }

    @PutMapping("/update/{id}")
    Income updateIncome(@RequestBody Income income, @PathVariable Long id) throws EntityNotFoundException {
        incomeService.updateIncome(income, id);
        return income;
    }

    @GetMapping("/getbetweendates/{start}/{end}")
    List<Income> getIncomesByDateBetween(@PathVariable LocalDate start, @PathVariable LocalDate end)
            throws EntityNotFoundException {
        return incomeService.getIncomesBetweenDates(start, end);
    }

    @GetMapping("/getbystatus/{status}")
    List<Income> getIncomesByUsernameAndStatus(@PathVariable IncomeStatus status) throws EntityNotFoundException {
        return incomeService.getIncomesByStatus(status);
    }

    @GetMapping("/getbycategory/{category}")
    List<Income> getIncomesByCategory(@PathVariable IncomeCategory category) throws EntityNotFoundException {
        return incomeService.getIncomesByCategory(category);
    }

    @GetMapping("/gettotal")
    Long getTotal() throws EntityNotFoundException {
        return incomeService.getTotal();
    }

    @GetMapping("/gettotalbystatus/{status}")
    Long getTotalByStatus(@PathVariable IncomeStatus status) throws EntityNotFoundException {
        return incomeService.getTotalByStatus(status);
    }

    @GetMapping("/gettotalbycategory/{category}")
    Long getTotalByCategory(@PathVariable IncomeCategory category) throws EntityNotFoundException {
        return incomeService.getTotalByCategory(category);
    }


}
