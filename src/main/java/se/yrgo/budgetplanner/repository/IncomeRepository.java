package se.yrgo.budgetplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeCategory;
import se.yrgo.budgetplanner.model.income.IncomeStatus;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {



    @Query("SELECT inc FROM Income inc WHERE inc.user.email = :#{ principal?.username }")
    List<Income> findAll();

    @Query("SELECT inc FROM Income inc WHERE inc.id =?1 AND inc.user.email = :#{ principal?.username }")
    Income findByIdAndUser(Long id);

    @Query("SELECT SUM(inc.amount) FROM Income inc WHERE inc.user.email = :#{ principal?.username }")
    Long totalAmount();

    @Query("SELECT SUM(inc.amount) FROM Income inc WHERE inc.incomeStatus = ?1 " +
            "AND inc.user.email = :#{ principal?.username }")
    Long totalAmountByStatus(IncomeStatus status);

    @Query("SELECT SUM(inc.amount) FROM Income inc WHERE inc.incomeCategory = ?1 " +
            "AND inc.user.email = :#{ principal?.username }")
    Long totalAmountByCategory(IncomeCategory category);


//    List<Income> findAllByDate(LocalDate date);
//
//    List<Income> findAllByDateBetween(LocalDate start, LocalDate end);
//
//    @Query(value = "SELECT sum(amount) FROM Income")
//    Long totalAmount();
//
//    @Query("SELECT sum(inc.amount) from Income inc where inc.incomeStatus = ?1")
//    Long totalAmountByStatus(IncomeStatus status);


}
