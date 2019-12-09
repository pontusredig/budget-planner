package se.yrgo.budgetplanner.model.expense;

import lombok.Data;
import se.yrgo.budgetplanner.model.user.User;

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
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    private String name;
    private LocalDate date;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private ExpenseStatus expenseStatus;
    @ManyToOne
    @JoinColumn
    private User user;


    public Expense(BigDecimal amount, ExpenseCategory expenseCategory, String name, LocalDate dueDate,
                   ExpenseStatus expenseStatus) {
        this.amount = amount;
        this.expenseCategory = expenseCategory;
        this.name = name;
        this.dueDate = dueDate;
        this.expenseStatus = expenseStatus;
    }

    public Expense() {
    }
}
