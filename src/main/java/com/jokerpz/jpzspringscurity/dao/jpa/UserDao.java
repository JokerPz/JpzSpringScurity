package com.jokerpz.jpzspringscurity.dao.jpa;

import com.jokerpz.jpzspringscurity.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findUserByUsername(String userName);
}
