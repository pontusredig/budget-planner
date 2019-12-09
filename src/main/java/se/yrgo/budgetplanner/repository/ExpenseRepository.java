package se.yrgo.budgetplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.expense.Expense;
import se.yrgo.budgetplanner.model.expense.ExpenseCategory;
import se.yrgo.budgetplanner.model.expense.ExpenseStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByDate(LocalDate date);

    List<Expense> findAllByDateBetween(LocalDate start, LocalDate end);

    List<Expense> findAllByExpenseStatus(ExpenseStatus status);

    List<Expense> findAllByExpenseCategory(ExpenseCategory category);

    @Query(value = "SELECT sum(amount) FROM Expense")
    Long totalAmount();

    @Query("SELECT sum(exp.amount) from Expense exp where exp.expenseStatus = ?1")
    Long totalAmountByStatus(ExpenseStatus status);

    @Query("SELECT sum(exp.amount) from Expense exp where exp.expenseCategory = ?1")
    Long totalAmountByCategory(ExpenseCategory category);

}

