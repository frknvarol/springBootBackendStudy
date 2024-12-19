package com.dreamgames.backendengineeringcasestudy.repository;
import java.util.List;

import com.dreamgames.backendengineeringcasestudy.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsername(String username);


}
