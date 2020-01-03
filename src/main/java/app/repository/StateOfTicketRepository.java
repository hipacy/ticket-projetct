package app.repository;

import app.model.StateOfTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateOfTicketRepository extends JpaRepository<StateOfTicket, Integer> {

    @Query(value = "select * from StateOfTicket", nativeQuery = true)
    List<StateOfTicket> findAllStateOfTickets();

    @Query("select t from StateOfTicket t where t.stateId = ?1")
    StateOfTicket findStateofTicketById(Integer id);

}