package org.nop.eshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue
    private Integer id;

    private String role;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="users_roles",
            joinColumns = {@JoinColumn(name="roles_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="users_id", referencedColumnName="id")})
    private Set<User> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<User> userRoles) {
        this.userRoles = userRoles;
    }
}
