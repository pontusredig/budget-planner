package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.exceptions.EntityNotFoundException;
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

        if (deleteThisIncome == null) {
            throw new EntityNotFoundException();
        }

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

    public Long getTotal() {
        return incomeRepository.totalAmount();
    }

    public Long getTotalByStatus(IncomeStatus status) {
        return incomeRepository.totalAmountByStatus(status);
    }

    //    public List<Income> getIncomesByDate(LocalDate date) {
//        return incomeRepository.findAllByDate(date);
//    }
//
//    public List<Income> getIncomesBetweenDates(LocalDate start, LocalDate end) {
//        return incomeRepository.findAllByDateBetween(start, end);
//    }


}
