package com.baixing.user.controller;

import com.baixing.user.entity.SaveUserReq;
import com.baixing.user.entity.User;
import com.baixing.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ao.wang
 *
 * @create: 2020-10-12
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    
    private final UserService service;
    
    
    @PostMapping("create")
    public boolean create(@RequestBody SaveUserReq req) {
        service.create(req);
        return true;
    }
    
    @PostMapping("update")
    public boolean update(@RequestBody SaveUserReq req) {
        service.update(req);
        return true;
    }
    
    @GetMapping("list")
    public List<User> list() {
        log.info("list");
        return service.list();
    }
    
    @GetMapping("{id}")
    public User get(@PathVariable("id") Long id) {
        return service.get(id).orElse(null);
    }
    
    
    @PostMapping("remove")
    public boolean remove(Long id) {
        service.remove(id);
        return true;
    }
    
}
