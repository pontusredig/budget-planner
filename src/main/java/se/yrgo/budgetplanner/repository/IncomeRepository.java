package se.yrgo.budgetplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.income.Income;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByDate(LocalDate date);

    List<Income> findAllByDateBetween(LocalDate start, LocalDate end);


}
