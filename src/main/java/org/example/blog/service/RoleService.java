package org.example.blog.service;

import org.example.blog.entity.Role;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.repository.SpringDataRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final SpringDataRoleRepository roleRepository;

    @Autowired
    public RoleService(SpringDataRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findById(Long id) throws NoSuchEntityException {
        return roleRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("Role Not Found"));
    }

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void delete(Long id) throws NoSuchEntityException {
        if (roleRepository.findById(id).isEmpty()) {
            throw new NoSuchEntityException("Role Not Found");
        }
        roleRepository.deleteById(id);
    }
}
