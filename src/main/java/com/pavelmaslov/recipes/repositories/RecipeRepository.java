package com.pavelmaslov.recipes.repositories;

import com.pavelmaslov.recipes.data.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
}