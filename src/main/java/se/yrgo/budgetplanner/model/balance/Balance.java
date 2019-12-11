package se.yrgo.budgetplanner.model.balance;

import lombok.Data;
import se.yrgo.budgetplanner.model.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private BalanceCategory balanceCategory;
    private LocalDate date;
    @ManyToOne
    @JoinColumn
    private User user;


    public Balance(BigDecimal amount, BalanceCategory balanceCategory) {
        this.amount = amount;
        this.balanceCategory = balanceCategory;
    }

    public Balance() {
    }
}
