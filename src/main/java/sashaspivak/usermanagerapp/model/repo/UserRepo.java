package sashaspivak.usermanagerapp.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sashaspivak.usermanagerapp.model.User;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByUserName(String userName);
}
