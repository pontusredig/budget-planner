package se.yrgo.budgetplanner.expenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    public void saveExpenses(Expense expense) {
        expenseRepository.save(expense);
    }

}
