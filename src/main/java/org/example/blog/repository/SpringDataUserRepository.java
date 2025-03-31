package org.example.blog.repository;

import org.example.blog.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataUserRepository extends CrudRepository<User, Long> {
}
