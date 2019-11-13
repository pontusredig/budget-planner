package se.yrgo.budgetplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.income.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

}
