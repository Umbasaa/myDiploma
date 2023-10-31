package com.pasha.myDiploma.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
/**
 Обязательное требование для всех сущностей: приватные поля, геттеры и сеттеры для всех полей и пустой конструктор
 User. В начале об аннотациях: Entity говорит о том, что поля класса имеют отображение в БД,
 Table(name = «t_user») указывает с какой именно таблицей.

 GenerationType.IDENTITY параметр IDENTITY значит, что генерацией id будет заниматься БД.

 Transient, не имеет отображения в БД. Список ролей связан с пользователем отношением многие ко многим
 (один пользователь может иметь несколько ролей с одной стороны и у одной роли может быть несколько пользователей с другой)

 FetchType.EAGER – «жадная» загрузка, т.е. список ролей загружается вместе с пользователем сразу (не ждет пока к нему обратятся).

 Для того, чтобы в дальнейшим использовать класс User в Spring Security, он должен реализовывать интерфейс UserDetails

 метод getAuthorities(),  возвращает список ролей пользователя.

 */

@Entity
@Table(name = "t_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=5, message = "Не меньше 5 знаков")
    private String username;
    @Size(min=5, message = "Не меньше 5 знаков")
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
