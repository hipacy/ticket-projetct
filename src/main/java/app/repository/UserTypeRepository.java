package app.repository;

import app.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

    UserType findByUserType(String type);

    @Query(value = "select * from UserType", nativeQuery = true)
    List<UserType> findAllRoles();


}