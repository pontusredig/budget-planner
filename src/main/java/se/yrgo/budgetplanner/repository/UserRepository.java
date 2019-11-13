package se.yrgo.budgetplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.budgetplanner.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
