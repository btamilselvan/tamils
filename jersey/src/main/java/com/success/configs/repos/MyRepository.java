package com.success.configs.repos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.success.entities.Recipe;

public class MyRepository implements IMyRepository {

	private EntityManager em = Persistence.createEntityManagerFactory("recipeUnit").createEntityManager();

	@Override
	public List<Recipe> findAll() {
		TypedQuery<Recipe> query = em.createQuery("select r from Recipe r", Recipe.class);
		List<Recipe> data = query.getResultList();
		return data;
	}

}
