package se.yrgo.budgetplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    public void saveExpenses(Expense expense) {
        expenseRepository.save(expense);
    }

}
