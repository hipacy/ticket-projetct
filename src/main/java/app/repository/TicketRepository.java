package app.repository;

import app.model.Ticket;
import app.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>, QuerydslPredicateExecutor<Ticket> {

    @Query(value = "SELECT DISTINCT t FROM Ticket t JOIN FETCH t.items i")
    List<Ticket> findAllTickets();

    @Query(value = "SELECT DISTINCT t FROM Ticket t JOIN FETCH t.items i WHERE t.ticketId = ?1 ")
    Ticket findTicketById(Integer id);

    @Query(value = "SELECT t.category FROM Ticket t")
    Set<String> findAllCategories();

}
