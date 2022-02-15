package com.success.configs.repos;

import java.util.List;

import com.success.entities.Recipe;

public interface IMyRepository {

	List<Recipe> findAll();
	
}
