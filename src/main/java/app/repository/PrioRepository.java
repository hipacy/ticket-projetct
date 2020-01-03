package app.repository;


import app.model.Prio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrioRepository extends JpaRepository<Prio, Integer> {

    @Query(value = "select * from Prio", nativeQuery = true)
    List<Prio> findAllPrios();

    @Query("select t from Prio t where t.prioId = ?1")
    Prio findPrioById(Integer id);

}
