package com.success.websocket.jpa.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.success.websocket.jpa.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  List<User> findByEmail(String email);
}
