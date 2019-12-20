package se.yrgo.budgetplanner.model.income;

import lombok.Data;
import se.yrgo.budgetplanner.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Amount is a required field.")
    private BigDecimal amount;
    @NotNull(message = "Category is a required field.")
    @Enumerated(EnumType.STRING)
    private IncomeCategory incomeCategory;
    private String name;
    private LocalDate date;
    @NotNull(message = "Status is a required field.")
    @Enumerated(EnumType.STRING)
    private IncomeStatus incomeStatus;
    @ManyToOne
    @JoinColumn
    private User user;

    public Income(BigDecimal amount, IncomeCategory incomeCategory, String name, LocalDate date, IncomeStatus incomeStatus) {
        this.amount = amount;
        this.incomeCategory = incomeCategory;
        this.name = name;
        this.date = date;
        this.incomeStatus = incomeStatus;
    }

    public Income() {
    }
}
