package app.repository;

import app.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {


    @Query(value = "select * from Team", nativeQuery = true)
    List<Team> findAllTeams();


    @Query(value = "select  distinct team.TeamId, team.TeamName from" +
            " [itsm].[dbo].[Ticket] tic left join " +
            "[itsm].[dbo].[Ticket_Team] ticteam on ticteam.TicketId = tic.TicketId " +
            "inner join [itsm].[dbo].[Team] team on " +
            "team.TeamId = ticteam.TeamId" +
            " where\n" +
            "    tic.TicketId = :id", nativeQuery = true)
    List<Team> findTeamsByTicket(@Param("id") Integer id);


}