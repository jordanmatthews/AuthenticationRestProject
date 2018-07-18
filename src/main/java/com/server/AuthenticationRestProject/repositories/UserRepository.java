package com.server.AuthenticationRestProject.repositories;

import com.server.AuthenticationRestProject.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends EntityRepository<User> {
    User findByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE confirmationtoken = ?1", nativeQuery = true)
    User findByConfirmationtoken(String token);
}
