package org.example.blog.repository;

import org.example.blog.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataRoleRepository extends CrudRepository<Role, Long> {
}
