package app.repository;

import app.model.Users;
import app.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UsersRepository extends  JpaRepository<Users, Integer> {
    Users findByEmail(String email);

    @Query(value = "SELECT DISTINCT u FROM Users u JOIN FETCH u.teams")
    Set<Users> findAllUsers();

    List<Users> findUserByRoles(UserType role);

    Users findUsersByUserId(Integer id);
}
