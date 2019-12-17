package se.yrgo.budgetplanner.model.income;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
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
    @NumberFormat
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category is a required field.")
    private IncomeCategory incomeCategory;
    private String name;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is a required field.")
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
