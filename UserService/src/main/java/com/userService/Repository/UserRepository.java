package com.userService.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.userService.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
