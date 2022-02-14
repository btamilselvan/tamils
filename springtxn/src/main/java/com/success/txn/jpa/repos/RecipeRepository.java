package com.success.txn.jpa.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.success.txn.jpa.entities.Recipe;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, String> {

  @Query("select a from Recipe a")
  Slice<Recipe> findAllSlice(Pageable pageable);
}
