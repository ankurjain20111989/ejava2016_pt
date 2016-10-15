package ejava.wk02.view;

import ejava.wk02.business.TeamBean;
import ejava.wk02.model.Team;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class TeamView {

	@EJB private TeamBean teamBean;

	private String name;
	private Team team;
	private String id;
	private List<Team> teams = new LinkedList<>();

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void findTeam() {
		Optional<Team> opt = teamBean.findOne(id);
		if (opt.isPresent()) {
			Team t = opt.get();
			System.out.println(">>> team name: " + t.getName());
		} else {
			FacesMessage m = new FacesMessage("Cannot find your team");
			FacesContext.getCurrentInstance().addMessage(null, m);
		}
	}


	public String createTeam() {
		team = new Team();
		team.setName(name);
		team.setTeamId(UUID.randomUUID().toString().substring(0, 8));
		teamBean.save(team);

		teams = teamBean.getAll();

		return ("create");
	}

	
}
