package com.baixing.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baixing.user.entity.SaveUserReq;
import com.baixing.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class UserServiceImplTest {
    
    @Autowired
    private UserService service;
    
    @Test
    void list() {
        List<User> users = service.list();
        assertEquals(3, users.size());
    }
    
    @Test
    void get() {
        Optional<User> user = service.get(1L);
        assertTrue(user.isPresent());
        User u = user.get();
        assertEquals("张三", u.getName());
    }

    @Test
    @Transactional
    void create() {
        SaveUserReq req = new SaveUserReq();
        req.setName("kaka");
        req.setAge(30);
        service.create(req);
        List<User> users = service.list();
        assertEquals(4, users.size());
    }

    @Test
    @Transactional
    void update() {
        SaveUserReq req = new SaveUserReq();
        req.setId(1L);
        req.setAge(30);
        service.update(req);
        Optional<User> user = service.get(1L);
        User u = user.get();
        assertEquals(30, u.getAge());
    }

    @Test
    @Transactional
    void remove() {
        List<User> users = service.list();
        assertEquals(3, users.size());
        service.remove(1L);
        users = service.list();
        assertEquals(2, users.size());
    }
}
