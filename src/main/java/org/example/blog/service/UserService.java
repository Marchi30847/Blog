package org.example.blog.service;

import org.example.blog.entity.User;
import org.example.blog.exception.NoSuchEntityException;
import org.example.blog.repository.SpringDataUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final SpringDataUserRepository userRepository;

    public UserService(SpringDataUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) throws NoSuchEntityException {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("User Not Found"));
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) throws NoSuchEntityException {
        if (userRepository.findById(id).isEmpty()) {
            throw new NoSuchEntityException("User Not Found");
        }
        userRepository.deleteById(id);
    }
}
