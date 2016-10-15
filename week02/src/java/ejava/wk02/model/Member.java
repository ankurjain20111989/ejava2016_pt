package ejava.wk02.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {

	public enum Type { PT, FT };

	@Id
	private String matriculation;

	private String name;

	@Column(name = "enroll_type")
	@Enumerated(EnumType.STRING)
	private Type enrollType;

	@ManyToOne
	@JoinColumn( name = "team_id", referencedColumnName = "team_id")
	private Team team;

	public String getMatriculation() {
		return matriculation;
	}

	public void setMatriculation(String matriculation) {
		this.matriculation = matriculation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getEnrollType() {
		return enrollType;
	}

	public void setEnrollType(Type enrollType) {
		this.enrollType = enrollType;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
