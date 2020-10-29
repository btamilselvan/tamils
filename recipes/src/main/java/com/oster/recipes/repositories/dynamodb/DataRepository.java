package com.oster.recipes.repositories.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.oster.recipes.entities.dynamodb.Data;
import com.oster.recipes.entities.dynamodb.Data.DataKey;

@EnableScan
public interface DataRepository extends CrudRepository<Data, DataKey> {

  Data findByPk(String pk);

  Data findByCategory(String category);

  Data findByCategoryContains(String category);
}
