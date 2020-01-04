package app.service;

import app.model.Team;
import app.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teamService")
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;

    }

    public List<Team> findAllTeams() {
        return teamRepository.findAllTeams();
    }

    public List<Team> findAllTicketTeams(Integer id) {
        return teamRepository.findTeamsByTicket(id);
    }

}