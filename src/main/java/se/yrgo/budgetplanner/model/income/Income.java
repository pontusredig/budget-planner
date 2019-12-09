package se.yrgo.budgetplanner.model.income;

import lombok.Data;
import se.yrgo.budgetplanner.model.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    private IncomeCategory incomeCategory;
    private String name;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private IncomeStatus incomeStatus;
    @ManyToOne
    @JoinColumn
    private User user;

    public Income(BigDecimal amount, IncomeCategory incomeCategory, String name) {
        this.amount = amount;
        this.incomeCategory = incomeCategory;
        this.name = name;
    }

    public Income() {
    }
}
