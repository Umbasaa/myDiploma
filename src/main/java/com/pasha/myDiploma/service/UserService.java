package com.pasha.myDiploma.service;

import com.pasha.myDiploma.entity.Role;
import com.pasha.myDiploma.entity.User;
import com.pasha.myDiploma.repository.RoleRepository;
import com.pasha.myDiploma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * UserService. Содержит методы для бизнес-логики приложения. Этот класс реализует интерфейс
 * UserDetailsService (необходим для Spring Security), в котором нужно переопределить один метод loadUserByUsername().
 * В этом классе можно увидеть еще один способ выполнения SQL запроса — с помощью EntityManager.
 * Сначала происходит поиск в БД по имени пользователя, если пользователь с таким именем уже существует метод заканчивает работу.
 * Если имя пользователя не занято, добавляется роль ROLE_USER. Чтобы не хранить пароль в
 * «сыром» виде он предварительно хэшируется с помощью bCryptPasswordEncoder. Затем новый пользователь сохраняется в БД.
 *
 */

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
