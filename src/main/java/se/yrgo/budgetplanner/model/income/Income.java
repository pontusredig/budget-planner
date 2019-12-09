package se.yrgo.budgetplanner.model.income;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private LocalDate addedOn;

    public Income(BigDecimal amount, IncomeCategory incomeCategory, String name) {
        this.amount = amount;
        this.incomeCategory = incomeCategory;
        this.name = name;
    }

    public Income() {
    }
}
