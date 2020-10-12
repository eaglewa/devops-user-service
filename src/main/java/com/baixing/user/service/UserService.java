package com.baixing.user.service;

import com.baixing.user.entity.SaveUserReq;
import com.baixing.user.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * @author: ao.wang
 *
 * @create: 2020-10-12
 **/
public interface UserService {
    
    List<User> list();
    
    Optional<User> get(Long id);
    
    void create(SaveUserReq req);
    
    void update(SaveUserReq req);
    
    void remove(Long id);
}
