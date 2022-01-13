package com.pavelmaslov.recipes.services;

import com.pavelmaslov.recipes.data.Recipe;
import com.pavelmaslov.recipes.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository repository;


    public Optional<Recipe> getRecipeById(Integer id) {
        return repository.findById(id);
    }

    public List<Recipe> getAllRecipesWithFilter(Predicate<Recipe> condition) {
        List<Recipe> list = new ArrayList<>();
        repository.findAll().forEach(x -> {
            if (condition.test(x)) {
                list.add(x);
            }
        });
        return list;
    }

    public void putRecipe(Recipe recipe) {
        repository.save(recipe);
    }

    public void deleteRecipe(Integer id) {
        repository.deleteById(id);
    }

}
