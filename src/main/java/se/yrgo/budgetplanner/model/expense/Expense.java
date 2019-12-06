package se.yrgo.budgetplanner.model.expense;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    private ExpenseCategory expenseCategory;
    private String name;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @Column(columnDefinition = "DATE")
    private LocalDate dueDate;
    private ExpenseStatus expenseStatus;


    public Expense(BigDecimal amount, ExpenseCategory expenseCategory, String name, LocalDate date, LocalDate dueDate,
                   ExpenseStatus expenseStatus) {
        this.amount = amount;
        this.expenseCategory = expenseCategory;
        this.name = name;
        this.date = date;
        this.dueDate = dueDate;
        this.expenseStatus = expenseStatus;
    }

    public Expense() {
    }
}
