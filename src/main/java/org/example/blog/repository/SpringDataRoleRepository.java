package org.example.blog.repository;

import org.example.blog.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataRoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.email = :userEmail")
    List<Role> findByUsers(String userEmail);
}
