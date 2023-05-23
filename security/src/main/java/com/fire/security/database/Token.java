package com.fire.security.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Role role;

    public Token(String _token, User _user, Role _role){
        token = _token;
        user = _user;
        role = _role;
    }
}
