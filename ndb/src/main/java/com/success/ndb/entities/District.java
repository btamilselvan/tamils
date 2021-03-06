package com.success.ndb.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the district database table.
 * 
 */
@Entity(name="district")
public class District implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	private State state;
	
	// bi-directional many-to-one association to city
	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
	private List<City> citites;

	public District() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<City> getCitites() {
		return citites;
	}

	public void setCitites(List<City> citites) {
		this.citites = citites;
	}
}