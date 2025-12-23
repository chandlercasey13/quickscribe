package org.chandlercasey.quickscribe.repositories;


import org.chandlercasey.quickscribe.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}

