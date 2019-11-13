package se.yrgo.budgetplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.expense.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}

