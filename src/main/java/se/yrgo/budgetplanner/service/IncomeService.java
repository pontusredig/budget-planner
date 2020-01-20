package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.exceptions.EntityNotFoundException;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeCategory;
import se.yrgo.budgetplanner.model.income.IncomeStatus;
import se.yrgo.budgetplanner.repository.IncomeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    BalanceService balanceService;

    @Autowired
    UserService userService;

    public Income saveIncome(Income income) {

        income.setUser(userService.getLoggedInUser());
        balanceService.addIncomeToBalance(income);
        incomeRepository.save(income);
        return income;
    }

    public void deleteIncome(Long id) {
        Income deleteThisIncome = incomeRepository.findByIdAndUser(id);

        if (deleteThisIncome == null) {
            throw new EntityNotFoundException();
        }

        balanceService.deleteIncomeFromBalance(deleteThisIncome);
        incomeRepository.delete(deleteThisIncome);
    }

    public List<Income> getAllIncomes() {
        List<Income> allIncomes = incomeRepository.findAll();

        if (allIncomes.isEmpty()) {
            throw new EntityNotFoundException("No expenses added yet!");
        }

        return allIncomes;
    }

    public Income updateIncome(Income income, Long id) {
        Income updateThisIncome = incomeRepository.findByIdAndUser(id);

        if (updateThisIncome == null) {
            throw new EntityNotFoundException();
        }

        income.setId(id);
        income.setUser(userService.getLoggedInUser());
        incomeRepository.save(income);
        return income;
    }

    public Income getIncomeById(Long id) {
        Income foundIncome = incomeRepository.findByIdAndUser(id);

        if (foundIncome == null) {
            throw new EntityNotFoundException();
        }

        return incomeRepository.getOne(id);
    }


    public List<Income> getIncomesBetweenDates(LocalDate start, LocalDate end) {
        List<Income> incomesBetweenDates = incomeRepository.findAllByDateBetween(start, end);

        if (incomesBetweenDates.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return incomesBetweenDates;
    }

    public List<Income> getIncomesByStatus(IncomeStatus status) {
        List<Income> incomesByStatus = incomeRepository.findAllByIncomeStatus(status);

        if (incomesByStatus.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return incomesByStatus;

    }

    public List<Income> getIncomesByCategory(IncomeCategory category) {
        List<Income> incomesByCategory = incomeRepository.findAllByIncomeCategory(category);

        if (incomesByCategory.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return incomesByCategory;
    }

    public Long getTotal() {

        Long totalAmount = incomeRepository.totalAmount();

        if (totalAmount == null) {
            throw new EntityNotFoundException();
        }

        return totalAmount;
    }

    public Long getTotalByStatus(IncomeStatus status) {

        Long totalByStatus = incomeRepository.totalAmountByStatus(status);

        if (totalByStatus == null) {
            throw new EntityNotFoundException();
        }

        return totalByStatus;
    }

    public Long getTotalByCategory(IncomeCategory category) {

        Long totalByCategory = incomeRepository.totalAmountByCategory(category);

        if (totalByCategory == null) {
            throw new EntityNotFoundException();
        }

        return totalByCategory;

    }

    public List<IncomeCategory> getAllCategories() {

        List<IncomeCategory> categories = new ArrayList<>();

        for (IncomeCategory ctg : IncomeCategory.values()) {
            categories.add(ctg);
        }

        return categories;
    }


}
