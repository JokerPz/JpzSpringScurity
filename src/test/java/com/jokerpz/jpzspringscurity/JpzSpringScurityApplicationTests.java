package com.jokerpz.jpzspringscurity;

import com.jokerpz.jpzspringscurity.dao.jpa.UserDao;
import com.jokerpz.jpzspringscurity.model.jpa.Role;
import com.jokerpz.jpzspringscurity.model.jpa.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootTest
class JpzSpringScurityApplicationTests {

    @Autowired
    UserDao userDao;
    @Test
    void contextLoads() {
        User u1 = new User();
        u1.setUsername("joker");
        u1.setPassword("1");
        u1.setAccountNonExpired(true);
        u1.setAccountNonLocked(true);
        u1.setCredentialsNonExpired(true);
        u1.setEnabled(true);
        List<Role> rs1 = new ArrayList<>();
        Role r1 = new Role();
        r1.setName("ROLE_admin");
        r1.setNameZh("管理员");
        rs1.add(r1);
        u1.setRoles(rs1);
        userDao.save(u1);
        User u2 = new User();
        u2.setUsername("user");
        u2.setPassword("1");
        u2.setAccountNonExpired(true);
        u2.setAccountNonLocked(true);
        u2.setCredentialsNonExpired(true);
        u2.setEnabled(true);
        List<Role> rs2 = new ArrayList<>();
        Role r2 = new Role();
        r2.setName("ROLE_user");
        r2.setNameZh("普通用户");
        rs2.add(r2);
        u2.setRoles(rs2);
        userDao.save(u2);
    }

    @Test
    public void testBCrypt() {
        String encode = new BCryptPasswordEncoder().encode("1");
        System.out.println("encode = " + encode);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] bytes1 = Base64.getDecoder().decode("MTVhZjNwJTJCeGZxVUFKYUM3VWlxajlBJTNEJTNEOkZHaWZNYjRkZW5sSGtUcjFxTTlLaXclM0QlM0Q");
        String str1 = new String(bytes1, "utf-8");
        System.out.println("str1 = " + str1);

        byte[] bytes2 = Base64.getUrlDecoder().decode("MTVhZjNwJTJCeGZxVUFKYUM3VWlxajlBJTNEJTNEOkZHaWZNYjRkZW5sSGtUcjFxTTlLaXclM0QlM0Q");
        String str2 = new String(bytes2, "utf-8");
        System.out.println("str2 = " + str2);
    }

}
