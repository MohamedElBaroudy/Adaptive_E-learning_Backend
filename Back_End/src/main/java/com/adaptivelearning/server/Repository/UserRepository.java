package com.adaptivelearning.server.Repository;


import com.adaptivelearning.server.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Integer userId);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailOrUsername(String email, String Username);

    User findByFirstNameAndLastName(String firstname,String lastname);

    User findByFirstNameAndParent(String firstname,User parent);

    User findByToken(String token);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
