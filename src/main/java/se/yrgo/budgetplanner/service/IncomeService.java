package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeStatus;
import se.yrgo.budgetplanner.repository.IncomeRepository;

import java.time.LocalDate;
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
        income.setDate(LocalDate.now());
        balanceService.addIncomeToBalance(income);
        incomeRepository.save(income);
        return income;
    }

    public void deleteIncome(Long id) {
        Income deleteThisIncome = incomeRepository.getOne(id);
        incomeRepository.delete(deleteThisIncome);
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income updateIncome(Income income, Long id) {
        incomeRepository.findById(id);
        income.setId(id);
        incomeRepository.save(income);
        return income;
    }

    public Income getIncomeById(Long id) {
        return incomeRepository.getOne(id);
    }


    public List<Income> getIncomesByDate(LocalDate date) {
        return incomeRepository.findAllByDate(date);
    }

    public List<Income> getIncomesBetweenDates(LocalDate start, LocalDate end) {
        return incomeRepository.findAllByDateBetween(start, end);
    }

    public Long getTotal() {
        return incomeRepository.totalAmount();
    }

    public Long getTotalByStatus(IncomeStatus status) {
        return incomeRepository.totalAmountByStatus(status);
    }


}
