package com.pavelmaslov.recipes.repositories;

import com.pavelmaslov.recipes.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
