package com.fire.social.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "roles_permissions",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = { @JoinColumn (name = "permission_id")}
    )
    private List<Permission> permissions = new ArrayList<>();

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
