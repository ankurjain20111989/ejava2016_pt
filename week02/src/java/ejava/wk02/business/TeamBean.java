package ejava.wk02.business;

import ejava.wk02.model.Team;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class TeamBean {

	@PersistenceContext private EntityManager em;

	public Optional<Team> findOne(String tid) {
		TypedQuery<Team> query = em.createNamedQuery(
				"Team.findByTeamId", Team.class);
		query.setParameter("teamId", tid);
		List<Team> result = query.getResultList();
		if (result.size() > 0)
			return (Optional.of(result.get(0)));
		return (Optional.empty());
	}

	public List<Team> getAll() {
		TypedQuery<Team> query = em.createQuery(
				"select t from Team t", 
				Team.class);
		return (query.getResultList());
	}

	public void save(Team team) {
		em.persist(team);
	}

	public Optional<Team> find(String teamId) {
		return (Optional.ofNullable(em.find(Team.class, teamId)));
	}
	
}
