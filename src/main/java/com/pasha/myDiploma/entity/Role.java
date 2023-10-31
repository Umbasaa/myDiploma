package com.pasha.myDiploma.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Role. Этот класс должен реализовывать интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли)

 * Кроме конструктора по умолчанию необходимо добавить еще пару публичных конструкторов: первый принимает только id, второй id и name.

 *  Size(min=5) – значит, что минимальная длина поля 5, в случае если ограничение нарушено будет выведено сообщение.
 */
@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
