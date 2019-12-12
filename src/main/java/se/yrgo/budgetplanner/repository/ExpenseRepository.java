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


    @Query("SELECT exp FROM Expense exp WHERE exp.user.email = :#{ principal?.username }")
    List<Expense> findAll();

    List<Expense> findAllByDateBetween(LocalDate start, LocalDate end);

    List<Expense> findAllByExpenseStatus(ExpenseStatus status);

    List<Expense> findAllByExpenseCategory(ExpenseCategory category);

    @Query("SELECT exp FROM Expense exp WHERE exp.id =?1 AND exp.user.email = :#{ principal?.username }")
    Expense findByIdAndUser(Long id);

    @Query("SELECT SUM(exp.amount) FROM Expense exp WHERE exp.user.email = :#{ principal?.username }")
    Long totalAmount();

    @Query("SELECT SUM(exp.amount) FROM Expense exp WHERE exp.expenseStatus = ?1 " +
            "AND exp.user.email = :#{ principal?.username }")
    Long totalAmountByStatus(ExpenseStatus status);

    @Query("SELECT SUM(exp.amount) FROM Expense exp WHERE exp.expenseCategory = ?1 " +
            "AND exp.user.email = :#{ principal?.username }")
    Long totalAmountByCategory(ExpenseCategory category);

}

