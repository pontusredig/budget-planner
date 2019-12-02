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

    public Expense(BigDecimal amount, ExpenseCategory expenseCategory, String name, LocalDate date) {
        this.amount = amount;
        this.expenseCategory = expenseCategory;
        this.name = name;
        this.date = date;
    }

    public Expense() {
    }
}
