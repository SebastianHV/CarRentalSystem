package mx.edu.j2se.hernandezv.CarRentalSystem.Repository;

import mx.edu.j2se.hernandezv.CarRentalSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
