package se.yrgo.budgetplanner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.balance.Balance;
import se.yrgo.budgetplanner.model.balance.BalanceCategory;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query("SELECT bal FROM Balance bal WHERE bal.user.email = :#{ principal?.username }")
    List<Balance> findAll();

    @Query(value = "SELECT bal.amount FROM Balance bal WHERE bal.balanceCategory = ?1 " +
            "AND bal.user.email = :#{ principal?.username } ORDER BY bal.id DESC")
    Page<BigDecimal> getCurrentBalance(Pageable pageable, BalanceCategory balanceCategory);


}
