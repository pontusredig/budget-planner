package se.yrgo.budgetplanner.model.expense;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    private ExpenseCategory expenseCategory;
    private String name;
    private String date;

    public Expense(BigDecimal amount, ExpenseCategory expenseCategory, String name, String date) {
        this.amount = amount;
        this.expenseCategory = expenseCategory;
        this.name = name;
        this.date = date;
    }
}
