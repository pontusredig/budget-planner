package se.yrgo.budgetplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.income.Income;
import se.yrgo.budgetplanner.model.income.IncomeStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {



    List<Income> findAllByDate(LocalDate date);

    List<Income> findAllByDateBetween(LocalDate start, LocalDate end);

    @Query(value = "SELECT sum(amount) FROM Income")
    Long totalAmount();

    @Query("SELECT sum(inc.amount) from Income inc where inc.incomeStatus = ?1")
    Long totalAmountByStatus(IncomeStatus status);


}
