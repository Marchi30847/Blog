package org.example.blog.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public void removeFromUsers(User user) {
        users.remove(user);
        user.getRoles().remove(this);
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public Set<User> getUsers() {return users;}

    public void setUsers(Set<User> users) {this.users = users;}
    public void setName(String name) {this.name = name;}
}
