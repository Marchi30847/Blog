package org.example.blog.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id: " + id + ", " +
                "name: " + name + ", " +
                "users: [" +  users.stream()
                .map((user -> user.getId() + " - " + user.getEmail()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("") + "]";
    }

    @PreRemove
    private void preRemove() {
        for (User user : users) {
            user.getRoles().remove(this);
        }
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public Set<User> getUsers() {return users;}

    public void setUsers(Set<User> users) {this.users = users;}
    public void setName(String name) {this.name = name;}
}
