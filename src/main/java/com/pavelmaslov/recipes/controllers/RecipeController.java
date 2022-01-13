package com.pavelmaslov.recipes.controllers;

import com.pavelmaslov.recipes.data.Recipe;
import com.pavelmaslov.recipes.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RestController
public class RecipeController {

    @Autowired
    RecipeService service;

    @PostMapping("/api/recipe/new")
    public Map<String, Integer> setRecipe(@Valid @RequestBody Recipe recipe,
                                          Authentication auth) {
        recipe.setDate(LocalDateTime.now());
        recipe.setAuthor(auth.getName());
        service.putRecipe(recipe);
        return Collections.singletonMap("id", recipe.getId());
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        return service.getRecipeById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("api/recipe/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id,
                                       Authentication auth) {
        Recipe recipe = service.getRecipeById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (auth.getName().equals(recipe.getAuthor()) || recipe.getAuthor() == null) {
            service.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("api/recipe/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody Recipe recipe,
                                       Authentication auth) {
        Recipe oldRecipe = service.getRecipeById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!auth.getName().equals(oldRecipe.getAuthor())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            recipe.setDate(LocalDateTime.now());
            recipe.setId(id);
            service.putRecipe(recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("api/recipe/search")
    public List<Recipe> getGroupedRecipes(@RequestParam Map<String, String> allParams) {
        if (allParams.keySet().size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!allParams.containsKey("category") && !allParams.containsKey("name")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Predicate<Recipe> condition;
        if (allParams.containsKey("category")) {
            condition = recipe -> recipe.getCategory().equalsIgnoreCase(allParams.get("category"));
        } else {
            condition = recipe -> recipe.getName().toLowerCase().
                    contains(allParams.get("name").toLowerCase());
        }
        List<Recipe> recipesFiltered = service.getAllRecipesWithFilter(condition);
        recipesFiltered.sort((recipe, t1) -> t1.getDate().compareTo(recipe.getDate()));
        return recipesFiltered;
    }
}
